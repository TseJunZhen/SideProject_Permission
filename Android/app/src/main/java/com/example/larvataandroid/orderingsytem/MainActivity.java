package com.example.larvataandroid.orderingsytem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.larvataandroid.orderingsytem.Realm.RealmFunction;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, Object> user = new HashMap<>();
    TextView textViewReadName;
    TextView textViewReadBorn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();//初始化
        initialRealm();

    }

    private void initialView() {
        textViewReadName = findViewById(R.id.textView_name);
        textViewReadBorn = findViewById(R.id.textView_born);
    }
    private void initialRealm(){
        RealmFunction.getInstance().initialFirestore();
    }




}


