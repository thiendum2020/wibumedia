package com.example.wibumedia.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wibumedia.R;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.WelcomeActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    View root;

    ApiInterface service;
    final String key = "VSBG";


    private Button btn_editProfile;
    private TextView tv_username, tv_displayName, tv_gmail;
    private ImageButton btn_logOut;
    private CircleImageView img_profile;
    private GridView gridView_post;

    public ProfileFragment() {
        // Required empty public constructor
        // này của t mới sửa.. đừng đụng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = Common.getGsonService();

        setControl(view);
        setEvent();
    }


    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        btn_editProfile = view.findViewById(R.id.btn_editProfile);

        tv_username = view.findViewById(R.id.tv_username);
        tv_displayName = view.findViewById(R.id.tv_displayName);
        tv_gmail = view.findViewById(R.id.tv_gmail);
        img_profile = view.findViewById(R.id.img_profile);

        btn_logOut = view.findViewById(R.id.btn_logOut);
        gridView_post = view.findViewById(R.id.gridView_post);

    }

    private void setEvent() {

        tv_username.setText(Common.currentUser.getUsername());
        tv_displayName.setText(Common.currentUser.getName());
        tv_gmail.setText(Common.currentUser.getEmail());
//        Picasso.get().load(Common.currentUser.get).into(img_profile);

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new EditProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.profile, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        Common.currentUser = null;
                        startActivity(new Intent(getContext(), WelcomeActivity.class));
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}