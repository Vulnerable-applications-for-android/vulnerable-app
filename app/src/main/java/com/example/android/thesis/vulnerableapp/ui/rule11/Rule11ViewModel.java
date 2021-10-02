package com.example.android.thesis.vulnerableapp.ui.rule11;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule11ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule11ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule11 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}