package com.example.wibumedia.Adapters;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wibumedia.Fragments.DetailPostFragment;
import com.example.wibumedia.Fragments.HomeFragment;
import com.example.wibumedia.Fragments.NotificationFragment;
import com.example.wibumedia.Fragments.OtherProfileFragment;
import com.example.wibumedia.Fragments.ProfileFragment;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.SaveImageHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private ArrayList<Post> list;

    NotificationFragment notificationFragment;
    private static final int PERMISSION_REQUEST_CODE = 1000;

    public NotifyAdapter(ArrayList<Post> list, NotificationFragment notificationFragment) {
        this.list = list;
        this.notificationFragment = notificationFragment;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(notificationFragment.getContext()).inflate(R.layout.notify_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post model = list.get(position);

        Picasso.get().load(model.getUser().getAvatar())
                .into(holder.img_user);
        holder.tv_username.setText(model.getUser().getUsername());
        holder.tv_caption.setText(model.getContent());

        holder.layout_notifyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Fragment someFragment = new DetailPostFragment();
                Bundle bundle = new Bundle();

                bundle.putString("PostID", String.valueOf(list.get(position).getId()));
                someFragment.setArguments(bundle);

                FragmentTransaction transaction = notificationFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_user;
        TextView tv_username, tv_caption;
        LinearLayout layout_notifyItem;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            img_user = itemView.findViewById(R.id.img_user);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            layout_notifyItem = itemView.findViewById(R.id.layout_notifyItem);
        }
    }
}
