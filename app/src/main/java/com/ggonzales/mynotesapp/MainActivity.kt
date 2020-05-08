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

        //Loading notes from DB
        loadNotes("%")
        Toast.makeText(this, "ACT ON CREATE", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        Toast.makeText(this, "ACT ON START", Toast.LENGTH_LONG).show()
        super.onStart()
    }
    //when the user returns to this activity. Example when I was creating or modifying a note and went back to Main Activity
    override fun onResume(){
        Toast.makeText(this, "ACT ON RESUME", Toast.LENGTH_LONG).show()
        super.onResume()
        loadNotes("%")
    }
    //another activity comes to front
    override fun onPause(){
        Toast.makeText(this, "ACT ON PAUSE", Toast.LENGTH_LONG).show()
        super.onPause()
    }
    //activity no longer visible
    override fun onStop() {
        Toast.makeText(this, "ACT ON STOP", Toast.LENGTH_LONG).show()
        super.onStop()
    }
    //activity is destroyed by system
    override fun onDestroy(){
        Toast.makeText(this, "ACT ON DESTROY", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
    override fun onRestart(){
        Toast.makeText(this, "ACT ON RESTART", Toast.LENGTH_LONG).show()
        super.onRestart()
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
                        noteID = cursor.getLong(cursor.getColumnIndex("ID")),
                        noteTitle = cursor.getString(cursor.getColumnIndex("title")),
                        noteDesc = cursor.getString(cursor.getColumnIndex("desc"))
                    )
                )


            }while (cursor.moveToNext())
        }

        var myNotesAdapter = NotesAdapter(notesList)
        notesListView.adapter = myNotesAdapter
    }

    fun deleteNote(noteID : Long){
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
            myView.editButton.setOnClickListener {
                val intent = Intent(applicationContext, CreateNoteActivity :: class.java )
                intent.putExtra("ID", currentNote.noteID)
                intent.putExtra("title", currentNote.noteTitle)
                intent.putExtra("desc", currentNote.noteDesc)
                startActivity(intent)
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



