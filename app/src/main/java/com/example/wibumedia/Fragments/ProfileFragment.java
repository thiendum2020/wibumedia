package com.example.wibumedia.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wibumedia.Adapters.OtherProfileAdapter;
import com.example.wibumedia.Adapters.ProfileAdapter;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.WelcomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    View root;

    ApiInterface service;
    final String key = "VSBG";


    private Button btn_editProfile;
    private TextView tv_username, tv_displayName, tv_gmail, tv_birthday;
    private ImageButton btn_logOut;
    ProfileAdapter viewPagerAdapter = null;

    private CircleImageView img_profile;
    private GridView gridView_post;

    public ProfileFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
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

        btn_editProfile = view.findViewById(R.id.btn_editprofile);
        tv_displayName = view.findViewById(R.id.tv_displayName);
        tv_birthday = view.findViewById(R.id.tv_birthday);
        img_profile = view.findViewById(R.id.img_profile);

        btn_logOut = view.findViewById(R.id.btn_logOut);
        gridView_post = view.findViewById(R.id.gridView_post);

    }

    private void setEvent() {
        tv_displayName.setText(Common.currentUser.getName());
        tv_birthday.setText(Common.currentUser.getBirthday());
        Picasso.get().load(Common.currentUser.getAvatar()).resize(500, 500)
                .into(img_profile);
        loadMyProfile(Common.currentUser.getId());

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new EditProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.profile, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Thông báo !");
                builder.setMessage("Bạn có chắc muốn đăng xuất không?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        Common.currentUser = null;
                        startActivity(new Intent(getContext(), WelcomeActivity.class));
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void loadMyProfile(String userId) {
        service.getPostUserID(key, userId).enqueue(new Callback<JSONResponsePost>() {
            @Override
            public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                JSONResponsePost jsonResponsePost = response.body();
                if (jsonResponsePost.getData() == null) {
                    Toast.makeText(getContext(), "This Profile does not has any Post", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Post> posts = jsonResponsePost.getData();

                tv_displayName.setText(Common.currentUser.getName());
                tv_birthday.setText(Common.currentUser.getBirthday());

                //viết profile adapter để set giao diện cho 1 tấm hình hiển thị ở layout center profile...trong profile adapter thì row = inflater.inflate(R.layout.profile_item_image, parent, false);
                viewPagerAdapter = new ProfileAdapter(getActivity().getBaseContext(), R.layout.fragment_other_profile, posts, ProfileFragment.this);
                gridView_post.setAdapter(viewPagerAdapter);
                viewPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                Toast.makeText(getContext(), "Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });

    }
}