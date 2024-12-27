package com.example.aplikasimobile1

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.MessageDigest

class EditProfile : AppCompatActivity() {

    private lateinit var databaseHelper: DBHelper

    fun getMD5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(input.toByteArray())
        // convert the byte array to hexadecimal string
        val hexString = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun isValidEmail(email: String): Boolean {
        val result: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        // Kode Inisialisasi
        var editNIM = findViewById<EditText>(R.id.editNIM)
        var editNama = findViewById<EditText>(R.id.editNama)
        var editEmail = findViewById<EditText>(R.id.editEmail)
        var editPassword = findViewById<EditText>(R.id.editPassword)
        // Ambil dan Tampilkan Data Intent
        val dataNIM: String = intent.getStringExtra("nim").toString()
        val dataNama: String = intent.getStringExtra("nama").toString()
        val dataEmail: String = intent.getStringExtra("email").toString()
        editNIM.setText(dataNIM)
        editNama.setText(dataNama)
        editEmail.setText(dataEmail)
        // Kunci Akses editNIM
        editNIM.isFocusable = false
        editNIM.isFocusableInTouchMode = false
        // Inisialisasi Button dan Listener
        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)
        val btnBatal = findViewById<Button>(R.id.btnBatal)
        var newNama: String = ""
        var newEmail: String = ""

        databaseHelper = DBHelper(this)

        btnKonfirmasi.setOnClickListener {
            newNama = editNama.text.toString()
            newEmail= editEmail.text.toString()
            val newPassword: String = editPassword.text.toString()
            // Verifikasi Data
            if (newNama.equals("") || newEmail.equals("")) {
                Toast.makeText(
                    applicationContext, "Nama dan Email tidak boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val db = databaseHelper.writableDatabase

                var updateValues: ContentValues? = null
                if (newPassword.equals("")) {
                    updateValues = ContentValues().apply {
                        put("nama", newNama)
                        put("email", newEmail)
                    }
                } else {
                    updateValues = ContentValues().apply {
                        put("nama", newNama)
                        put("email", newEmail)
                        put("password", newPassword)
                    }

                }


                var result = db.update(
                    "TBL_MHS", updateValues,
                    "nim = ? AND nama = ?", arrayOf(dataNIM, dataNama)
                )
                if (result > 0) {
                    Toast.makeText(applicationContext, "Update Berhasil", Toast.LENGTH_LONG)
                        .show()
                    // Kirim kembali data yang diperbarui
                    val resultIntent = Intent()
                    resultIntent.putExtra("updated_nama", newNama)
                    resultIntent.putExtra("updated_email", newEmail)
                    setResult(RESULT_OK,resultIntent)

                    // Kembali ke ProfileActivity
                } else {
                    Toast.makeText(applicationContext, "Update Gagal", Toast.LENGTH_LONG).show()
                }
            }
            btnBatal.setOnClickListener {
                var intent: Intent = Intent(this, ProfileMahasiswa::class.java)
                intent.putExtra("nim", dataNIM)
                intent.putExtra("nama", newNama)
                intent.putExtra("email", newEmail)
                startActivity(intent)
//                editProfileLauncher.launch(intent)
            }
        }
    }
}