package com.example.larvataandroid.orderingsytem;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                // Must be bumped when the schema changes
                .schemaVersion(0)
                // Migration to run instead of throwing an exception
                // .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);
    }
//    @Override
//    public void onTerminate() {
//        Realm.getDefaultInstance().close();
//        super.onTerminate();
//    }
}
