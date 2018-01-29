package com.example.admin.notesapp.Data;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDB {
    public static int number =0;
    Realm realm = Realm.getDefaultInstance();
    Calendar calendar = Calendar.getInstance();

    private static final RealmDB ourInstance = new RealmDB();

    public static RealmDB getInstance() {
        return ourInstance;
    }

    private RealmDB() {
    }

    public void addNewNote() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class); //, UUID.randomUUID());
                note.setText("text " + number++ );
                note.setNotification(true);
                note.setNotificationTime(calendar.getTime());

            }
        });

    }

    public void editNote(int adapterPosition) {
    }

    public void deleteNote(final int adapterPosition) {
        Log.v("tag", "delet method");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<Note> results = realm.where(Note.class).findAll();

                Note note = results.get(adapterPosition);
                note.deleteFromRealm();

//                RealmResults<Note> note2 = realm.where(Note.class)
//                        .equalTo("id", adapterPosition)
//                        .findAll();
//
//
//                Log.v("tag", "note = "+ note);
//                Log.v("tag", "note2 = "+ note2);
//                note.deleteFromRealm();
//                note2.deleteAllFromRealm();
//
//
//
////                // RealmQuery<Note> note = realm.where(Note.class).equalTo("id", adapterPosition);
////                //realm.
//                RealmResults<Note> rows = realm.where(Note.class).equalTo("id", adapterPosition).findAll();
//                rows.deleteAllFromRealm();
            }
        });

    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> allNotes = new ArrayList(realm.where(Note.class).findAll());
        return allNotes;
    }


}
