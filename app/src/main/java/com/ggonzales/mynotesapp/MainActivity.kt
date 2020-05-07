package com.ggonzales.mynotesapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_element.view.*

class MainActivity : AppCompatActivity() {
    var notesList = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Loading hardcoded notes
//        notesList.add(Note(1, "Melissa", "Melissa es inteligente"))
//        notesList.add(Note(2, "Carlos", "Carlos es inteligente"))
//        notesList.add(Note(3, "Isabel", "Isabel es inteligente"))
//        notesList.add(Note(4, "Elena", "Elena es inteligente"))

        //Loading notes from DB
        loadNotes("%")

//
//        var myNotesAdapter = NotesAdapter(notesList)
//        notesListView.adapter = myNotesAdapter
    }

    fun loadNotes(title : String){
        val DBM = DBManager(this)
        val projections = arrayOf("ID", "title", "desc")
        val selectionArgs  = arrayOf(title)
        val cursor = DBM.getNotesQuery(projections, "Title like ?", selectionArgs, "desc")
        notesList.clear()
        if(cursor.moveToFirst()){
            do{
                notesList.add(
                    Note(
                        noteID = cursor.getInt(cursor.getColumnIndex("ID")),
                        noteTitle = cursor.getString(cursor.getColumnIndex("title")),
                        noteDesc = cursor.getString(cursor.getColumnIndex("desc"))
                    )
                )


            }while (cursor.moveToNext())
        }

        var myNotesAdapter = NotesAdapter(notesList)
        notesListView.adapter = myNotesAdapter
    }

    fun deleteNote(noteID : Int){
        val DBM = DBManager(this)
        val selectionArgs  = arrayOf(noteID.toString())
        val count = DBM.deleteNote("ID = ?", selectionArgs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu!!.findItem(R.id.search_note_ic).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                loadNotes("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.add_note_ic -> {
                    val intent = Intent(this, CreateNoteActivity :: class.java )
                    startActivity(intent)
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
            var myView = layoutInflater.inflate(R.layout.note_element, null)

            var currentNote = notesListAdapter[position]
            myView.noteTitleTxtView.text = currentNote.noteTitle.toString()
            myView.noteDescTxtView.text = currentNote.noteDesc.toString()

            myView.deleteButton.setOnClickListener {
                deleteNote(currentNote.noteID!!)
                loadNotes("%")
            }

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



