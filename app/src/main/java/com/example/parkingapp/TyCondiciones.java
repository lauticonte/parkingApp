package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TyCondiciones extends AppCompatActivity {

    private Button mBtnReturnAutenticacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ty_condiciones);

        mBtnReturnAutenticacion = (Button) findViewById(R.id.btnReturnAutenticacion);

        mBtnReturnAutenticacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TyCondiciones.this, SingUpActivity.class));
                finish();
            }
        });
    }
}