package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText mEditTextResetMail;
    private Button mBtnRecet;
    private Dialog resetDialog;

    private String email = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        mEditTextResetMail = (EditText) findViewById(R.id.editTextResetMail);
        mBtnRecet = (Button) findViewById(R.id.btnReset);
        resetDialog = new Dialog(this);

        mBtnRecet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEditTextResetMail.getText().toString();
                if(!email.isEmpty()){
                    resetPassword();
                }else {
                    Toast.makeText(ResetPassword.this, "Debe ingresar el Correo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void resetPassword(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    openDilogReset();
                }else{
                    Toast.makeText(ResetPassword.this, "No se pudo enviar el correo de restableser contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDilogReset(){
        resetDialog.setContentView(R.layout.confirm_password_dialog);
        resetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnAceptar = resetDialog.findViewById(R.id.BtnAceptarAlertPassword);
        resetDialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}