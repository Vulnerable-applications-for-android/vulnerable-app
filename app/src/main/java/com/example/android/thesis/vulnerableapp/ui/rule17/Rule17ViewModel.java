package com.example.android.thesis.vulnerableapp.ui.rule17;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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