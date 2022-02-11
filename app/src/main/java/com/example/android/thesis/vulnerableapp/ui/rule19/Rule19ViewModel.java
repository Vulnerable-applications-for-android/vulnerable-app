package com.example.android.thesis.vulnerableapp.ui.rule19;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule19ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule19ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule19 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}