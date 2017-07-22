package com.adamsapp.rateme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 15-Jul-17.
 */

public class PhoneAuth extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener listener;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    EditText getPhone,getCode;
    Button sendCode,verifyCode;
    String verID=null;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneauth);

        dialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    finish();
                    startActivity(new Intent(PhoneAuth.this,HomeActivity.class));
                    }
            }
        };

        getPhone = (EditText) findViewById(R.id.get_phone_number);
        getCode = (EditText) findViewById(R.id.get_code);

        sendCode = (Button) findViewById(R.id.verify_phone);
        verifyCode = (Button) findViewById(R.id.verify_code);

    }

    public void verifyPhone(View v){
        String number = "+237"+getPhone.getText().toString().trim();
        dialog.setMessage("Please Wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, PhoneAuth.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithCredential(phoneAuthCredential);
                        getPhone.setVisibility(View.INVISIBLE);
                        getCode.setVisibility(View.VISIBLE);
                        sendCode.setVisibility(View.INVISIBLE);
                        verifyCode.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(PhoneAuth.this, ""+e, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verID = s;
                        getPhone.setVisibility(View.INVISIBLE);
                        getCode.setVisibility(View.VISIBLE);
                        sendCode.setVisibility(View.INVISIBLE);
                        verifyCode.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        Toast.makeText(PhoneAuth.this, "Too Slow "+s, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        auth.signInWithCredential(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(PhoneAuth.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PhoneAuth.this,SetupUserData.class));

                finish();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PhoneAuth.this, ""+e, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void verifyCode(View v){
        String code = getCode.getText().toString().trim();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(this, "Enter Code", Toast.LENGTH_SHORT).show();
            return;
        }
        signInWithCredential(PhoneAuthProvider.getCredential(verID,code));
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (listener != null) {
            auth.removeAuthStateListener(listener);
        }
    }



}
