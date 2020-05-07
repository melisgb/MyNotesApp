package com.ggonzales.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import com.ggonzales.mynotesapp.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notes_view.*
import kotlinx.android.synthetic.main.notes_view.view.*

class MainActivity : AppCompatActivity() {
    var notesList = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //Loading notes
        notesList.add(Note(1, "Melissa", "Melissa es inteligente"))
        notesList.add(Note(2, "Carlos", "Carlos es inteligente"))
        notesList.add(Note(3, "Isabel", "Isabel es inteligente"))
        notesList.add(Note(4, "Elena", "Elena es inteligente"))


        var myNotesAdapter = NotesAdapter(notesList)
        notesListView.adapter = myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.add_note_ic -> {
                    val intent = Intent(this, CreateNoteActivity :: class.java )
                    startActivity(intent)
                }
                R.id.search_note_ic -> {

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class NotesAdapter : BaseAdapter {
        var notesListAdapter = ArrayList<Note>()
        constructor(notesListAdapter : ArrayList<Note>) : super(){
            this.notesListAdapter = notesListAdapter
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.notes_view, null)

            var currentNote = notesListAdapter[position]
            myView.noteTitleTxtView.text = currentNote.noteName.toString()
            myView.noteDescTxtView.text = currentNote.noteDesc.toString()
            return myView
        }

        override fun getItem(position: Int): Any {
            return notesListAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position!!.toLong()
        }

        override fun getCount(): Int {
            return notesListAdapter.size
        }

    }

}



