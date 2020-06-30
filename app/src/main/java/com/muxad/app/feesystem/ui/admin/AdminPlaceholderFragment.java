package com.muxad.app.feesystem.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.muxad.app.feesystem.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdminPlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private AdminViewModel adminViewModel;

    public static AdminPlaceholderFragment newInstance(int index) {
        AdminPlaceholderFragment fragment = new AdminPlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        adminViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fee_details, container, false);
        return root;
    }
}