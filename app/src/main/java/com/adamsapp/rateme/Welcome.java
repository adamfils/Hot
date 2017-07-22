package com.adamsapp.rateme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

public class Welcome extends AppCompatActivity {

    Button signIn, register;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressDialog = new ProgressDialog(Welcome.this);
        builder = new AlertDialog.Builder(Welcome.this);
        dialog = builder.create();


        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    finish();
                    startActivity(new Intent(Welcome.this,HomeActivity.class));
                    Log.e("D Mess", "onAuthStateChanged:signed_in:" + user.getUid());
                }

            }
        };

        signIn = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.signUpBtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Welcome.this);
                View v = getLayoutInflater().inflate(R.layout.login_layout, null);
                final EditText email = (EditText) v.findViewById(R.id.emailText);
                final EditText pass = (EditText) v.findViewById(R.id.passText);
                Button login = (Button) v.findViewById(R.id.signInBtn);
                ImageButton cancel =(ImageButton) v.findViewById(R.id.cancel_btn);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailS, passS;
                        emailS = email.getText().toString().trim();
                        passS = pass.getText().toString().trim();
                        if (TextUtils.isEmpty(emailS) || TextUtils.isEmpty(passS)) {
                            Toast.makeText(Welcome.this, "Fill All Fields Before Proceeding...", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (passS.length() < 6) {
                            Toast.makeText(Welcome.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            progressDialog.setTitle("Signing In");
                            progressDialog.setMessage("Please Wait.....");
                            progressDialog.setCanceledOnTouchOutside(false);

                            progressDialog.show();
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailS, passS)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            progressDialog.dismiss();
                                            finish();
                                            startActivity(new Intent(Welcome.this, HomeActivity.class));
                                            Toast.makeText(Welcome.this, "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Welcome.this, "Could Not Sign In", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        finish();
                                        startActivity(new Intent(Welcome.this, HomeActivity.class));
                                        Toast.makeText(Welcome.this, "Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Welcome.this, "Could Not Sign In", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(Welcome.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(Welcome.this);
                View v = getLayoutInflater().inflate(R.layout.register_layout, null);
                final EditText email =(EditText) v.findViewById(R.id.emailTextReg);
                final EditText pass =(EditText) v.findViewById(R.id.passTextReg);
                Button register =(Button) v.findViewById(R.id.registerBtn);
                ImageButton cancel =(ImageButton) v.findViewById(R.id.cancel_btn);
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String emailS, passS;
                        emailS = email.getText().toString().trim();
                        passS = pass.getText().toString().trim();
                        if (TextUtils.isEmpty(emailS) || TextUtils.isEmpty(passS)) {
                            Toast.makeText(Welcome.this, "Fill All Fields Before Proceeding...", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            progressDialog.setTitle("Signing In");
                            progressDialog.setMessage("Please Wait.....");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailS, passS)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            progressDialog.dismiss();
                                            finish();
                                            startActivity(new Intent(Welcome.this, HomeActivity.class));
                                            Toast.makeText(Welcome.this, "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    auth.fetchProvidersForEmail(emailS).addOnSuccessListener(new OnSuccessListener<ProviderQueryResult>() {
                                        @Override
                                        public void onSuccess(ProviderQueryResult providerQueryResult) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Welcome.this, "Email Already Exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Welcome.this, "Could Not Sign In", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    }
                });
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });


    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}

