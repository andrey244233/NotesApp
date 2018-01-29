package com.example.admin.notesapp.MainActivityPackage;

import com.example.admin.notesapp.Data.Note;

import java.util.ArrayList;

public interface MainActivityInterface {

    void returnNotesFromRealm(ArrayList<Note> notes);
}
