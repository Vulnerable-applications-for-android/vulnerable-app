package com.example.android.thesis.vulnerableapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.ui.rule23.Rule23Fragment;

public class VulnerableReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.thesis.SOME_ACTION"))
//            Toast.makeText(context, "Received!!", Toast.LENGTH_LONG).show();  // tested when no extras are present
            Toast.makeText(context, intent.getStringExtra("msg"), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Receiver is not arrived!!", Toast.LENGTH_LONG).show();
    }
}
