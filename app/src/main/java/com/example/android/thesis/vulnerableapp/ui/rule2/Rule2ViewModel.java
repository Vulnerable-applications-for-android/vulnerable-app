package com.example.android.thesis.vulnerableapp.ui.rule2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule2ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}