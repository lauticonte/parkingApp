package com.example.parkingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirebaseUser user;
    public TextView email;
    public TextView usuario;

    ImageView mPlusIrAgregarMetodoPago;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        View vista =  inflater.inflate(R.layout.fragment_blank, container, false);

        usuario = (TextView) vista.findViewById(R.id.textView10);
        usuario.setText(user.getDisplayName());

        email = (TextView) vista.findViewById(R.id.textView26);
        email.setText(user.getEmail());

        mPlusIrAgregarMetodoPago = (ImageView) vista.findViewById(R.id.PlusIrAgregarMetodoPago);


        mPlusIrAgregarMetodoPago.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                goToMetodoPago();
            }
        });

        return vista;
    }

    public void goToMetodoPago(){
        Intent intent = new Intent(getActivity(), AddMetodoPagoActivity.class);
        startActivity(intent);
    }
}