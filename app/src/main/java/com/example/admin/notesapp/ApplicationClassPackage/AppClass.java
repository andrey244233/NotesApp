package com.example.admin.notesapp.ApplicationClassPackage;

import android.app.Application;

import com.example.admin.notesapp.Data.RealmDB;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.example.admin.notesapp.Data.RealmDB.initRealmDBInstance;


public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        initRealmDBInstance();
    }
}
