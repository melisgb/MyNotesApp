package com.ggonzales.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        createNoteButton.setOnClickListener {
            //instead of using a new intent to go back to MainActivity, we finish this activity and will go back.
            finish()
        }
    }

}
