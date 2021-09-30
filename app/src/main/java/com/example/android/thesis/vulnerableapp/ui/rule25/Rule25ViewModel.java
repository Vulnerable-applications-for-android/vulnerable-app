package com.example.android.thesis.vulnerableapp.ui.rule25;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule25ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule25ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule5 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}