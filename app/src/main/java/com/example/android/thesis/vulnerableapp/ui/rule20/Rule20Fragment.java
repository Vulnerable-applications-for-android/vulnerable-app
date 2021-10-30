package com.example.android.thesis.vulnerableapp.ui.rule20;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.thesis.vulnerableapp.MainActivity;
import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.VulnerableService;

public class Rule20Fragment extends Fragment {

    private Rule20ViewModel rule20ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule20ViewModel = new ViewModelProvider(this).get(Rule20ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule20, container, false);

        final Context context = this.getContext();

        Button startServ = root.findViewById(R.id.start_button_rule20);
        Button stopServ = root.findViewById(R.id.stop_button_rule20);
//        TextView tvOutput = root.findViewById(R.id.tv_result_rule20);

        // When click the start button, start the service
        startServ.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VulnerableService.class);
            context.startService(intent);
        });

        // When click the stop button, stop the service
        stopServ.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VulnerableService.class);
            context.stopService(intent);
        });

        return root;
    }
}
