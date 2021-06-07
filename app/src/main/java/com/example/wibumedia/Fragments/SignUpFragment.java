package com.example.wibumedia.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wibumedia.FragmentReplaceActivity;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.R;

public class SignUpFragment extends Fragment {
    private TextView tv_backToLogin;
    private EditText et_username,et_name, et_phone, et_email, et_password, et_confirmPass;
    private Button btn_signUp;
    private ImageButton btn_back;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl(view);
        setEvent();
    }
    private void setControl(View view) {
        tv_backToLogin = view.findViewById(R.id.tv_backToLogin);
        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_name = view.findViewById(R.id.et_name);
        et_phone = view.findViewById(R.id.et_phone);
        et_password = view.findViewById(R.id.et_password);
        et_confirmPass = view.findViewById(R.id.et_confirmPass);
        btn_signUp = view.findViewById(R.id.btn_signUp);
        btn_back = view.findViewById(R.id.btn_back);
    }

    private void setEvent() {

        tv_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();
            }
        });
    }
}
