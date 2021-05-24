package com.example.wibumedia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wibumedia.Fragments.DetailPostFragment;
import com.example.wibumedia.Fragments.HomeFragment;
import com.example.wibumedia.Fragments.OtherProfileFragment;
import com.example.wibumedia.Fragments.ProfileFragment;
import com.example.wibumedia.Models.Comment;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.Common;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>  {

    private ArrayList<Comment> list;
    Context context;
    DetailPostFragment detailPostFragment;

    public CommentAdapter(ArrayList<Comment> list, DetailPostFragment detailPostFragment) {
        this.list = list;
        this.detailPostFragment = detailPostFragment;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(detailPostFragment.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment model = list.get(position);

        Picasso.get().load(model.getUser().getAvatar())
                .into(holder.img_profile);
        holder.tv_displayName.setText(model.getUser().getName());
        holder.tv_displayName.setText(model.getContent());

        holder.img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getUser().getId().equals(Common.currentUser.getId())) {
                    Fragment someFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("UserID", String.valueOf(model.getUser().getId()));
                    someFragment.setArguments(bundle);

                    FragmentTransaction transaction = detailPostFragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                } else {
                    Fragment someFragment = new OtherProfileFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("UserID", String.valueOf(model.getUser().getId()));
                    someFragment.setArguments(bundle);

                    FragmentTransaction transaction = detailPostFragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }
            }
        });

        holder.tv_displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getUser().getId().equals(Common.currentUser.getId())) {
                    Fragment someFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("UserID", String.valueOf(model.getUser().getId()));
                    someFragment.setArguments(bundle);

                    FragmentTransaction transaction = detailPostFragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                } else {
                    Fragment someFragment = new OtherProfileFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("UserID", String.valueOf(model.getUser().getId()));
                    someFragment.setArguments(bundle);

                    FragmentTransaction transaction = detailPostFragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

     static class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_profile;
        TextView tv_displayName, tv_comment;

        public CommentViewHolder(@NonNull View itemView) {

            super(itemView);

            img_profile = itemView.findViewById(R.id.img_profile);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_displayName = itemView.findViewById(R.id.tv_displayName);

        }
    }
}


