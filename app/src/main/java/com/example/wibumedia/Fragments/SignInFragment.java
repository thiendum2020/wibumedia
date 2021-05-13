package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
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
    private TextView tv_create, tv_forgotPass, tv_skip;
    private EditText et_username, et_password;
    private Button btn_signin;

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
        tv_forgotPass = view.findViewById(R.id.tv_forgotPass);
        tv_skip = view.findViewById(R.id.tv_skip);
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
        tv_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((FragmentReplaceActivity) getActivity()).setFragment(new ForgotPasswordFragment());
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();


                if(Common.isConnectedToInternet(getActivity().getBaseContext())){
                    ProgressDialog mDialog = new ProgressDialog(getActivity().getBaseContext().getApplicationContext());

                    service.getUserLogin(key, et_username.getText().toString(), et_password.getText().toString()).enqueue(new Callback<JSONResponseUser>() {
                        @Override
                        public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                            if(Integer.parseInt(response.body().getStatus())==1)
                            {
                                Common.currentUser.setUsername(et_username.getText().toString());

                                Toast.makeText(getContext(), "Sign in successfully !", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getContext(), MainActivity.class));

//                                Intent intentHome = new Intent(getContext(), SignInFragment.class);
//                                startActivity(intentHome);
//                                getActivity().getFragmentManager().popBackStack();
                            }else{
                                Toast.makeText(getContext(), "Sign in failed !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                            Toast.makeText(getActivity().getBaseContext().getApplicationContext(), ""+t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(getActivity().getBaseContext(), "Please check your connection !!", Toast.LENGTH_SHORT).show();
//                    return;
                }

            }
        });
    }
}
