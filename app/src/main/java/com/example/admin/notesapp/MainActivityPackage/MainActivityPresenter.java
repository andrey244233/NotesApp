package com.example.admin.notesapp.MainActivityPackage;


import com.example.admin.notesapp.Data.Note;
import com.example.admin.notesapp.Data.RealmDB;

import java.util.ArrayList;

public class MainActivityPresenter {
    MainActivityInterface mainActivityInterface;
    RealmDB realmDB;

    public MainActivityPresenter(MainActivityInterface mainActivityInterface){
            this.mainActivityInterface = mainActivityInterface;
            realmDB = RealmDB.getInstance();
    }

    public void getNotesFromRealm() {
        ArrayList<Note> allNotes = realmDB.getAllNotes();
        mainActivityInterface.returnNotesFromRealm(allNotes);
    }

    public void addNewNote() {
        realmDB.addNewNote();
    }

    public void editNoteById(int adapterPosition) {
        realmDB.editNote(adapterPosition);
    }

    public void deleteNoteById(int adapterPosition) {
        realmDB.deleteNote(adapterPosition);
    }
}
