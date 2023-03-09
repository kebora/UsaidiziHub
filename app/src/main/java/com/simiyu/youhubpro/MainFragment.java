package com.simiyu.youhubpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        // Get the user's name and email from the arguments
//        String userName = getArguments().getString("user_name");
//        String userEmail = getArguments().getString("user_email");

        // Update the UI with the user's name and email
//        TextView usernameTextView = view.findViewById(R.id.textview_username);
//        usernameTextView.setText(userName);

        FrameLayout rowsFragment = view.findViewById(R.id.frame_layout_home_row);
        //Add the fragment
        RowFragment rowFragment = new RowFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_home_row, rowFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return view;
    }
}