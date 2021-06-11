package com.example.wibumedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wibumedia.Adapters.NotifyAdapter;
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

public class NotificationFragment extends Fragment {
    View root;
    ApiInterface service;
    private final String key = "VSBG";
    RecyclerView layout_notify;
    ArrayList<Post> postList;

    NotifyAdapter notifyAdapter = null;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_notification, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        loadData();

    }

    private void loadData() {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            service.getPost(key).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    ArrayList<Post> notifyList = new ArrayList<>();
                    postList = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                    for(int i = 0; i < postList.size(); i++) {
                        if((postList.get(i).getUser().getId().equals(Common.currentUser.getId()))){

//                            postList.remove(postList.get(i));
                        }
                        else {
                            notifyList.add(postList.get(i));
                        }
                    }
                    notifyAdapter = new NotifyAdapter(notifyList, NotificationFragment.this);
                    layout_notify.setAdapter(notifyAdapter);

                    notifyAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro : "+ t, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }

    }



    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        layout_notify = view.findViewById(R.id.layout_notify);
        layout_notify.setHasFixedSize(true);
        layout_notify.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}