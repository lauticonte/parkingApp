package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    Locale localizacionUs = new Locale("en", "US");
    Locale localizacionMx = new Locale("es", "MX");

    private EditText usernameEditText;
    private EditText passwordEditText;

//    private TextView singUpButton;
    private TextView mTextViewPassword;
    private Switch mSwitchCambiarIdioma;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.editTextResetMail);
        passwordEditText = (EditText) findViewById(R.id.LoginPassword);
//        mSwitchCambiarIdioma = (Switch) findViewById(R.id.SwitchCambiarIdioma);

//        singUpButton = (TextView) findViewById(R.id.SingUpButton);
        mTextViewPassword = (TextView) findViewById(R.id.TextViewPassword);

        firebaseAuth = FirebaseAuth.getInstance();

//        singUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SingUpActivity.class));
//                finish();
//            }
//        });

        mTextViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
                finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    if(!user.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "Correo electrónico no verificado" + " " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                    }else{
                        goToStart();
                        Toast.makeText(LoginActivity.this, "Pantalla de Inicio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

//        if(mSwitchCambiarIdioma.isChecked()){
//            Locale.setDefault(localizacionUs);
//            Configuration config = new Configuration();
//            config.locale = localizacionUs;
//            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        }else {
//            Locale.setDefault(localizacionMx);
//            Configuration config = new Configuration();
//            config.locale = localizacionMx;
//            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        }

//        mSwitchCambiarIdioma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    mSwitchCambiarIdioma.setSaveEnabled(true);
//                    Locale.setDefault(localizacionUs);
//                    Configuration config = new Configuration();
//                    config.locale = localizacionUs;
//                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//                }
//                else{
//                    mSwitchCambiarIdioma.setChecked(false);
//                    Locale.setDefault(localizacionMx);
//                    Configuration config = new Configuration();
//                    config.locale = localizacionMx;
//                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//                }
//            }
//
//        });
    }

    //Conectar la pagina de inicio
    private void goToStart() {
        Intent intent = new Intent(this, NavViewButton.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void login(View view) {

        //Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if ("".equals(username) || "".equals(password)){
            Toast.makeText(this, "Ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
        }else {

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Las credenciales no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

//    Boton SingUp

    public void singup(View view) {

        //Toast.makeText(this, "Sing Up", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SingUpActivity.class);
        startActivity(intent);

    }
}