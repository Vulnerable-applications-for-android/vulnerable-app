package com.example.android.thesis.vulnerableapp.ui.rule18;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
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

        // Launch an Activity using an implicit intent
        Button sendIntentActButton = root.findViewById(R.id.button_activity_rule18);
        sendIntentActButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent("com.victim.ADD_CARD_ACTION"); // do NOT use standard actions such as ACTION_VIEW, ACTION_SEND, ...
            Bundle bundle = new Bundle();
            bundle.putString("login", "alberto");
            bundle.putString("password", "very_secret");
            sendIntent.putExtras(bundle);
            sendIntent.putExtra("laughing", "ahaahahahahha");
//            startActivity(sendIntent);

            // This allows to send the implicit intent without the selector
            for(ResolveInfo info : context.getPackageManager().queryIntentActivities(sendIntent, 0)) {
                sendIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                startActivity(sendIntent);
                return;
            }
        });

        // Send a broadcast using an implicit intent
        Button sendIntentBroButton = root.findViewById(R.id.button_broadcast_rule18);
        sendIntentBroButton.setOnClickListener(view -> {
            Intent intent = new Intent("com.victim.messenger.IN_APP_MESSAGE");
//            intent.addCategory("example.test.category");
            intent.putExtra("from", "alberto");
            intent.putExtra("text", "Hello everyone!!");
            context.sendBroadcast(intent, "test.permission");   // remember to add "test.permission" in the evilapp's manifest
        });

        return root;
    }
}
