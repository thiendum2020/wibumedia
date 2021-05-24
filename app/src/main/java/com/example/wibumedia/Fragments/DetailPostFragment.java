package com.example.wibumedia.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wibumedia.Adapters.CommentAdapter;
import com.example.wibumedia.Adapters.PostAdapter;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.Models.Comment;
import com.example.wibumedia.Models.JSONResponseComment;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.WelcomeActivity;
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
    CircleImageView imgProfile;
    TextView tvDisplayname, tvCaption;
    RoundedImageView imgPost;
    ApiInterface service;

    RecyclerView layout_comment;

    private Toolbar toolbar;

    private ArrayList<Comment> comments;
    private ArrayList<Post> posts;

    CommentAdapter commentAdapter = null;

    String postId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void loadPost(String postID) {
        service.getDetailPost(key, postID).enqueue(new Callback<JSONResponsePost>() {
            @Override
            public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                JSONResponsePost jsonResponsePost = response.body();
                posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                tvDisplayname.setText(posts.get(0).getUser().getUsername());
                tvCaption.setText(posts.get(0).getContent());

                Picasso.get().load("" + posts.get(0).getImage()).into(imgPost);
                Picasso.get().load("" + posts.get(0).getUser().getAvatar()).into(imgProfile);
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
    }

    public DetailPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_post, container, false);
        tvDisplayname = view.findViewById(R.id.tv_displayName);
        tvCaption = view.findViewById(R.id.tv_caption);
        imgProfile = view.findViewById(R.id.img_profile);
        imgPost = view.findViewById(R.id.img_post);
        layout_comment = view.findViewById(R.id.layout_comment);

        service = Common.getGsonService();

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            postId = bundle.getString("PostID");
            loadPost(postId);
        }

        initToolbars(view);
        return view;
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
//                        Fragment someFragment = new EditDetailPostFragment();
//                        FragmentTransaction transaction = detailPostFragment.getFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
//                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                        transaction.commit();
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

                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                service.deletePost(key, posts.get(0).getId()).enqueue(new Callback<JSONResponsePost>() {
                                    @Override
                                    public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                                        JSONResponsePost jsonResponsePost = response.body();
                                        Toast.makeText(getContext(), "DELETE SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<JSONResponsePost> call, Throwable t) {

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
//                    case R.id.save:
//
//                        builder.setTitle("Confirm");
//                        builder.setMessage("Are you sure?");
//
//                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Do nothing but close the dialog
//                                dialog.dismiss();
//                                startActivity(new Intent(getContext(), MainActivity.class));
//                            }
//                        });
//                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Do nothing
//                                dialog.dismiss();
//                            }
//                        });
//                        alert = builder.create();
//                        alert.show();
//                        break;
                }
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu_edit_post);
    }

}
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_edit_post, menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.delete:
//
//                return true;
//            case R.id.save:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
