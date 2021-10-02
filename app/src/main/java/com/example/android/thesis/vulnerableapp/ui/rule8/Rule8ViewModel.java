package com.example.android.thesis.vulnerableapp.ui.rule8;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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