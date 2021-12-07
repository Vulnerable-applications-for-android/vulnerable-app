package com.example.android.thesis.vulnerableapp.ui.rule23;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.VulnerableReceiver;

public class Rule23Fragment extends Fragment {

    private Rule23ViewModel rule23ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule23ViewModel = new ViewModelProvider(this).get(Rule23ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule23, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule23_container).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {   // when the finger is over the screen
                v.performClick();
                assert context != null;
                assert activity != null;
                InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        });

        EditText etMessage = root.findViewById(R.id.et_rule23);
        Button sendBroadcast = root.findViewById(R.id.start_button_rule23);

        sendBroadcast.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VulnerableReceiver.class);   // explicit intent => it is intended only for VulnerableReceiver
//            Intent intent = new Intent();                                 // not good since it is implicit
            intent.putExtra("msg", etMessage.getText().toString());    // insert as extra what's written in editText
            intent.setAction("com.thesis.SOME_ACTION");
            context.sendBroadcast(intent);  // send the broadcast
        });

        return root;
    }
}
