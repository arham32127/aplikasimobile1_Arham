package com.example.aplikasimobile1
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "DB_MHS"
        private const val DATABASE_VERSION = 1
        private const val CREATE_TABLE_MY_DATA = "CREATE TABLE TBL_MHS " +
                "(nim TEXT PRIMARY KEY, nama TEXT, email TEXT," +
                "password TEXT)"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_MY_DATA)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        // Untuk upgrade database
    }
}
