package com.example.android.thesis.vulnerableapp.ui.rule28;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule28ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule28ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule28 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}