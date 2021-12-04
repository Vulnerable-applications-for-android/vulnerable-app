package com.example.android.thesis.vulnerableapp.ui.rule18;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.thesis.vulnerableapp.R;

public class Rule18Fragment extends Fragment {

    private Rule18ViewModel rule18ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule18ViewModel = new ViewModelProvider(this).get(Rule18ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule18, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

//        ComponentName cn = new ComponentName("com.example.implicit.intents.interception", "com.example.implicit.intents.interception.EvilActivity");

//        Button sendIntentButton = root.findViewById(R.id.button_rule18);
//        sendIntentButton.setOnClickListener(view -> {
//            Intent intent = new Intent("com.victim.messenger.IN_APP_MESSAGE");
//            intent.putExtra("from", "alberto");
//            intent.putExtra("text", "Hello everyone!!");
//            context.sendBroadcast(intent);
//        });

        Button sendIntentButton = root.findViewById(R.id.button_rule18);
        sendIntentButton.setOnClickListener(view -> {
            String uri = "https://www.facebook.com";
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//            Intent sendIntent = new Intent("com.victim.ADD_CARD_ACTION");
//            Intent sendIntent = new Intent();
//            sendIntent.setComponent(cn);
            sendIntent.setDataAndType(Uri.parse(uri), "text/plain");
            sendIntent.putExtra("secret", "sensitive data");
//            startActivity(sendIntent);
            startActivity(Intent.createChooser(sendIntent, "Send it using: "));
        });

        return root;
    }
}
