package com.example.android.thesis.vulnerableapp.ui.rule1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.thesis.vulnerableapp.R;

public class Rule1Fragment extends Fragment {

    private Rule1ViewModel rule1ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule1ViewModel = new ViewModelProvider(this).get(Rule1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule1, container, false);

        // Launch an Activity using an implicit intent (and without using an app chooser)
        Button sendIntentButton = root.findViewById(R.id.button_rule1);
        sendIntentButton.setOnClickListener(v -> {
//            String uri = "https://www.google.com";
//            String uri = "test://ss.ss";
            Intent sendIntent = new Intent(Intent.ACTION_SEND);     // rule checked only with ACTION_SEND and ACTION_GET_CONTENT
            sendIntent.putExtra("secret", "this is a big secret!");
            sendIntent.putExtra("login", "login");
            sendIntent.putExtra("password", "pass");
            startActivity(sendIntent);      // vulnerable
//            startActivity(Intent.createChooser(sendIntent, "Send it using: "));   // NOT vulnerable
        });

        return root;
    }
}
