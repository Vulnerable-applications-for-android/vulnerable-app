package com.example.android.thesis.vulnerableapp.ui.rule2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.VulnerableProvider;

import org.w3c.dom.Text;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Rule2Fragment extends Fragment {

    private Rule2ViewModel rule2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rule2ViewModel =
                ViewModelProviders.of(this).get(Rule2ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule2, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule2_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        // Add new secret to the content provider
        final EditText editText = (EditText) root.findViewById(R.id.et_rule2);
        Button mButton = (Button) root.findViewById(R.id.button_rule2);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onAddSecretHandler(editText);
                }
                return false;
            }
        });
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        onAddSecretHandler(editText);
                    }
                }
        );

        // HIDE query results
        final Button hideButton = (Button) root.findViewById(R.id.button_hide_rule2);
        hideButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        onHideHandler(root);
                    }
                }
        );

        // Query the content provider
        Button qButton = (Button) root.findViewById(R.id.query_button_rule2);
        qButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        onClickRetrieveSecrets(root);
                        hideButton.setVisibility(View.VISIBLE);
                    }
                }
        );

        // Delete all secrets in the content provider
        Button deleteButton = (Button) root.findViewById(R.id.delete_button_rule2);
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        onDeleteHandler(root);
                    }
                }
        );

        return root;
    }

    public void onDeleteHandler(View view) {
        // DELETE all secrets
        int deleted = getContext().getContentResolver().delete(
                VulnerableProvider.CONTENT_URI, null, null);
        Log.i("DELETED RECORDS", deleted + "");

        // HIDE
        onHideHandler(view);
    }

    public void onHideHandler(View view) {
        Button hideButton = (Button) view.findViewById(R.id.button_hide_rule2);
        hideButton.setVisibility(View.INVISIBLE);
        TextView headerQuery = (TextView) view.findViewById(R.id.tv_rule2_query_header);
        headerQuery.setVisibility(View.INVISIBLE);
        TextView queryResult = (TextView) view.findViewById(R.id.tv_query_result_rule2);
        queryResult.setText("");
    }

    public void onAddSecretHandler(EditText editText) {
        onClickAddSecret(editText);
        editText.setText("");
    }

    public void onClickAddSecret(View view) {
        // Add a new secret record
        String input = ((EditText) view.findViewById(R.id.et_rule2)).getText().toString();
        if (input.equals("")) {
            Toast.makeText(getContext(),
                    "No secret typed. Please type the secret you want to save above.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(VulnerableProvider.SECRET, input);

        Uri uri = getContext().getContentResolver().insert(
                VulnerableProvider.CONTENT_URI, values);

        // CHECK the value just inserted, displaying it using a Toast
        String[] splittedUri = uri.toString().split("/");
        String lastInsertedId = splittedUri[splittedUri.length - 1];

        Cursor cursor = getContext().getContentResolver().query(
                uri,
                null,
                VulnerableProvider._ID + "='" + lastInsertedId + "'",
                null,
                null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                Toast.makeText(getContext(),
                        "ID: " + cursor.getString(cursor.getColumnIndex(VulnerableProvider._ID)) +
                                "; SECRET: " + cursor.getString(cursor.getColumnIndex(VulnerableProvider.SECRET)),
                        Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void onClickRetrieveSecrets(View view) {
        // Retrieve secrets
        String URL = "content://com.example.VulnerableApp.VulnerableProvider/secrets";

        Uri secrets = Uri.parse(URL);
        String[] projection = new String[]{VulnerableProvider.SECRET};
        Cursor cursor = getContext().getContentResolver().query(secrets, projection, null, null, null);

        String output = "";
        TextView queryOutput = (TextView) view.findViewById(R.id.tv_query_result_rule2);
        TextView queryHeader = (TextView) view.findViewById(R.id.tv_rule2_query_header);
        queryHeader.setVisibility(View.VISIBLE);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                output += "- " + cursor.getString(cursor.getColumnIndex(VulnerableProvider.SECRET)) + "\n";
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (output.equals("")) {
            output = "No secrets to show...";
        }
        queryOutput.setText(output);
    }
}
