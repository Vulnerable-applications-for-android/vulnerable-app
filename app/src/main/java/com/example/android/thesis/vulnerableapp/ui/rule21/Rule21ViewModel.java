package com.example.android.thesis.vulnerableapp.ui.rule21;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule21ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule21ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule23 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}