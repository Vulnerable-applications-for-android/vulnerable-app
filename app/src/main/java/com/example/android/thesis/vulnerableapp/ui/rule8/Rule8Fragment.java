package com.example.android.thesis.vulnerableapp.ui.rule8;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;
import com.example.android.thesis.vulnerableapp.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Rule8Fragment extends Fragment {

    private Rule8ViewModel rule8ViewModel;

    // Custom function when the user input is submitted
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSubmitHandler(EditText editText) {
        String secret = editText.getText().toString();      // content of the EditText
        if (secret.equals("")) {
            Toast.makeText(getContext(),
                    "No secret typed. Please type the secret you want to save above.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("EditText", secret);
        editText.setText("");

        final String FILE_NAME = "secret.txt";
        Context context = getContext();

        // WRITE to file
        // Creates a file with this name, or replaces an existing file that has the same name.
        // Note that the file name cannot contain path separators
        FileOutputStream fos = null;
        try {
            assert context != null;
            fos = context.openFileOutput(FILE_NAME, Context.MODE_WORLD_READABLE);
            fos.write(secret.getBytes());
            fos.close();
        } catch (IOException e) {
            Toast.makeText(getContext(),
                    "IOException" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.e("IOException writing", Objects.requireNonNull(e.getMessage()));
        } catch (NullPointerException e) {
            Toast.makeText(getContext(),
                    "NullPointerException" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.e("NullPointer writing", Objects.requireNonNull(e.getMessage()));
        }

        // READ from file
        try {
            FileInputStream in = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStreamReader.close();
//            Log.i("READ", sb.toString());
            Toast.makeText(getContext(),
                    "CHECK written secret: " + sb.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(),
                    "IOException" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.i("IOException", e.getMessage());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSubmitExternalHandler(EditText editText) {
        String secret = editText.getText().toString();
        if (secret.equals("")) {
            Toast.makeText(getContext(),
                    "No secret typed. Please type the secret you want to save above.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("EditText", secret);
        editText.setText("");

        final String FILE_NAME = "secret.txt";
        Context context = getContext();

        // Checking External storage availability
        boolean Available = false;
        boolean Readable = false;
        String state = Environment.getExternalStorageState(); // used to know the status of media mounted to a computer (such as missing, read-only, ...)
        if(Environment.MEDIA_MOUNTED.equals(state)){
            // Both Read and write operations available
            Available= true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            // Only Read operation available
            Available= true;
            Readable= true;
        } else {
            // SD card not mounted
            Available = false;
        }
        if (Available && !Readable) {
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myFile = new File(folder, FILE_NAME);

            FileOutputStream fstream = null;

            try {
                assert context != null;
                myFile.createNewFile();
                fstream = new FileOutputStream(myFile);
                fstream.write(secret.getBytes());
                fstream.close();
            } catch (IOException e) {
                Toast.makeText(getContext(),
                        "IOException writing" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("IOException writing", Objects.requireNonNull(e.getMessage()));
            } catch (NullPointerException e) {
                Toast.makeText(getContext(),
                        "NullPointerException" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("NullPointer writing", Objects.requireNonNull(e.getMessage()));
            }
        }


        // READ from file
        try {
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myFile = new File(folder, FILE_NAME);

            FileInputStream in = new FileInputStream(myFile);
            StringBuffer sbuffer = new StringBuffer();
            int i;
            while ((i = in.read())!= -1){
                sbuffer.append((char)i);
            }
            in.close();

            Toast.makeText(getContext(),
                    "CHECK written secret: " + sbuffer.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(),
                    "IOException reading" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.i("IOException", Objects.requireNonNull(e.getMessage()));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSubmitExternalHandler2(EditText editText) {
        String secret = editText.getText().toString();
        if (secret.equals("")) {
            Toast.makeText(getContext(),
                    "No secret typed. Please type the secret you want to save above.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("EditText", secret);
        editText.setText("");

        final String FILE_NAME = "secret.txt";
        Context context = getContext();

        // Checking External storage availability
        boolean Available = false;
        boolean Readable = false;
        String state = Environment.getExternalStorageState(); // used to know the status of media mounted to a computer (such as missing, read-only, ...)
        if(Environment.MEDIA_MOUNTED.equals(state)){
            // Both Read and write operations available
            Available= true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            // Only Read operation available
            Available= true;
            Readable= true;
        } else {
            // SD card not mounted
            Available = false;
        }
        if (Available && !Readable) {
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myFile = new File(folder, FILE_NAME);

            FileOutputStream fstream = null;
            Log.i("DDDDDDDDD", myFile.toString());
            try {
                assert context != null;
                myFile.createNewFile();
                fstream = new FileOutputStream(myFile);
                fstream.write(secret.getBytes());
                fstream.close();
            } catch (IOException e) {
                Toast.makeText(getContext(),
                        "IOException writing" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("IOException writing", Objects.requireNonNull(e.getMessage()));
            } catch (NullPointerException e) {
                Toast.makeText(getContext(),
                        "NullPointerException" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("NullPointer writing", Objects.requireNonNull(e.getMessage()));
            }

            // READ from file
            try {

                FileInputStream in = new FileInputStream(myFile);
                StringBuffer sbuffer = new StringBuffer();
                int i;
                while ((i = in.read())!= -1){
                    sbuffer.append((char)i);
                }
                in.close();

                Toast.makeText(getContext(),
                        "CHECK written secret: " + sbuffer.toString(),
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getContext(),
                        "IOException reading" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.i("IOException", Objects.requireNonNull(e.getMessage()));
            }

        }



    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule8ViewModel = new ViewModelProvider(this).get(Rule8ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule8, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule8_container).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {   // when the finger is over the screen
                v.performClick();
                assert context != null;
                assert activity != null;
                InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        });

        final EditText editText = (EditText) root.findViewById(R.id.et_rule8);
        Button mButton = (Button) root.findViewById(R.id.button_rule8);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    onSubmitHandler(editText);
                }
                return false;
            }
        });

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(View view) {
                        onSubmitHandler(editText);
                    }
                }
        );

        return root;
    }
}
