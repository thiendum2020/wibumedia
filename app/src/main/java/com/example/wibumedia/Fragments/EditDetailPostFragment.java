package com.example.wibumedia.Fragments;

import android.app.ProgressDialog;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class EditDetailPostFragment extends Fragment {
    final String key = "VSBG";

    TextView tv_displayName;
    ExtractEditText tvCaption;
    RoundedImageView imgPost;
    ApiInterface service;
    CircleImageView img_profile;

    Button btn_Sua;


    public EditDetailPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_detail_post, container, false);

        service = Common.getGsonService();

        tvCaption = view.findViewById(R.id.tv_caption_edit);
        tv_displayName = view.findViewById(R.id.tv_displayName_edit);
        img_profile = view.findViewById(R.id.img_profile_edit);
        imgPost = view.findViewById(R.id.img_post_edit);

        btn_Sua = view.findViewById(R.id.btn_Sua);

        Bundle bundle = this.getArguments();
        String postId = bundle.getString("PostID");
//            Picasso.get().load(""+Common.currentUser.getImage()).into(img_profile);

        loadPost(postId);

        btn_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                service.updatePost("VSBG", postId, tvCaption.getText().toString()).enqueue(new Callback<JSONResponsePost>() {
                    @Override
                    public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Success!!!", Toast.LENGTH_SHORT).show();

                        Fragment someFragment = new ProfileFragment();
                        FragmentTransaction transaction = EditDetailPostFragment.this.getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }

                    @Override
                    public void onFailure(Call<JSONResponsePost> call, Throwable t) {

                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadPost(String postID) {
        service.getDetailPost(key, postID).enqueue(new Callback<JSONResponsePost>() {
            @Override
            public void onResponse(Call<JSONResponsePost> call, Response<JSONResponsePost> response) {
                JSONResponsePost jsonResponsePost = response.body();
                ArrayList<Post> posts = new ArrayList<>(Arrays.asList(jsonResponsePost.getData()));
                tv_displayName.setText(posts.get(0).getUser().getUsername());
                tvCaption.setText(posts.get(0).getContent());
                Picasso.get().load("" + posts.get(0).getImage()).into(imgPost);
                Picasso.get().load("" + posts.get(0).getUser().getAvatar()).into(img_profile);
            }

            @Override
            public void onFailure(Call<JSONResponsePost> call, Throwable t) {

            }
        });

    }
}
