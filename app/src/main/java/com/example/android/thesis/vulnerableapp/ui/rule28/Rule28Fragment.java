package com.example.android.thesis.vulnerableapp.ui.rule28;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Rule28Fragment extends Fragment {

    private Rule28ViewModel rule28ViewModel;
    public String[] HTTPWEBSITES = new String[]{
            "http://open-up.eu/en", "http://floraofksa.myspecies.info/", "http://go.com/", "http://www.example.com/", "http://www.mit.edu/"
    };

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = "";
            String inputLine = "";
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection

                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                // Connect to our url
                connection.connect();

                // Create a new InputStreamReader
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                // Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                // Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                Log.i("IOException", e.getMessage());
                Toast.makeText(getContext(),
                        "IOException:\n" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        public AsyncTask<String, Void, String> execute(View view, String httpwebsite) {
            TextView waiting = (TextView) view.findViewById(R.id.tv_wait_rule28);
//            waiting.setVisibility(View.VISIBLE);
            waiting.setText("Wait...");
            AsyncTask<String, Void, String> result = execute(httpwebsite);
//            waiting.setVisibility(View.INVISIBLE);
            waiting.setText("");
            return result;
        }
    }

    public void makeHttpRequest(View view) {
        int random = new Random().nextInt(HTTPWEBSITES.length);

        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();

        //Perform the doInBackground method, passing in our url
        String response = "";
        try {
            response = getRequest.execute(view, HTTPWEBSITES[random]).get();
        } catch (ExecutionException e) {
            Toast.makeText(getContext(),
                    "ExecutionException:\n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.i("ExecutionException", e.getMessage());
        } catch (InterruptedException e) {
            Toast.makeText(getContext(),
                    "InterruptedException:\n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.i("InterruptedException", e.getMessage());
        }

        if (!response.equals("")) {
            // Get the response
            Toast.makeText(getContext(),
                    response,
                    Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rule28ViewModel =
                ViewModelProviders.of(this).get(Rule28ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule28, container, false);

        Button button = (Button) root.findViewById(R.id.button_rule28);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        makeHttpRequest(root);
                    }
                }
        );

        return root;
    }

}
