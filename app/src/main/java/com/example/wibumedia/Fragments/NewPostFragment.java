package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.R;
import com.example.wibumedia.ReadPathUtil;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class NewPostFragment extends Fragment {
    private TextView tv_displayName, tv_address, tv_addImage, tv_tagFriend, tv_live, tv_location, tv_done;
    private TextInputEditText et_caption;
    private CircleImageView img_profile;
    private ImageButton btn_back;
    private Button addPhoto, btnUpload;
    private ImageView imageViewPic;
    String IMAGE_PATH = "";
    private final int PICK_IMAGE_REQUEST = 77;
    Uri saveUri;
    ApiInterface service;

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
        service = Common.getGsonService();
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        btn_back = view.findViewById(R.id.btn_back);
        tv_done = view.findViewById(R.id.tv_done);
        tv_displayName = view.findViewById(R.id.tv_displayName);
        tv_address = view.findViewById(R.id.tv_address);
        img_profile = view.findViewById(R.id.img_profile);

        imageViewPic = view.findViewById(R.id.imageViewPicture);
        addPhoto = view.findViewById(R.id.btn_addImage);
//        btnUpload = view.findViewById(R.id.btnUpload);

        et_caption = view.findViewById(R.id.et_caption);

    }

    private void setEvent() {
        tv_displayName.setText(Common.currentUser.getName());
        Picasso.get().load(Common.currentUser.getAvatar()).into(img_profile);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseIamge();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadImage();
                if (IMAGE_PATH.equals("")) {
                    Toast.makeText(getContext(), "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();

                    File file = new File(IMAGE_PATH);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                    service.addPost("VSBG", "" + et_caption.getText(), body, "" + Common.currentUser.getId()).enqueue(new Callback<JSONResponsePost>() {
                        @Override
                        public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "cc!", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
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

            Picasso.get().load(saveUri)
                    .into(imageViewPic);
//            Picasso.with(getContext()).load(saveUri).into(imageViewPic);

        }
//            btnSelectImage.setText("Image Selected");
//            btnUploadImage.setEnabled(true);
    }

}