package com.example.android.thesis.vulnerableapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class VulnerableService extends Service {

    private MediaPlayer player;

    // The execution of service will start on calling this method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create( this, Settings.System.DEFAULT_RINGTONE_URI );  // creating a media player which will play the audio of Default ringtone in android device
        player.setLooping(true);    // provide 'true' to play the audio on loop
        player.start();     // start the process

        return START_STICKY;    // return the status of the program
    }

    // Execution of the service will stop on calling this method
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();      // stop the process
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
