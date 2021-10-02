package com.example.android.thesis.vulnerableapp.ui.rule21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.thesis.vulnerableapp.R;

public class Rule21Fragment extends Fragment {

    private Rule21ViewModel rule21ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rule21ViewModel =
                ViewModelProviders.of(this).get(Rule21ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule21, container, false);
        final TextView textView = root.findViewById(R.id.description_rule21);
        rule21ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
