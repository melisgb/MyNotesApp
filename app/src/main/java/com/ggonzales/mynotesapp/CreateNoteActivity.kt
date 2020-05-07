package com.ggonzales.mynotesapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.contentcapture.ContentCaptureCondition
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        createNoteButton.setOnClickListener {
            //instead of using a new intent to go back to MainActivity, we finish this activity and will go back.
            val DBM = DBManager(this)
            val title = noteTitleEditText.text.toString()
            val desc = noteDescEditText.text.toString()
            var values = ContentValues()
            values.put("title", title)
            values.put("desc", desc)
            val ID = DBM.insertNote(values)
            if(ID > 0){
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "Unable to add note", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
