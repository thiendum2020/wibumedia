package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private TextView tv_backToLogin;
    private EditText et_username, et_name, et_phone, et_email, et_password, et_birthday, et_confirmPass;
    private Button btn_signUp;
    private ImageButton btn_back;
    ApiInterface service;
    boolean check = true;


    public static boolean isValidPhone(String phone) {
        String expression = "^[0-9]{10}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
            return true;

        else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
            return true;

        else {
            return false;
        }
    }

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
        service = Common.getGsonService();
        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        //tv_backToLogin = view.findViewById(R.id.tv_backToLogin);
        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_name = view.findViewById(R.id.et_name);
        et_phone = view.findViewById(R.id.et_phone);
        et_birthday = view.findViewById(R.id.et_birthday);
        et_password = view.findViewById(R.id.et_password);
        et_confirmPass = view.findViewById(R.id.et_confirmPass);
        btn_signUp = view.findViewById(R.id.btn_signUp);
        btn_back = view.findViewById(R.id.btn_back);
    }

    private void setEvent() {

        /*tv_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
            }
        });*/
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                if (et_username.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Username không để trống !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (et_name.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Name không để trống !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (et_email.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Email không để trống !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                }
//                else if (!isValidEmail(et_email.toString().trim())) {
//                    check = false;
//                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
//                    b.setTitle("Thông báo !");
//                    b.setMessage("Email không hợp lệ !");
//                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//                    AlertDialog al = b.create();
//                    al.show();
//                }
                else if (et_phone.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Nhập số điện thoại ");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (!isValidPhone(et_phone.getText().toString().trim())) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Định dạng Số điện thoại không đúng ! Nhập lại ");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (et_password.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Password không để trống !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (et_confirmPass.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Mời xác nhận lại mật khẩu !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (!et_password.getText().toString().equals(et_confirmPass.getText().toString())) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Mật khẩu xác nhận không chính xác !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                }
                if(check == true){
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();

                    service.addUser("VSBG" , et_username.getText().toString(), et_password.getText().toString(),
                            et_name.getText().toString(), et_email.getText().toString(),
                            et_phone.getText().toString(), et_birthday.getText().toString()
                    ).enqueue(new Callback<JSONResponseUser>() {
                        @Override
                        public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("asd" , "------------ loi iii -----"+t.getMessage().toString());
                            Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                            ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
                        }
                    });
                }


            }
        });
    }
}
