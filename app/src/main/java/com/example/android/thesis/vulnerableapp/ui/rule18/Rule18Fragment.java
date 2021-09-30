package com.example.android.thesis.vulnerableapp.ui.rule18;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.example.android.thesis.vulnerableapp.R;

public class Rule18Fragment extends Fragment {

    private Rule18ViewModel rule18ViewModel;

    private void sendIntent() {
        String uri = "https://www.facebook.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule18ViewModel = ViewModelProviders.of(this).get(Rule18ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule18, container, false);

        Button sendIntentButton = root.findViewById(R.id.button_rule18);
        sendIntentButton.setOnClickListener(
                view -> sendIntent()
        );

        return root;
    }
}
