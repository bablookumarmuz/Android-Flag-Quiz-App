package com.techmania.flagquiz.database

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseCopyHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    private var DB_PATH: String = "/data/data/" + context.packageName + "/databases/"
    private var myContext: Context = context
    lateinit var myDataBase: SQLiteDatabase

    companion object {
        const val DB_NAME: String = "countries.db"
    }

    /**
     * Check if the database already exists.
     */
    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            // Database doesn't exist yet.
        }
        checkDB?.close()
        return checkDB != null
    }

    /**
     * Copy the database from assets to system database path.
     */
    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput = myContext.assets?.open(DB_NAME)
        val outFileName = DB_PATH + DB_NAME
        val myOutput: OutputStream = FileOutputStream(outFileName)

        val buffer = ByteArray(1024)
        var length: Int

        if (myInput != null) {
            while (myInput.read(buffer).also { length = it } > 0) {
                myOutput.write(buffer, 0, length)
            }
        }

        myOutput.flush()
        myOutput.close()
        myInput?.close()
    }

    /**
     * Creates the database if it doesn't exist by copying from assets.
     */
    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (!dbExist) {
            this.readableDatabase.close()
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    /**
     * Opens the database for reading.
     */
    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        if (this::myDataBase.isInitialized && myDataBase.isOpen) {
            myDataBase.close()
        }
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // This method will not be used as the database is pre-populated and copied from assets.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // If database structure changes, you can handle upgrades here.
    }
}
