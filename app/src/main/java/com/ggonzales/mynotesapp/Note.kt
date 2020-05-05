package com.ggonzales.mynotesapp

class Note{
    var noteID : Int? = null
    var noteName : String? = null
    var noteDesc : String? = null

    constructor(noteID : Int, noteName: String, noteDesc : String){
        this.noteID = noteID
        this.noteName = noteName
        this.noteDesc = noteDesc
    }
}