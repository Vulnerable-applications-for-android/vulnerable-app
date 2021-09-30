package com.example.android.thesis.vulnerableapp.ui.rule12;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class Rule12ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Rule12ViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("Apps should use the SharedPreferences only in MODE_PRIVATE to prevent other apps from accessing them");
    }

    public LiveData<String> getText() {
        return mText;
    }
}