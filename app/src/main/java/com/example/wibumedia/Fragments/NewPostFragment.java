package com.example.wibumedia.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wibumedia.FragmentReplaceActivity;
import com.example.wibumedia.MainActivity;
import com.example.wibumedia.R;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewPostFragment extends Fragment {
    private TextView tv_displayName, tv_address, tv_addImage, tv_tagFriend, tv_live, tv_location, tv_done;
    private TextInputEditText et_caption;
    private CircleImageView img_profile;
    private ImageButton btn_back;
    NewPostFragment newPostFragment;
    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        btn_back = view.findViewById(R.id.btn_back);
        tv_done = view.findViewById(R.id.tv_done);
        tv_displayName = view.findViewById(R.id.tv_displayName);
        tv_address = view.findViewById(R.id.tv_address);
        img_profile = view.findViewById(R.id.img_profile);
        tv_addImage = view.findViewById(R.id.tv_addImage);
        tv_tagFriend = view.findViewById(R.id.tv_tagFriend);
        tv_live = view.findViewById(R.id.tv_live);
        tv_location = view.findViewById(R.id.tv_location);
        et_caption = view.findViewById(R.id.et_caption);

    }

    private void setEvent() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new HomeFragment();
                FragmentTransaction transaction = newPostFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.newPostFragment, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new HomeFragment();
                FragmentTransaction transaction = newPostFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.newPostFragment, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
    }

}