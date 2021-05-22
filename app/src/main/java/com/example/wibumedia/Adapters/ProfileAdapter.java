package com.example.wibumedia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wibumedia.Fragments.DetailPostFragment;
import com.example.wibumedia.Fragments.ProfileFragment;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class ProfileAdapter extends ArrayAdapter<Post> {
    Context context;
    int resource;
    Post post = null;
    ArrayList<Post> data = null;
    PostAdapter adapter;
    ProfileHolder holder = null;
    ProfileFragment profileFragment;

    public ProfileAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> data, ProfileFragment profileFragment) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.profileFragment = profileFragment;
    }

    static class ProfileHolder {
        ImageView post_image;
        public ProfileHolder() {
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row != null) {
            holder = (ProfileAdapter.ProfileHolder) row.getTag();
        } else {
            holder = new ProfileAdapter.ProfileHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.image_item, parent, false);
            holder.post_image = row.findViewById(R.id.imageProfileItem);

            Picasso.get().load(""+data.get(position).getImage()).resize(600,600)
                    .centerCrop()
                    .into(holder.post_image);
            row.setTag(holder);
        }

        //khi click vào 1 hình ảnh trong lưới thì sẽ hiển thị bài đăng chi tiết của ảnh đang click (CHƯA VIẾT)
        holder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new DetailPostFragment();
                Bundle bundle = new Bundle();

                bundle.putString("PostID", String.valueOf(data.get(position).getId()));
                someFragment.setArguments(bundle);

                FragmentTransaction transaction = profileFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return row;
    }
}
