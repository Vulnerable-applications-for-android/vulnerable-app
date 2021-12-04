package com.example.android.thesis.vulnerableapp.ui.rule6;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule6ViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public Rule6ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule6 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
