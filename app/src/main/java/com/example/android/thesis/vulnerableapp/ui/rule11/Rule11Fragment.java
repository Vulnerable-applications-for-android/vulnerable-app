package com.example.android.thesis.vulnerableapp.ui.rule11;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.thesis.vulnerableapp.R;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Rule11Fragment extends Fragment {

    private Rule11ViewModel rule11ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule11ViewModel = new ViewModelProvider(this).get(Rule11ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule11, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule11_container).setOnTouchListener((v, event) -> {
            v.performClick();
            assert context != null;
            assert activity != null;
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            View focusView = activity.getCurrentFocus();
            if(focusView != null)
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            return true;
        });

        EditText editText = root.findViewById(R.id.et_rule11);
        Button mButton = root.findViewById(R.id.button_rule11);

        mButton.setOnClickListener(v -> {
            assert context != null;
            File cacheDir = context.getExternalCacheDir();              // get the path of the external cache
            final String FILENAME = "external-secret.txt";
            File cacheFile = new File(cacheDir.getAbsolutePath(), FILENAME);     // create a file object
            // File superfluo = new File(getFilesDir(), "superfluo.txt");   // this is not visualized from R11 since it isn't in cache!

            Log.i("getExternalCacheDir ", cacheDir.getAbsolutePath());
            // getCacheDir()           =   "/data/user/0/com.example.test/cache"
            // getExternalCacheDir()   =   "/storage/emulated/0/Android/data/com.example.test/cache" => used for file with size > 1 MB

            File secret_dir = new File(context.getExternalCacheDir().getAbsolutePath() + "/Segreta");
            File nascosto = new File(context.getExternalCacheDir().getAbsolutePath() + "/Segreta", "nascosto.txt");
            FileWriter fw1;
            FileWriter fw2;
            try {
                cacheFile.createNewFile();   // create the file (empty, if NOT exists) in the application (external) folder of the app
                fw1 = new FileWriter(cacheFile.getAbsoluteFile());
                System.out.println(editText.getText().toString());
                fw1.write(editText.getText().toString());            // write in the (external) cache
                fw1.close();

                secret_dir.mkdir();
                nascosto.createNewFile();
                fw2 = new FileWriter(nascosto.getAbsoluteFile());
                fw2.write("ciao");
                fw2.close();
            } catch (IOException e) {
                Toast.makeText(context, "IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            editText.setText("");       // clear editText
        });

        return root;
    }
}
