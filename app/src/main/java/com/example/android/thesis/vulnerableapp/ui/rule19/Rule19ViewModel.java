package com.example.android.thesis.vulnerableapp.ui.rule19;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule19ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule19ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule21 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}