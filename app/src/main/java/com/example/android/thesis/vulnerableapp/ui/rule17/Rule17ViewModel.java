package com.example.android.thesis.vulnerableapp.ui.rule17;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule17ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule17ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule19 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}