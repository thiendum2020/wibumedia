package com.example.wibumedia.Fragments;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.wibumedia.Adapters.CommentAdapter;
import com.example.wibumedia.Models.Comment;
import com.example.wibumedia.Models.JSONResponseComment;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostFragment extends Fragment {

    final String key = "VSBG";
    int count = 0;

    CircleImageView imgProfile, img_my_profile;
    TextView tvDisplayname, tvCaption;
    TextView tv_comment_count;
    RoundedImageView imgPost;
    ApiInterface service;
    RecyclerView layout_comment;

    private Toolbar toolbar;
    private ArrayList<Comment> comments;
    private ArrayList<Post> posts;

    CommentAdapter commentAdapter = null;

    String postId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);

        service = Common.getGsonService();

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            postId = bundle.getString("PostID");

            loadData(postId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_post, container, false);
    }




    private void loadData(String postID) {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            service.getDetailPost(key, postID).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                    tvDisplayname.setText(posts.get(0).getUser().getUsername());
                    tvCaption.setText(posts.get(0).getContent());

                    Picasso.get().load("" + posts.get(0).getImage()).into(imgPost);
                    Picasso.get().load("" + posts.get(0).getUser().getAvatar()).into(imgProfile);
                    Picasso.get().load("" + Common.currentUser.getAvatar()).into(img_my_profile);
                }

                @Override
                public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                }
            });

            service.getComment(key, postID).enqueue(new Callback<JSONResponseComment>() {
                @Override
                public void onResponse(Call<JSONResponseComment> call, Response<JSONResponseComment> response) {
                    JSONResponseComment jsonResponseComment = response.body();
                    if (jsonResponseComment.getData() == null) {
                        Toast.makeText(getActivity(), "This Post does not has Comment", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    comments = new ArrayList<>(Arrays.asList(jsonResponseComment.getData()));
                    commentAdapter = new CommentAdapter(comments, DetailPostFragment.this);
                    layout_comment.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseComment> call, Throwable t) {
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        tvDisplayname = view.findViewById(R.id.tv_displayName);
        tvCaption = view.findViewById(R.id.tv_caption);
        imgProfile = view.findViewById(R.id.img_profile);
        imgPost = view.findViewById(R.id.img_post);
        img_my_profile = view.findViewById(R.id.img_my_profile);
        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        layout_comment = view.findViewById(R.id.layout_comment);
        layout_comment.setHasFixedSize(true);
        layout_comment.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postId = bundle.getString("PostID");
        }
    }
}

