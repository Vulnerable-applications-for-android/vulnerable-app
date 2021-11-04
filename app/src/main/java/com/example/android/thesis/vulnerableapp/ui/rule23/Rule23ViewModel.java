package com.example.android.thesis.vulnerableapp.ui.rule23;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule23ViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public Rule23ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule23 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
