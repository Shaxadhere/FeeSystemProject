package com.muxad.app.feesystem.ui.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}