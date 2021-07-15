package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wibumedia.Adapters.CommentAdapter;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.Comment;
import com.example.wibumedia.Models.JSONResponseComment;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostFragment extends Fragment {

    final String key = "VSBG";

    CircleImageView imgProfile, img_my_profile;
    TextView tvDisplayname, tvCaption;
    TextView tv_comment_detail;
    ImageButton btn_send, btn_back;
    RoundedImageView imgPost;
    ApiInterface service;
    RecyclerView layout_comment;
    EditText edt_comment;
    Post post_id;
    private Toolbar toolbar;
    private ArrayList<Comment> comments;
    private ArrayList<Post> posts;
    String user_id = "";

    CommentAdapter commentAdapter = null;

    String postId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
        service = Common.getGsonService();
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            postId = bundle.getString("PostID");

            loadData(view, postId);

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edt_comment.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập gì đó để comment bài viết này !", Toast.LENGTH_SHORT).show();
                    } else {
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Uploading...");
                        progressDialog.show();

                        service.addComment(key, "" + edt_comment.getText().toString(), "" + Common.currentUser.getId(), "" + post_id.getId()).enqueue(new Callback<JSONResponseComment>() {
                            @Override
                            public void onResponse(Call<JSONResponseComment> call, Response<JSONResponseComment> response) {
                                progressDialog.dismiss();
                                edt_comment.setText("");
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

        tvDisplayname = view.findViewById(R.id.tv_displayName);
        btn_back = view.findViewById(R.id.btn_back);
        tvCaption = view.findViewById(R.id.tv_caption);
        imgProfile = view.findViewById(R.id.img_profile);
        imgPost = view.findViewById(R.id.img_post);
        img_my_profile = view.findViewById(R.id.img_my_profile);

        tv_comment_detail = view.findViewById(R.id.tv_comment_detail);

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

    private void setEvent() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new HomeFragment();
                FragmentTransaction transaction = DetailPostFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
    }

    private void loadData(View view, String postID) {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            service.getDetailPost(key, postID).enqueue(new Callback<JSONResponsePost>() {
                @Override
                public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                    JSONResponsePost jsonResponsePost = response.body();
                    posts = jsonResponsePost.getData();
                    post_id = posts.get(0);

                    Log.e("id1", "" + post_id.getUser().getId());
                    Log.e("id2", "" + Common.currentUser.getId());
                    if (Common.currentUser.getId().equals(post_id.getUser().getId())) {
                        initToolbars(view);
                    }

                    tvDisplayname.setText(post_id.getUser().getUsername());
                    tvCaption.setText(post_id.getContent());

                    tv_comment_detail.setText(post_id.getComment_count());

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
                    if (jsonResponseComment.getData() == null) {
                        Toast.makeText(getActivity(), "This Post does not has Comment", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    comments = jsonResponseComment.getData();
                    commentAdapter = new CommentAdapter(comments, DetailPostFragment.this);
                    layout_comment.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseComment> call, Throwable t) {
                    Toast.makeText(getActivity(), "FAILED ! This Post can not load Comment", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initToolbars(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog alert;
                switch (item.getItemId()) {
                    case R.id.edit:
                        Fragment someFragment = new EditDetailPostFragment();
                        Bundle bundle = new Bundle();

                        bundle.putString("PostID", postId);
                        someFragment.setArguments(bundle);

                        FragmentTransaction transaction = DetailPostFragment.this.getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                        Toast.makeText(getContext(), "EDIT DETAIL POST!", Toast.LENGTH_SHORT).show();


                        break;
                    case R.id.delete:

                        builder.setTitle("Thông báo !");
                        builder.setMessage("Bạn thực sự muốn xóa bài viết này ?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                service.deletePost(key, posts.get(0).getId()).enqueue(new Callback<JSONResponsePost>() {
                                    @Override
                                    public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
//                                        JSONResponsePost jsonResponsePost = response.body();
                                        Toast.makeText(getContext(), "DELETE SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                                        Toast.makeText(getContext(), "DELETE FAILED!", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                dialog.dismiss();

                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                        alert = builder.create();
                        alert.show();

                        break;
                }
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.menu_edit_post);
    }

    public void showAlertDialog(String id, String content) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Enter your new comment : ");

        final EditText edtComment = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        edtComment.setLayoutParams(lp);
        edtComment.setText(content);
        alertDialog.setView(edtComment);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtComment.getText().toString().trim().equals("")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    builder.setTitle("Thông báo !");
                    builder.setMessage("Bạn chưa nhập gì để sửa comment này !");

                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    service.updateComment(key, id, edtComment.getText().toString()).enqueue(new Callback<JSONResponsePost>() {
                        @Override
                        public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                            getFragmentManager().beginTransaction().detach(DetailPostFragment.this).attach(DetailPostFragment.this).commit();
                            Toast.makeText(getContext(), "Update comment successful !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<JSONResponsePost> call, Throwable t) {
                            Toast.makeText(getContext(), "Update comment failed !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}

