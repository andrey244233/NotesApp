package com.example.admin.notesapp.MainActivityPackage;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.admin.notesapp.AddNewNotePackage.AddNewNoteActivity;
import com.example.admin.notesapp.Data.Note;
import com.example.admin.notesapp.Data.RealmDB;
import com.example.admin.notesapp.ReturnDataCallBack;

import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.callback.Callback;

public class MainActivityPresenter implements AddNewNoteActivity.Callback {
    MainActivityInterface mainActivityInterface;
    RealmDB realmDB;
    public static final String NOTE_TEXT = "note text";
    public static final String NOTE_DATE = "note text";


    public MainActivityPresenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
        realmDB = RealmDB.getRealmDBInstance();
    }

    public void getNotesFromRealm() {
        ArrayList<Note> allNotes = realmDB.getAllNotes();
        mainActivityInterface.returnNotesFromRealm(allNotes);
    }

    public void addNewNote(Context context) {

        context.startActivity(new Intent(context, AddNewNoteActivity.class));
        realmDB.addNewNote();
        // getNotesFromRealm();
    }

    public void editNoteById(Context context, String text, Date date) {
        Log.v("tag", "text in presenter =" + text + "date = " + date);
        Intent intent = new Intent(context, AddNewNoteActivity.class);
        intent.putExtra(NOTE_TEXT, text);
        intent.putExtra(NOTE_DATE, date);
        context.startActivity(intent);

        // realmDB.editNote(adapterPosition);
    }

    public void deleteNoteById(String id) {
        Log.v("tag", "id " + id);
        realmDB.deleteNote(id);
    }

    @Override
    public void callingBack(String s) {
//        MainActivityInterface.returnData();
        //if(int = 1 то апдейт)
        //if(int = 0 то эдд нью)
    }
}
