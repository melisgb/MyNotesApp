package com.ggonzales.mynotesapp

class Note{
    var noteID : Long? = null
    var noteTitle : String? = null
    var noteDesc : String? = null

    constructor(noteID : Long, noteTitle: String, noteDesc : String){
        this.noteID = noteID
        this.noteTitle = noteTitle
        this.noteDesc = noteDesc
    }
}