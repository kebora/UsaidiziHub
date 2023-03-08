package com.simiyu.youhubpro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class RowFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_row, container, false);
        // Remove the horizontal scroll bar
        HorizontalScrollView horizontalScrollView =
                view.findViewById(R.id.horizontal_counselors_row);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        // Fill the profile card frame layouts
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.profile_card_placeholder, ProfileCardFragment.class, null)
                .commit();
        return view;
    }
}