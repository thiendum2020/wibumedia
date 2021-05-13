package com.example.wibumedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileAdapter extends ArrayAdapter<Post> {
    Context context;
    int resource;
    Post post = null;
    ArrayList<Post> data = null;
    PostAdapter adapter;
    OtherProfileHolder holder = null;


    public OtherProfileAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    class OtherProfileHolder {
        CircleImageView img_profile;
        TextView username, count_like, publisher, description, no_of_comments;
        ImageView post_image, like, comment;

        public OtherProfileHolder() {
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("abc","abcd");
        View row = convertView;


        if (row != null) {
            holder = (OtherProfileHolder) row.getTag();
        } else {
            holder = new OtherProfileHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.image_item, parent, false);


            holder.post_image = row.findViewById(R.id.imageProfileItem);


            row.setTag(holder);
        }

        Post post = data.get(position);
        //holder.username.setText(post.getUser().getUsername());



        //holder.description.setText(post.getContent());

        //Picasso.with(getBaseContext()).load(post.getImage()).into(holder.post_image);
//        Picasso.get().load(data.get(position).getImage())
//                .into(holder.post_image);
        Picasso.get().load("https://dienthoaivui.com.vn/wp-content/uploads/2020/10/hinh-nen-iphone-12-19-1024x1024.jpg")
                .into(holder.post_image);
        /*holder.username.setText(post.getUser().getUsername());
        holder.username.setText(post.getUser().getUsername());
        holder.username.setText(post.getUser().getUsername());*/


        //khi click vào 1 hình ảnh trong lưới thì sẽ hiển thị bài đăng chi tiết của ảnh đang click (CHƯA VIẾT)
        holder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intentHome = new Intent(getContext(), ProfileActivity.class);    //thay thế thành PostActivity...xml là post_item
                Bundle bundle = new Bundle();
                bundle.putString("UserID", String.valueOf(post.getUser().getId()));
                intentHome.putExtras(bundle);
                Log.e("ghi","slasdjsaldjaslsd"+String.valueOf(post.getUser().getId()));
                *//*intentHome.putExtra("UserID",adapter.post.getUser().getId());
                Log.e("def","slaldkasldasl");*//*
                context.startActivity(intentHome);*/
            }
        });

            /*@Override
            public void onClick(View v, int position) {
                //Common.currentUser.setUsername(post.getUser().getUsername());
                Intent intentHome = new Intent(getContext(), ProfileActivity.class);
                intentHome.putExtra("UserID",holder.username.getId());
                context.startActivity(intentHome);
                //getActivity().getFragmentManager().popBackStack();
            }*/


        return row;
    }
}
