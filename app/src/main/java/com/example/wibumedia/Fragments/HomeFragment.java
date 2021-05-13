package com.example.wibumedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wibumedia.Adapters.PostAdapter;
import com.example.wibumedia.Models.PostModel;
import com.example.wibumedia.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView layout_post;
    ArrayList<PostModel> postList;
    PostAdapter postAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, getContext());
        layout_post.setAdapter(postAdapter);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    private void loadData() {
        postList.add(new PostModel(R.drawable.img_profile_1,R.drawable.post1,"thien.wibu","Viet Nam", "Nobita Sasuke SonGoKu Vegeta Sakura.","100","50"));
        postList.add(new PostModel(R.drawable.img_profile_2,R.drawable.post2,"thang.wibu","Nhat Ban", "Nobita Sasuke SonGoKu Vegeta Sakura.","50","100"));
        postList.add(new PostModel(R.drawable.img_profile_3,R.drawable.post3,"thuan.wibu","Han Quoc", "Nobita Sasuke SonGoKu Vegeta Sakura.","200","20"));
        postList.add(new PostModel(R.drawable.img_profile_4,R.drawable.post4,"phu.wibu","Trung Quoc", "Nobita Sasuke SonGoKu Vegeta Sakura.","20","1"));
        postList.add(new PostModel(R.drawable.img_profile_5,R.drawable.post5,"tan.wibu","Trieu Tien", "Nobita Sasuke SonGoKu Vegeta Sakura.","10","10"));
        postAdapter.notifyDataSetChanged();
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        layout_post = view.findViewById(R.id.layout_post);
        layout_post.setHasFixedSize(true);
        layout_post.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}