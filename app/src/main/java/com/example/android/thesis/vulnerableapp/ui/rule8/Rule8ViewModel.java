package com.example.android.thesis.vulnerableapp.ui.rule8;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule8ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule8ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule8 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}