package com.example.android.thesis.vulnerableapp.ui.rule18;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule18ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule18ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule20 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}