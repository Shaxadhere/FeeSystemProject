package com.muxad.app.feesystem;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    private onFragmentBtnSelected listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listener.onButtonSelected();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        }else{
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onFragmentBtnSelected{
        public void onButtonSelected();
    }
}