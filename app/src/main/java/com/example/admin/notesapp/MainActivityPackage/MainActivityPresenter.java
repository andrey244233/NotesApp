package com.example.admin.notesapp.MainActivityPackage;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;

import com.example.admin.notesapp.AddNewNotePackage.AddEditNoteActivityPresenter;
import com.example.admin.notesapp.AddNewNotePackage.AddNewNoteActivity;
import com.example.admin.notesapp.Data.Note;
import com.example.admin.notesapp.Data.RealmDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;
import static com.example.admin.notesapp.MainActivityPackage.MainActivity.REQ_CODE_SPEECH_INPUT;


public class MainActivityPresenter{

    MainActivityInterface mainActivityInterface;
    MainActivity mainActivity;
    RealmDB realmDB;
    public static final String NOTE_TEXT = "note text";
    public static final String NOTE_DATE = "note date";
    public static final String NOTE_ID = "note id" ;
    public static final String NOTE_NOTIFICATION_ON = "note notification on" ;


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
    }

    public void addNewNote(Context context, String text) {
        Intent intent = new Intent(context, AddNewNoteActivity.class);
        intent.putExtra(NOTE_TEXT, text);
        context.startActivity(intent);
      //  context.startActivity(new Intent(context, AddNewNoteActivity.class));
    }

    public void editNoteById(String id, String text, Date date, Boolean notification, Context context) {
        Intent intent = new Intent(context, AddNewNoteActivity.class);
        intent.putExtra(NOTE_ID, id);
        intent.putExtra(NOTE_TEXT, text);
        intent.putExtra(NOTE_DATE, date);
        intent.putExtra(NOTE_NOTIFICATION_ON, date);
        context.startActivity(intent);
    }

    public void deleteNoteById(String id) {
        realmDB.deleteNote(id);
    }

    public void addNewNoteByVoice(MainActivity mainActivity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Продиктуйте свою заметку");
        try {
            mainActivity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.v("tag", "exception");
        }


    }




}
