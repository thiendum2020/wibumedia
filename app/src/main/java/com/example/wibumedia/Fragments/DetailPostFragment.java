package com.example.wibumedia.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostFragment extends Fragment {

    final String key = "VSBG";

    CircleImageView imgProfile, img_my_profile;
    TextView tvDisplayname, tvCaption;
    TextView tv_comment_count;
    ImageButton btn_send;
    RoundedImageView imgPost;
    ApiInterface service;
    RecyclerView layout_comment;
    EditText edt_comment;
    Post post_id;
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

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edt_comment.getText().toString().trim().isEmpty()){
                        Toast.makeText(getContext(), "Hãy nhập gì đó để comment bài viết này !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Uploading...");
                        progressDialog.show();

                        service.addComment(key, "" + edt_comment.getText().toString(), ""+Common.currentUser.getId(), ""+post_id.getId()).enqueue(new Callback<JSONResponseComment>() {
                            @Override
                            public void onResponse(Call<JSONResponseComment> call, Response<JSONResponseComment> response) {
                                progressDialog.dismiss();
                                getFragmentManager().beginTransaction().detach(DetailPostFragment.this).attach(DetailPostFragment.this).commit();

                            }

                            @Override
                            public void onFailure(Call<JSONResponseComment> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Failed to Comment!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_post, container, false);
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
        btn_send = view.findViewById(R.id.btn_send);
        edt_comment = view.findViewById(R.id.edt_comment);

        layout_comment = view.findViewById(R.id.layout_comment);
        layout_comment.setHasFixedSize(true);
        layout_comment.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postId = bundle.getString("PostID");
        }
    }

    private void loadData(String postID) {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            service.getDetailPost(key, postID).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                    post_id = posts.get(0);
                    tvDisplayname.setText(post_id.getUser().getUsername());
                    tvCaption.setText(post_id.getContent());

                    Picasso.get().load("" + post_id.getImage()).into(imgPost);
                    Picasso.get().load("" + post_id.getUser().getAvatar()).into(imgProfile);
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
                    String cmt = (jsonResponseComment.getData().length) + "";
                    if (jsonResponseComment.getData() == null) {
                        Toast.makeText(getActivity(), "This Post does not has Comment", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tv_comment_count.setText(cmt);

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
}

