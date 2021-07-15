package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.wibumedia.FragmentReplaceActivity;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.JSONResponseUser;
import com.example.wibumedia.R;
import com.example.wibumedia.ReadPathUtil;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SignUpFragment extends Fragment {
    //    private TextView tv_backToLogin;
    private EditText et_username, et_name, et_phone, et_email, et_password, et_birthday, et_confirmPass;
    private Button btn_signUp, btn_addPhoto ;
    private ImageButton btn_back;
    ApiInterface service;
    boolean check = true;
    private final int PICK_IMAGE_REQUEST = 77;
    String IMAGE_PATH = "";
    Uri saveUri;

    public static boolean isValidPhone(String phone) {
        String regex = "^[0-9]{10}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
            return true;

        else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        Log.d("regex", "" + inputString + " - " + matcher.matches());
        if (matcher.matches())
            return true;
        else
            return false;
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

        btn_addPhoto = view.findViewById(R.id.btn_addImage);
    }

    private void chooseIamge() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            saveUri = data.getData();
            IMAGE_PATH = ReadPathUtil.getPath(getContext(), saveUri);

//            Picasso.get().load(saveUri)
//                    .into(imageViewPic);
//            Picasso.with(getContext()).load(saveUri).into(imageViewPic);

        }
//            btnSelectImage.setText("Image Selected");
//            btnUploadImage.setEnabled(true);
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


        btn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseIamge();
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
                } else if (!isValidEmail(et_email.getText().toString().trim())) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Email không hợp lệ !");
                    b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                } else if (et_phone.getText().toString().trim().isEmpty()) {
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
                } else if (et_birthday.getText().toString().trim().isEmpty()) {
                    check = false;
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo !");
                    b.setMessage("Mời nhập ngày sinh !");
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
                else if (IMAGE_PATH.equals("")) {
                    check = false;
                    Toast.makeText(getContext(), "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Thông báo");
                    b.setMessage("Chưa chọn ảnh!");
                    b.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog al = b.create();
                    al.show();
                }
                if (check == true) {
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();

                    File file = new File(IMAGE_PATH);
                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

                    service.addUser("VSBG", et_username.getText().toString(), et_password.getText().toString(),
                            et_name.getText().toString(), et_email.getText().toString(),
                            et_phone.getText().toString(), et_birthday.getText().toString(), body
                    ).enqueue(new Callback<JSONResponseUser>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                            progressDialog.dismiss();
                            if (response.body().getStatus().equals("1")) {
                                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity().getApplicationContext(), SignUpFragment.class));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Create new Account Failed !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("asd", "------------ loi iii -----" + t.getMessage());
                            Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                            ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
                        }
                    });
                }
            }
        });
    }
}
