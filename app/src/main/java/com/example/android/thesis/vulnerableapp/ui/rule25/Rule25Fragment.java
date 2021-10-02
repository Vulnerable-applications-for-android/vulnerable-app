package com.example.android.thesis.vulnerableapp.ui.rule25;

import androidx.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class Rule25Fragment extends Fragment {
    private Rule25ViewModel rule25ViewModel;

    private class NetworkAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... param) {
            String result = "";
            try {
                // 1. Create an HostnameVerifier that hardwires the expected hostname
                // Note that is different than the URL’s hostname: example.com vs example.org
                HostnameVerifier hostnameVerifier = (hostname, session) -> {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("google.com", session);    // see this hostname in the list of valid CN of the youtube's certificate
                };

                // 2. Declare the URL Connection and set the overrided hostname verification
                URL url = new URL(param[0]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setHostnameVerifier(hostnameVerifier);

                // 3. Open InputStream to connection
                conn.connect();
                InputStream in = conn.getInputStream();

                // 4. Download and decode the string response using builder
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                in.close();

                // Set our result equal to our stringBuilder
                result = stringBuilder.toString();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return result;
        }

        // This method is executed in the UIThread with access to the result of the long running task
        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule25ViewModel = ViewModelProviders.of(this).get(Rule25ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule25, container, false);

        Button button = root.findViewById(R.id.button_rule25);
        button.setOnClickListener(v -> {
//            new Rule25Fragment.NetworkAsyncTask().execute("https://example.org");
            new Rule25Fragment.NetworkAsyncTask().execute("https://www.youtube.com/");
        });

        return root;
    }
}