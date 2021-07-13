package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.JSONResponseUser;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.WelcomeActivity;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileFragment extends Fragment {
    View root;
    private ImageButton btn_back;
    private TextView tv_done;
    CircleImageView img_profile;
    private EditText edt_username, edt_password, edt_displayName, edt_phoneNumber, edt_email, edt_birthday;
    ApiInterface service;
    private final String key = "VSBG";
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        btn_back = view.findViewById(R.id.btn_back);
        tv_done = view.findViewById(R.id.tv_done);

        img_profile = view.findViewById(R.id.img_profile);

        edt_birthday = view.findViewById(R.id.edt_birthday);
        edt_email = view.findViewById(R.id.edt_email);
        edt_phoneNumber = view.findViewById(R.id.edt_phoneNumber);
        edt_displayName = view.findViewById(R.id.edt_displayName);
        edt_password = view.findViewById(R.id.edt_password);
        edt_username = view.findViewById(R.id.edt_username);
    }

    private void setEvent() {

        Picasso.get().load(Common.currentUser.getAvatar()).resize(140,140)
                .into(img_profile);
        edt_username.setText(Common.currentUser.getUsername());
        edt_password.setText(Common.currentUser.getPassword());
        edt_displayName.setText(Common.currentUser.getName());
        edt_phoneNumber.setText(Common.currentUser.getPhone());
        edt_email.setText(Common.currentUser.getEmail());
        edt_birthday.setText(Common.currentUser.getBirthday());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.edit_profile, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                if (edt_password.getText().toString().trim().isEmpty()) {
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
                }
                else if (edt_displayName.getText().toString().trim().isEmpty()) {
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
                }
                else if (edt_phoneNumber.getText().toString().trim().isEmpty()) {
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
                }else if (edt_email.getText().toString().trim().isEmpty()) {
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
                else if (edt_birthday.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Birthday không để trống !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                }
                else if (!isValidPhone(edt_phoneNumber.getText().toString().trim())) {
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
                }
//                else if (!isValidEmail(edt_email.toString().trim())) {
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
                else if (!isValidBirthday(edt_birthday.getText().toString().trim())) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Birthday không hợp lệ !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                }
                if (check == true) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    builder.setTitle("Thông báo !");
                    builder.setMessage("Bạn có chắc muốn thay đổi các thông tin cá nhân không?");

                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(getContext());
                            progressDialog.setMessage("Uploading...");
                            progressDialog.show();
                            service.updateUser(key, Common.currentUser.getId(),
                                    edt_password.getText().toString(), edt_displayName.getText().toString(),
                                    edt_email.getText().toString(), edt_phoneNumber.getText().toString(),
                                    edt_birthday.getText().toString()).enqueue(new Callback<JSONResponseUser>() {
                                @Override
                                public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                                    // cập nhật dữ liệu mới cho Common.CurrentUser -----------------
                                    Common.currentUser = response.body().getData().get(0);

                                    Fragment someFragment = new ProfileFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.edit_profile, someFragment); // give your fragment container id in first parameter
                                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                                    transaction.commit();
                                }

                                @Override
                                public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Thay đổi thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

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

//    public static boolean isValidEmail(String email) {
//        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//        CharSequence inputString = email;
//        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputString);
//        if (matcher.matches())
//            return true;
//        else
//            return false;
//    }

    public static boolean isValidBirthday(String email) {
        String expression = "\\d{2}[-|/]\\d{2}[-|/]\\d{4}";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
            return true;

        else {
            return false;
        }
    }
}