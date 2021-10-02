package com.example.android.thesis.vulnerableapp.ui.rule21;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule21ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule21ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule23 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}