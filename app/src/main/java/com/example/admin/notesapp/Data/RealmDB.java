package com.example.admin.notesapp.Data;


import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

public class RealmDB {

    private static RealmDB realmDBInstance;
    Realm realm = Realm.getDefaultInstance();

    public static RealmDB getRealmDBInstance() {
        if (realmDBInstance == null) {
            return new RealmDB();
        }
        return realmDBInstance;
    }

    public static void initRealmDBInstance() {
        if (realmDBInstance == null) {
            realmDBInstance = new RealmDB();
        }
    }

    public void addNewNote(final String text, final Date notificationTime, final boolean notification) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.v("tag", "text in database =" + text);
                Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
                note.setText(text);
                note.setNotification(notification);
                note.setNotificationTime(notificationTime);
                ;
            }
        });
    }

    public void editNote(final String id, final String text, final Date date, final Boolean notification) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", id).findFirst();
                note.setText(text);
                note.setNotificationTime(date);
                note.setNotification(notification);
            }
        });
    }

    public void deleteNote(final String id) {
        Log.v("tag", "delet method");
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", id).findFirst();// bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
                note.deleteFromRealm();
                Log.v("tag", "id =" + id);
            }
        });
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> allNotes = new ArrayList(realm.where(Note.class).findAll());
        return allNotes;
    }


}
