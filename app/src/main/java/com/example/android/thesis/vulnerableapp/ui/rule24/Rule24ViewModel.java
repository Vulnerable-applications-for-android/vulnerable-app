package com.example.android.thesis.vulnerableapp.ui.rule24;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule24ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule24ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule24 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
