package com.example.wibumedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wibumedia.Adapters.OtherProfileAdapter;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherProfileFragment extends Fragment {

    final String key = "VSBG";
    private ArrayList<Post> posts;
    ApiInterface service;
    GridView gridViewProfile;
    Context context;
    FragmentManager fragmentManager;

    OtherProfileAdapter viewPagerAdapter = null;
    TextView tv_username, tv_displayName, tv_birthday_other;
    private CircleImageView img_profile;

    public OtherProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        service = Common.getGsonService();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_other_profile, container, false);
        tv_username = view.findViewById(R.id.tv_username_other);
        tv_displayName = view.findViewById(R.id.tv_displayName_other);
        tv_birthday_other = view.findViewById(R.id.tv_birthday_other);
        img_profile = view.findViewById(R.id.img_profile_other);
        gridViewProfile = view.findViewById(R.id.gridView_post_other);

        //Fragment fragment = new Fragment();
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            String userId = bundle.getString("UserID");
//            Picasso.get().load(""+Common.currentUser.getImage()).into(img_profile);
            loadProfile(userId);
        }
        return view;
    }

    private void loadProfile(String userId) {
            service.getPostUserID(key,userId).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    if (jsonResponsePost.getData() == null) {
                        Toast.makeText(getContext(), "This Profile does not has any Post", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));

                    tv_username.setText(posts.get(0).getUser().getUsername());
                    tv_displayName.setText(posts.get(0).getUser().getName());
                    tv_birthday_other.setText(posts.get(0).getUser().getBirthday());

                    //viết profile adapter để set giao diện cho 1 tấm hình hiển thị ở layout center profile...trong profile adapter thì row = inflater.inflate(R.layout.profile_item_image, parent, false);
                    viewPagerAdapter = new OtherProfileAdapter(getActivity().getBaseContext(), R.layout.fragment_other_profile, posts);
                    gridViewProfile.setAdapter(viewPagerAdapter);
                    viewPagerAdapter.notifyDataSetChanged();


                }

                @Override
                public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                    Toast.makeText(getContext(), "Error : "+ t, Toast.LENGTH_SHORT).show();
                }
            });

    }

}