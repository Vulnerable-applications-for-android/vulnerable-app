package com.example.android.thesis.vulnerableapp.ui.rule5;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.ui.rule17.Rule17ViewModel;

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

        protected void onPostExecute(String result) {
            // This method is executed in the UIThread with access to the result of the long running task
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





//public class Rule5Fragment extends Fragment {
//
//    private Rule5ViewModel rule5ViewModel;
//    public String[] HTTPWEBSITES = new String[]{
//            "http://open-up.eu/en", "http://scratchpads.org/explore/sites-list"
//    };
//
//    public class HttpGetRequest extends AsyncTask<String, Void, String> {
//
//        public static final String REQUEST_METHOD = "GET";
//        public static final int READ_TIMEOUT = 15000;
//        public static final int CONNECTION_TIMEOUT = 15000;
//
//        @Override
//        protected String doInBackground(String... params) {
//            String stringUrl = params[0];
//            String result = "";
//            String inputLine = "";
//            try {
//                //Create a URL object holding our url
//                URL myUrl = new URL(stringUrl);
//                //Create a connection
//
//                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
//
//                //Set methods and timeouts
//                connection.setRequestMethod(REQUEST_METHOD);
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CONNECTION_TIMEOUT);
//
//                // Connect to our url
//                connection.connect();
//
//                // Create a new InputStreamReader
//                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
//
//                // Create a new buffered reader and String Builder
//                BufferedReader reader = new BufferedReader(streamReader);
//                StringBuilder stringBuilder = new StringBuilder();
//
//                // Check if the line we are reading is not null
//                while ((inputLine = reader.readLine()) != null) {
//                    stringBuilder.append(inputLine);
//                }
//
//                //Close our InputStream and Buffered reader
//                reader.close();
//                streamReader.close();
//
//                //Set our result equal to our stringBuilder
//                result = stringBuilder.toString();
//            } catch (IOException e) {
//                Log.i("IOException", e.getMessage());
//                Toast.makeText(getContext(),
//                        "IOException:\n" + e.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//            return result;
//        }
//
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//
//        public AsyncTask<String, Void, String> execute(View view, String httpwebsite) {
//            TextView waiting = (TextView) view.findViewById(R.id.tv_wait_rule5);
////            waiting.setVisibility(View.VISIBLE);
//            waiting.setText("Wait...");
//            AsyncTask<String, Void, String> result = execute(httpwebsite);
////            waiting.setVisibility(View.INVISIBLE);
//            waiting.setText("");
//            return result;
//        }
//    }
//
//    public void makeHttpRequest(View view) {
//        int random = new Random().nextInt(HTTPWEBSITES.length);
//
//        //Instantiate new instance of our class
//        HttpGetRequest getRequest = new HttpGetRequest();
//
//        //Perform the doInBackground method, passing in our url
//        String response = "";
//        try {
//            response = getRequest.execute(view, HTTPWEBSITES[random]).get();
//        } catch (ExecutionException e) {
//            Toast.makeText(getContext(),
//                    "ExecutionException:\n" + e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
//            Log.i("ExecutionException", e.getMessage());
//        } catch (InterruptedException e) {
//            Toast.makeText(getContext(),
//                    "InterruptedException:\n" + e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
//            Log.i("InterruptedException", e.getMessage());
//        }
//
//        if (!response.equals("")) {
//            // Get the response
//            Toast.makeText(getContext(),
//                    response,
//                    Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        rule5ViewModel =
//                ViewModelProviders.of(this).get(Rule5ViewModel.class);
//        final View root = inflater.inflate(R.layout.fragment_rule5, container, false);
//
//        Button button = (Button) root.findViewById(R.id.button_rule5);
//        button.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        makeHttpRequest(root);
//                    }
//                }
//        );
//
//        return root;
//    }
//
//}

