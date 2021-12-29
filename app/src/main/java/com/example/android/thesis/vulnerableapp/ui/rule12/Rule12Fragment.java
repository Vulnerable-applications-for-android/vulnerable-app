package com.example.android.thesis.vulnerableapp.ui.rule12;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Rule12Fragment extends Fragment {

    private Rule12ViewModel rule12ViewModel;

    private void onSubmitPref(EditText editText) {
        String secret = editText.getText().toString();
        if (secret.equals("")) {
            Toast.makeText(getContext(),
                    "No secret typed. Please type the secret you want to save above.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("EditText", secret);
        editText.setText("");
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("secretPreferences", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("secret", secret);
        editor.apply();

        // Check we actually stored the preference value
        Toast.makeText(getContext(),
                "CHECK saved preference: " + sharedPreferences.getString("secret", "ERROR"),
                Toast.LENGTH_SHORT).show();
//        Log.i("Stored Secret Pref", sharedPreferences.getString("secret", "ERROR"));
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule12ViewModel = new ViewModelProvider(this).get(Rule12ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rule12, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule12_container).setOnTouchListener((v, event) -> {
            v.performClick();
            assert context != null;
            assert activity != null;
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            View focusView = activity.getCurrentFocus();
            if(focusView != null)
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            return true;
        });

        final EditText editText = (EditText) root.findViewById(R.id.et_rule12);

        Button mButton = (Button) root.findViewById(R.id.button_rule12);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    Log.i("EditText from Submit", editText.getText().toString());
                    onSubmitPref(editText);
                }
                return false;
            }
        });

        mButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
//                    Log.i("EditText", editText.getText().toString());
                    onSubmitPref(editText);
                }
            }
        );
        return root;
    }
}
