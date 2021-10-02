package com.example.android.thesis.vulnerableapp.ui.rule17;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.VulnerableProvider;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Rule17Fragment extends Fragment {

    private Rule17ViewModel rule17ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule17ViewModel = ViewModelProviders.of(this).get(Rule17ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule17, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule17_container).setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            return true;
        });

        // Add new secret to the content provider
        final EditText editText = root.findViewById(R.id.et_rule17);
        Button mButton = root.findViewById(R.id.button_rule17);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onAddSecretHandler(editText);
            }
            return false;
        });

        mButton.setOnClickListener(
                view -> onAddSecretHandler(editText)
        );

        // Hide query results
        final Button hideButton = root.findViewById(R.id.button_hide_rule17);
        hideButton.setOnClickListener(
                view -> onHideHandler(root)
        );

        // Query the content provider
        Button qButton = root.findViewById(R.id.query_button_rule17);
        qButton.setOnClickListener(view -> {
                    onClickRetrieveSecrets(root);
                    hideButton.setVisibility(View.VISIBLE);
                });

        // Delete all secrets in the content provider
        Button deleteButton = root.findViewById(R.id.delete_button_rule17);
        deleteButton.setOnClickListener(
                view -> onDeleteHandler(root)
        );

        return root;
    }


    public void onDeleteHandler(View view) {
        // DELETE all secrets
        int deleted = getContext().getContentResolver().delete(VulnerableProvider.CONTENT_URI, null, null);
        Log.i("DELETED RECORDS", deleted + "");

        // HIDE
        onHideHandler(view);
    }


    public void onHideHandler(View view) {
        Button hideButton = view.findViewById(R.id.button_hide_rule17);
        hideButton.setVisibility(View.INVISIBLE);
        TextView headerQuery = view.findViewById(R.id.tv_rule17_query_header);
        headerQuery.setVisibility(View.INVISIBLE);
        TextView queryResult = view.findViewById(R.id.tv_query_result_rule17);
        queryResult.setText("");
    }


    public void onAddSecretHandler(EditText editText) {
        onClickAddSecret(editText);
        editText.setText("");
    }


    public void onClickAddSecret(View view) {
        // Add a new secret record
        String input = ((EditText) view.findViewById(R.id.et_rule17)).getText().toString();
        if (input.equals("")) {
            Toast.makeText(getContext(), "No secret typed. Please type the secret you want to save above.", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(VulnerableProvider.SECRET, input);   // insert the new secret

        Uri uri = getContext().getContentResolver().insert(VulnerableProvider.CONTENT_URI, values);     // retrieve the URI of what prev inserted

        // CHECK the value just inserted, displaying it using a Toast
        String[] splittedUri = uri.toString().split("/");
        String lastInsertedId = splittedUri[splittedUri.length - 1];

        // Create a cursor which selects the last secret inserted
        Cursor cursor = getContext().getContentResolver().query(
                uri,
                null,
                VulnerableProvider._ID + "='" + lastInsertedId + "'",
                null,
                null);

        assert cursor != null;
        if (cursor.moveToFirst()) {             // read all secrets
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
//        Log.i("PROJECTION", Arrays.toString(projection));
        Cursor cursor = getContext().getContentResolver().query(secrets, projection, null, null, null);

        String output = "";
        TextView queryOutput = view.findViewById(R.id.tv_query_result_rule17);
        TextView queryHeader = view.findViewById(R.id.tv_rule17_query_header);
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
