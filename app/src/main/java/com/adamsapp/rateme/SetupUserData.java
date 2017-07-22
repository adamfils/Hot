package com.adamsapp.rateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by user on 15-Jul-17.
 */

public class SetupUserData extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference databaseReference;

    EditText first,last,town;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_user_data);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("user");

        first = (EditText) findViewById(R.id.setup_user_firstname);
        last = (EditText) findViewById(R.id.setup_user_lastname);
        town = (EditText) findViewById(R.id.setup_user_town);



    }

    public void saveDetails(View v){
        String one = first.getText().toString().trim();
        String two = last.getText().toString().trim();
        String twn = town.getText().toString().trim();

        if(TextUtils.isEmpty(one) || TextUtils.isEmpty(two) || TextUtils.isEmpty(twn)){
            Toast.makeText(this, "Fill All Info To Proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("firstName",one);
        map.put("lastName",two);
        map.put("town",twn);

        databaseReference.child(user.getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SetupUserData.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(SetupUserData.this,HomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SetupUserData.this, "Could Not Save Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
