package com.example.admin.notesapp.MainActivityPackage;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.admin.notesapp.AddNewNotePackage.AddNewNoteActivity;
import com.example.admin.notesapp.Data.Note;
import com.example.admin.notesapp.Data.RealmDB;
import com.example.admin.notesapp.ReturnDataCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class MainActivityPresenter implements AddNewNoteActivity.Callback, Serializable {
    // MainActivityPresenter mainActivityPresenter;
    MainActivityInterface mainActivityInterface;
    RealmDB realmDB;
    public static final String NOTE_TEXT = "note text";
    public static final String NOTE_DATE = "note date";


    public MainActivityPresenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
        realmDB = RealmDB.getRealmDBInstance();
    }

    protected MainActivityPresenter(Parcel in) {
    }


    public void getNotesFromRealm() {
        ArrayList<Note> allNotes = realmDB.getAllNotes();
        mainActivityInterface.returnNotesFromRealm(allNotes);
    }

    public void addNewNote(Context context) {

      //  AddNewNoteActivity addNewNoteActivity = new AddNewNoteActivity();
       // addNewNoteActivity.registerCallBack(this);
        Intent intent = new Intent(context, AddNewNoteActivity.class);
      //  intent.putExtra("callback", (Serializable) addNewNoteActivity);

        context.startActivity(intent);

//        intent.putExtra("callback", (Serializable) callback);
//        context.startActivity(new Intent(context, AddNewNoteActivity.class));


        //AddNewNoteActivity.Callback callback. =
//        mainActivityPresenter.r
//        Intent intent = new Intent();
//        intent.putExtra("callBack", (Serializable) callback);
//        Log.v("atg", "add new note callback = " + callback);
//        context.startActivity(new Intent(context, AddNewNoteActivity.class));


        //realmDB.addNewNote();
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
        Log.v("tag", "s = " + s);
    }

}
