package com.example.wibumedia.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wibumedia.FragmentReplaceActivity;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.R;

public class SignInFragment extends Fragment {
    private TextView tv_create, tv_forgotPass, tv_skip;
    private EditText et_email, et_password;
    private Button btn_signin;

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
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btn_signin = view.findViewById(R.id.btn_signin);

    }

    private void setEvent() {

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
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();

            }
        });
    }
}
