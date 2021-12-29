package com.example.android.thesis.vulnerableapp.ui.rule5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;
import com.example.android.thesis.vulnerableapp.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Rule5Fragment extends Fragment {
    private Rule5ViewModel rule5ViewModel;

    private class NetworkAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... param) {
            String result = "";
            try {
                // 1. Declare a URL Connection
                URL url = new URL(param[0]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                // Set methods and timeouts (default is GET method)
                conn.setRequestMethod("GET");
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);

                // 2. Open InputStream to connection
                conn.connect();
                InputStream in = conn.getInputStream();

                // 3. Download and decode the string response using builder
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

        protected void onPostExecute(String result) {   // it accesses the (string) result of the network task
//            super.onPostExecute(result);
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule5ViewModel = new ViewModelProvider(this).get(Rule5ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule5, container, false);

        Button button = root.findViewById(R.id.button_rule5);
        button.setOnClickListener(v -> {
            new NetworkAsyncTask().execute("https://www.ansa.it");
        });

        return root;
    }
}
