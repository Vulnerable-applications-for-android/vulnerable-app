package com.example.android.thesis.vulnerableapp.ui.rule11;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule11ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule11ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule11 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}