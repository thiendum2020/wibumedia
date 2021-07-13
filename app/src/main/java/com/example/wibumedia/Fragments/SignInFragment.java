package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.wibumedia.FragmentReplaceActivity;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.JSONResponseUser;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {
    private TextView tv_create;
    private EditText et_username, et_password;
    private Button btn_signin;
    boolean check = true;
    final String key = "VSBG";
    ApiInterface service;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        tv_create = view.findViewById(R.id.tv_create);
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        btn_signin = view.findViewById(R.id.btn_signin);

    }

    private void setEvent() {
        service = Common.getGsonService();

        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignUpFragment());
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.isConnectedToInternet(getActivity().getBaseContext())){

                    check = true;
                    if (et_username.getText().toString().trim().isEmpty()) {
                        check = false;
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setTitle("Thông báo !");
                        b.setMessage("Username không được để trống !");
                        b.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog al = b.create();
                        al.show();
                    }
                    else if (et_password.getText().toString().trim().isEmpty()) {
                        check = false;
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setTitle("Thông báo !");
                        b.setMessage("Password không được để trống !");
                        b.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog al = b.create();
                        al.show();
                    }
                    if(check == true) {
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Wait a second...");
                        progressDialog.show();

                        service.getUserLogin(key, et_username.getText().toString(), et_password.getText().toString()).enqueue(new Callback<JSONResponseUser>() {
                            @Override
                            public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                                progressDialog.dismiss();
                                if(Integer.parseInt(response.body().getStatus())==1)
                                {
                                    Common.currentUser = response.body().getData().get(0);
                                    Toast.makeText(getContext(), "Sign in successfully !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }else{
                                    Toast.makeText(getContext(), "Sign in failed !", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity().getBaseContext().getApplicationContext(), ""+t.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                }else{
                    Toast.makeText(getActivity().getBaseContext(), "Please check your connection !!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
