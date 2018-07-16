package com.example.larvataandroid.orderingsytem.Realm;

import android.util.Log;

import com.example.larvataandroid.orderingsytem.RealmObject.Users;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmFunction {
    private static final String TAG = RealmFunction.class.getSimpleName();
    public static RealmFunction mRealmFunction = null;

    public static RealmFunction getInstance() {
        if (mRealmFunction == null) {
            mRealmFunction = new RealmFunction();
        }
        return mRealmFunction;
    }

    public void initialFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error Message: " + e.getMessage());
                }
                FirestoreToRealm(queryDocumentSnapshots.getDocuments());
            }
        });

    }


    public void FirestoreToRealm(final List<DocumentSnapshot> messagesDocuments) {

        for (int i = 0; i < messagesDocuments.size(); i++) {

            final int finalI = i;
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Users users = new Users();
                    users.setId((String) messagesDocuments.get(finalI).getId());
                    users.setName((String) messagesDocuments.get(finalI).get("name"));
                    users.setAge(((Long) messagesDocuments.get(finalI).get("age")).intValue());
                    realm.copyToRealmOrUpdate(users);
                }
            });
        }
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Users> users = realm.where(Users.class).findAll();

        for (int i = 0;i < users.size();i++) {
            Log.d(TAG, "FirestoreToRealm Name : " + users.get(i).getAge());
            Log.d(TAG, "FirestoreToRealm Age : " + users.get(i).getName());
        }
    }
}



