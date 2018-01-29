package com.example.admin.notesapp.ApplicationClassPackage;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
