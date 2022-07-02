package com.example.parkingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EscannerFragment extends Fragment {

    Button mBtnEscannearQR;
    TextView textViewInfoReturnEscanner;

    public EscannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_escanner, container, false);

        textViewInfoReturnEscanner = vista.findViewById(R.id.textViewInfoReturnEscanner);
        mBtnEscannearQR = vista.findViewById(R.id.BtnEscannearQR);

        mBtnEscannearQR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        return vista;
    }

    public void scanCode(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(EscannerFragment.this);
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setMessage(result.getContents());
                builder.setTitle("Scaning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

            }
        }
    }


}