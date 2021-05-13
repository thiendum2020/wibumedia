package com.example.wibumedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wibumedia.Fragments.HomeFragment;
import com.example.wibumedia.Models.PostModel;
import com.example.wibumedia.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private ArrayList<PostModel> list;
    Context context;
    boolean clicked = false;
    int likeCount;

    public PostAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel model = list.get(position);

        holder.img_profile.setImageResource(model.getImg_profile());
        holder.img_post.setImageResource(model.getImg_post());
        holder.tv_displayName.setText(model.getTv_displayName());
        holder.tv_address.setText(model.getTv_address());
        holder.tv_caption.setText(model.getTv_caption());
        holder.tv_like.setText(model.getTv_like());
        holder.tv_comment.setText(model.getTv_comment());

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
