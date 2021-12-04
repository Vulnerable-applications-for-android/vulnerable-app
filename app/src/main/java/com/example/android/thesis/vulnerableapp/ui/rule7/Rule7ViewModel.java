package com.example.android.thesis.vulnerableapp.ui.rule7;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rule7ViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public Rule7ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rule7 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
