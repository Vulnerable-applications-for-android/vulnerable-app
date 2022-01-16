package com.example.android.thesis.vulnerableapp.ui.rule24;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.thesis.vulnerableapp.R;
import java.io.File;
import java.lang.reflect.Method;
import dalvik.system.DexClassLoader;

public class Rule24Fragment extends Fragment {
    private Rule24ViewModel rule24ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule24ViewModel = new ViewModelProvider(this).get(Rule24ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule24, container, false);

        final TextView textView = root.findViewById(R.id.description_rule24);
        rule24ViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        final Activity activity = getActivity();
        Context context = this.getContext();

        // Need to grant the READ_EXTERNAL_STORAGE permission at runtime
        assert activity != null;
        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        // Retrieve the (malicious) apk in the Download directory
        final String FILENAME = "app-debug.apk";
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File apkFile = new File(folder, FILENAME);

        final File tmpDir = context.getDir("apk", 0);

        // Instantiate the DexClassLoader object which is gonna loading the apk
        if (apkFile.exists() && apkFile.canRead()) {
            DexClassLoader classloader = new DexClassLoader(
                    apkFile.getAbsolutePath(),
                    tmpDir.getAbsolutePath(),
                    null,
                    ClassLoader.getSystemClassLoader().getParent());
            try {
                Class<?> cls = classloader.loadClass("com.exec.dlc.EvilClass");     // get a class
                Method m = cls.getDeclaredMethod("evilMethod");             // get a method of the class
                m.invoke(cls);                      // invoke the method
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return root;
    }
}
