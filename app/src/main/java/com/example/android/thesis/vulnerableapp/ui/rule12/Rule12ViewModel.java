package com.example.android.thesis.vulnerableapp.ui.rule12;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule12ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule12ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule12 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}