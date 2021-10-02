package com.example.android.thesis.vulnerableapp.ui.rule2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule2ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}