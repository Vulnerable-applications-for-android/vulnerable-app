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

    private void sendIntent() {
//        String uri = "https://www.facebook.com";
//        String uri = "https://www.google.com";
        String uri = "test://ss.ss";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("secret", "this is a big secret!");
        intent.putExtra("login", "login");
        intent.putExtra("password", "pass");
        startActivity(intent);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule1ViewModel = new ViewModelProvider(this).get(Rule1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule1, container, false);

        Button sendIntentButton = root.findViewById(R.id.button_rule1);
        sendIntentButton.setOnClickListener(
                view -> sendIntent()
        );

        return root;
    }
}
