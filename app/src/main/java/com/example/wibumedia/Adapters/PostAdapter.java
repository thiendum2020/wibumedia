package com.example.wibumedia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wibumedia.Fragments.HomeFragment;
import com.example.wibumedia.Fragments.OtherProfileFragment;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private ArrayList<Post> list;
    Context context;
    HomeFragment homeFragment;
    boolean clicked = false;
    int likeCount;

    public PostAdapter(ArrayList<Post> list, HomeFragment homeFragment) {
        this.list = list;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeFragment.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post model = list.get(position);


        //holder.img_post.setImageResource(model.getImage());
        Picasso.get().load(list.get(position).getImage())
                .into(holder.img_post);
        holder.tv_displayName.setText(model.getUser().getUsername());
        //holder.tv_address.setText(model.getTv_address());
        holder.tv_caption.setText(model.getContent());
        //holder.tv_like.setText(model.getTv_like());
        //holder.tv_comment.setText(model.getTv_comment());

        holder.img_post.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!clicked) {
                    holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.tv_like.getText() + "");
                    //holder.like.setText(likeCount++);
                    clicked = true;
                } else {
                    holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_red, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.tv_like.getText() + "");
                    // holder.like.setText(Integer.parseInt(model.getLike())-1);
                    clicked = false;
                }
                return false;
            }
        });

        holder.img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!clicked) {
                    holder.img_save.setImageResource(R.drawable.saved);
                    clicked = true;
                } else {
                    holder.img_save.setImageResource(R.drawable.saved);
                    clicked = false;
                }
            }
        });

        holder.tv_displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((FragmentReplaceActivity) homeFragment.getActivity()).setFragment(new OtherProfileFragment());
                Fragment someFragment = new OtherProfileFragment();
                Bundle bundle = new Bundle();

                bundle.putString("UserID", String.valueOf(model.getUser().getId()));
                FragmentTransaction transaction = homeFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_profile;
        RoundedImageView img_post;
        ImageButton img_save;
        TextView tv_displayName, tv_address, tv_caption, tv_like, tv_comment;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            img_profile = itemView.findViewById(R.id.img_profile);
            img_post = itemView.findViewById(R.id.img_post);
            img_save = itemView.findViewById(R.id.img_save);
            tv_displayName = itemView.findViewById(R.id.tv_displayName);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            tv_like = itemView.findViewById(R.id.tv_like);
            tv_comment = itemView.findViewById(R.id.tv_comment);
        }
    }
}
