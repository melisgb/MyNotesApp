package com.ggonzales.mynotesapp

class Note{
    var noteID : Int? = null
    var noteTitle : String? = null
    var noteDesc : String? = null

    constructor(noteID : Int, noteTitle: String, noteDesc : String){
        this.noteID = noteID
        this.noteTitle = noteTitle
        this.noteDesc = noteDesc
    }
}