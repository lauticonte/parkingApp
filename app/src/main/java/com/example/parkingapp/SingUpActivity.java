package com.example.parkingapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText password2EditText;
    private CheckBox mChekBoxTerminos;
    private Dialog emailDialog;

    private TextView mTextViewLogin;
    private TextView mTextViewAutenticacion;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        nameEditText = (EditText) findViewById(R.id.Username);
        usernameEditText = (EditText) findViewById(R.id.SingUpMail);
        passwordEditText = (EditText) findViewById(R.id.SingUpPassword);
        password2EditText = (EditText) findViewById(R.id.SingUpPassword2);
        mChekBoxTerminos = (CheckBox) findViewById(R.id.checkBoxTerminos);
        mTextViewLogin = (TextView) findViewById(R.id.textViewLogin);
        mTextViewAutenticacion = (TextView) findViewById(R.id.textViewAutenticacion);
        emailDialog = new Dialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this, LoginActivity.class));
                finish();
            }
        });

        mTextViewAutenticacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this, TyCondiciones.class));
                finish();
            }
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            //Verificar si el usuario sigue conectado o no

            if (user != null){
                Toast.makeText(SingUpActivity.this, "Verificar correo electronico", Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(SingUpActivity.this, "El usuario salio de la sesion", Toast.LENGTH_SHORT).show();
            }
        };




    }

    //Acción del botón de Sing Up

    public void singup(View view) {

        //Verificar si el boton funciona
        //Toast.makeText(this, "Sing Up", Toast.LENGTH_SHORT).show();

        String name = nameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String password2 = password2EditText.getText().toString();

        //Validador de los campos (Todos deben llenarse)

        if("".equals(username) || "".equals(password) || "".equals(name) || "".equals(password2)){
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }else {
            //Validar si las contraseñas coinciden
            if (!password.equals(password2)){
                Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            }else{
                //Validar que la contraseña tenga más de 8 caracteres
                if(password.length()<8){
                    Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                }else{
                    // Validar los terminos y condiciones
                    if(mChekBoxTerminos.isChecked() == false){
                        Toast.makeText(this, "Debe Aceptar Terminos y Condiciones", Toast.LENGTH_SHORT).show();
                    }else{
                        //Completar el registro en la base de datos (firebase) y esperar la confirmacion del correo
                        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(SingUpActivity.this, "Hubo un error al crear el usuario", Toast.LENGTH_SHORT).show();
                                }else{

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    user.sendEmailVerification();
                                    firebaseAuth.signOut();
                                    openDilogEmail();

                                }
                            }
                        });
                    }
                }
            }
        }
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

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openDilogEmail(){
        emailDialog.setContentView(R.layout.confirm_email_dialog);
        emailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnAceptar = emailDialog.findViewById(R.id.BtnAceptarAlertPassword);
        emailDialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

    }

}