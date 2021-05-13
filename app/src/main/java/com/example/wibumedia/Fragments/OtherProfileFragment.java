package com.example.wibumedia.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherProfileFragment extends Fragment {

    final String key = "VSBG";
    private ArrayList<Post> posts;
    ApiInterface service;

    public OtherProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_profile, container, false);




        Fragment fragment = new Fragment();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String userId = bundle.getString("UserID");

            loadProfile(userId);
        }
    }

    private void loadProfile(String userId) {

        service = Common.getGsonService();
            Log.e("ccc","sadad"+userId);
            //userId  = getIntent().getExtras().getString("UserID");
            service.getPostUserID(key,userId).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    if (jsonResponsePost.getData() == null) {
                        Toast.makeText(getContext(), "This Profile does not has Post", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                    Log.e("aaaa","brvdfvdv"+jsonResponsePost.getStatus());
                    //viết profile adapter để set giao diện cho 1 tấm hình hiển thị ở layout center profile...trong profile adapter thì row = inflater.inflate(R.layout.profile_item_image, parent, false);
                    viewPagerAdapter = new ProfileAdapter(getBaseContext(), R.layout.layout_center_profile, posts);
                    gridViewProfile.setAdapter(viewPagerAdapter);
                    viewPagerAdapter.notifyDataSetChanged();


                }

                @Override
                public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Erro : "+ t, Toast.LENGTH_SHORT).show();
                }
            });

    }
}