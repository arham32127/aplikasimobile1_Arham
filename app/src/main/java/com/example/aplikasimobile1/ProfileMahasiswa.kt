package com.example.aplikasimobile1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileMahasiswa : AppCompatActivity() {
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_mahasiswa)

        // Inisialisasi Objek
        var txtNIM = findViewById<TextView>(R.id.textNIM)
        var txtNama = findViewById<TextView>(R.id.textNama)
        var txtEmail = findViewById<TextView>(R.id.textEmail)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        // Ambil Data Intent
        val dataNIM: String = intent.getStringExtra("nim").toString()
        val dataNama: String = intent.getStringExtra("nama").toString()
        val dataEmail: String = intent.getStringExtra("email").toString()
        // Tampilkan Data Intent
        // Tampilkan Data Intent
        txtNIM.setText("NIM : " + dataNIM)
        txtNama.setText("Nama : " + dataNama)
        txtEmail.setText("E-Mail : " + dataEmail)

        editProfileLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val resultIntent = result.data
                    val updatedNama = resultIntent?.getStringExtra("updated_nama")
                    val updatedEmail = resultIntent?.getStringExtra("updated_email")

                    // Update TextView dengan data yang diperbarui
                    updatedNama?.let { txtNama.text = "Nama : $it" }
                    updatedEmail?.let { txtEmail.text = "E-Mail : $it" }
                }
            }




        // Button Ubah
        btnEdit.setOnClickListener {
            var intent: Intent = Intent(this, EditProfile::class.java)
            intent.putExtra("nim", dataNIM)
            intent.putExtra("nama", dataNama)
            intent.putExtra("email", dataEmail)
            startActivity(intent)
            //editProfileLauncher.launch(intent)
        }

        // Button Keluar
        btnKeluar.setOnClickListener {
            finish()
        }
    }
}