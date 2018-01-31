package com.example.admin.notesapp.Data;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDB {

    private static RealmDB realmDBInstance;
    public static int number = 0;
    Realm realm = Realm.getDefaultInstance();
    Calendar calendar = Calendar.getInstance();

    public static RealmDB getRealmDBInstance(){
        if (realmDBInstance == null){
            return new RealmDB();
        }
        return realmDBInstance;
    }

    public static void initRealmDBInstance(){
        if(realmDBInstance == null){
            realmDBInstance = new RealmDB();
        }
    }





  //  private static final RealmDB ourInstance = new RealmDB();

//    public static RealmDB getInstance() {
//        return ourInstance;
//    }
//
//    private RealmDB() {
//    }

    public void addNewNote() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                Note note = realm.createObject(Note.class);
//                note.setText("text " + number++ );
//                note.setNotification(true);
//                note.setNotificationTime(calendar.getTime());

                Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
                note.setText("text " + number++ );
                note.setNotification(true);
                note.setNotificationTime(calendar.getTime());;


//
//                realm.beginTransaction();
//                UserRealm userRealm = realm.createObject(UserRealm.class);
//                userRealm.setToken("some_token");
//                realm.commitTransaction();
//                realm.close();
            }
        });

    }

    public void editNote(int adapterPosition) {
//        // Asynchronously update objects on a background thread
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm bgRealm) {
//                Dog dog = bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
//                dog.setAge(3);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                // Original queries and Realm objects are automatically updated.
//                puppies.size(); // => 0 because there are no more puppies younger than 2 years old
//                managedDog.getAge();   // => 3 the dogs age is updated
//            }
//        });
    }

    public void deleteNote(final String id) {
        Log.v("tag", "delet method");
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                final RealmResults<Note> results = realm.where(Note.class).findAll();
//
//                Note note = results.get(adapterPosition);
//                note.deleteFromRealm();

                Note note = realm.where(Note.class).equalTo("id", id).findFirst();// bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
                note.deleteFromRealm();
                Log.v("tag", "id =" + id);


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
