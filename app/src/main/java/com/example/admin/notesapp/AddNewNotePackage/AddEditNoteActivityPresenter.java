package com.example.admin.notesapp.AddNewNotePackage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.admin.notesapp.Data.RealmDB;
import com.example.admin.notesapp.MainActivityPackage.MainActivity;
import com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter;

import java.util.Date;

public class AddEditNoteActivityPresenter {


    RealmDB realmDB;


    public void addNewNoteToRealm(String text, Date date, Boolean notification, Context context) {
        realmDB = RealmDB.getRealmDBInstance();
        Log.v("tag", "add or edit note activity presenter" + "add new note to realm");
        realmDB.addNewNote(text, date, notification);
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void editNoteInRealm(String id, String text, Date date, Boolean notification, Context context) {
        realmDB = RealmDB.getRealmDBInstance();
        Log.v("tag", "add or edit note activity presenter" + "edit note to realm");
       realmDB.editNote(id, text, date, notification);
        context.startActivity(new Intent(context, MainActivity.class));
    }


}














