package com.example.android.thesis.vulnerableapp.ui.rule25;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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