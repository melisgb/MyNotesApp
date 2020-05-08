package com.ggonzales.mynotesapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.contentcapture.ContentCaptureCondition
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_note.*
import java.lang.Exception

class CreateNoteActivity : AppCompatActivity() {
    var noteID : Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val intentBundle : Bundle? = intent.extras
        noteID = intentBundle?.getLong("ID")

        if(noteID != null){
            val noteTitle = intentBundle!!.getString("title")
            val noteDesc = intentBundle!!.getString("desc")
            noteTitleEditText.setText(noteTitle)
            noteDescEditText.setText(noteDesc)
            createNoteButton.text = "UPDATE"
        }



        createNoteButton.setOnClickListener {
            //instead of using a new intent to go back to MainActivity, we finish this activity and will go back.
            val DBM = DBManager(this)
            val title = noteTitleEditText.text.toString()
            val desc = noteDescEditText.text.toString()
            var values = ContentValues()
            values.put("title", title)
            values.put("desc", desc)

            if(noteID == null){
                val ID = DBM.insertNote(values)
                if(ID > 0){
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "Unable to add note", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                val qty = DBM.updateNote(values, "ID = ?", arrayOf(noteID.toString()) )
                if(qty > 0) Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}
