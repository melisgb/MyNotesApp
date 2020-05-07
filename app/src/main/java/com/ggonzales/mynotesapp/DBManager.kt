package com.ggonzales.mynotesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.projection.MediaProjection
import android.widget.Toast
//when trying to access the DB locally you can access C:\Users\[user]\AppData\Local\Android\sdk\platform-tools

class DBManager{
    val dbName = "NotesDB"
    val dbTable = "Notes"
    val id = "ID"
    val title = "title"
    val desc = "desc"
    var dbVersion = 1

    val queryCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + id + " INTEGER PRIMARY KEY, "+
            title + " TEXT, " + desc + " TEXT );"

    var sqlDB : SQLiteDatabase? = null

    constructor(context : Context){
        var db = DataBaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DataBaseHelperNotes : SQLiteOpenHelper {
        var context : Context? = null
        constructor(context : Context) : super( context, dbName, null, dbVersion ) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(queryCreateTable)
            Toast.makeText(this.context, "DB Created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTable)
            //check the dbVersion...              dbVersion = newVersion
        }

    }

    fun insertNote(values: ContentValues):Long{
        val ID = sqlDB!!.insert(dbTable, "", values)
        return ID
    }
    
    //specifying which columns you want, the rows and also in which order to sort the results
    fun queryDB (projection: Array<String>, )
}

