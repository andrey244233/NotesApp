package com.example.admin.notesapp.MainActivityPackage;

import com.example.admin.notesapp.Data.Note;

import java.util.ArrayList;
import java.util.Date;

public interface MainActivityInterface {

    void returnNotesFromRealm(ArrayList<Note> notes);
    //void returnData(String text, Date date);
}
