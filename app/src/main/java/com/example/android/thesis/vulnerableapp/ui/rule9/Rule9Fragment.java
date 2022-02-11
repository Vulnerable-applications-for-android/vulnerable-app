package com.example.android.thesis.vulnerableapp.ui.rule9;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.VulnerableReceiver;
import com.example.android.thesis.vulnerableapp.VulnerableService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Rule9Fragment extends Fragment {

    private Rule9ViewModel rule9ViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule9ViewModel = new ViewModelProvider(this).get(Rule9ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule9, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule9_container).setOnTouchListener((v, event) -> {
            v.performClick();
            assert context != null;
            assert activity != null;
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            View focusView = activity.getCurrentFocus();
            if(focusView != null)
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            return true;
        });

        EditText editText = root.findViewById(R.id.et_rule9);
        Button button = root.findViewById(R.id.button_rule9);

//        From API level ≥ 24, it returns FileUriExposedException
        button.setOnClickListener(v -> {
            assert context != null;
            File dataDir = context.getFilesDir();
            final String FILENAME = "confidential.txt";
            File file = new File(dataDir.getAbsolutePath(), FILENAME);
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                fw.write(editText.getText().toString());            // write in the internal app storage
                fw.close();
            } catch (IOException e) {
                Toast.makeText(context, "IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            editText.setText("");       // clear editText

            // Send an intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.example.test", "com.example.test.MainActivity");
            intent.setData(Uri.parse("file://" + file.getAbsolutePath()));
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        });

        return root;
    }
}
