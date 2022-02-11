package com.example.android.thesis.vulnerableapp.ui.rule9;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule9ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule9ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule9 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
