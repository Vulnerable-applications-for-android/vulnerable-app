package com.example.android.thesis.vulnerableapp.ui.rule1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule1ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}