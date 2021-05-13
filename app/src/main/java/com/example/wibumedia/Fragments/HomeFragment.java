package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wibumedia.Adapters.PostAdapter;
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

public class HomeFragment extends Fragment {

    final String key = "VSBG";
    RecyclerView layout_post;
    ArrayList<Post> postList;
    PostAdapter postAdapter=null;
    ApiInterface service;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);

        service = Common.getGsonService();

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
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            ProgressDialog mDialog = new ProgressDialog(getActivity().getBaseContext().getApplicationContext());
            service.getPost(key).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    if (jsonResponsePost.getData() == null) {
                        Toast.makeText(getActivity(), "This Home does not has Post", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    postList = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                    Log.e("aaaa","brvdfvdv"+jsonResponsePost.getStatus());

                    postAdapter = new PostAdapter(postList, HomeFragment.this);
                    layout_post.setAdapter(postAdapter);

                    postAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro : "+ t, Toast.LENGTH_SHORT).show();
                }
            });

            //loadJSONPostID();

//            mListView.scheduleLayoutAnimation();

        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
//            return;
        }

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