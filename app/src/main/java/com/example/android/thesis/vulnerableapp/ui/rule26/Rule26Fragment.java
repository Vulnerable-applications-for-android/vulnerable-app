package com.example.android.thesis.vulnerableapp.ui.rule26;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.thesis.vulnerableapp.R;
import com.example.android.thesis.vulnerableapp.ui.rule5.Rule5ViewModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Rule26Fragment extends Fragment {
    private Rule26ViewModel rule26ViewModel;

    private class NetworkAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... param) {
            String result = "";
            try {
                // 1. Open SSLSocket directly to mail.google.com
                SocketFactory sf = SSLSocketFactory.getDefault();
                SSLSocket socket = (SSLSocket) sf.createSocket(param[0], 443);
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();

                SSLSession s = socket.getSession();

                // 2. Verify that the certificate hostname is for inbox.google.com (if we see the gmail certificate, inbox.google.com is listed as well)
                // This is due to lack of SNI support in the current SSLSocket
                if (!hv.verify("inbox.google.com", s)) {
                    throw new SSLHandshakeException("Expected inbox.google.com, " + " found " + s.getPeerPrincipal());
                }
                /* At this point SSLSocket performed certificate verification
                   and we have performed hostname verification, so it is safe to proceed. */

                // now we can use socket...
                InputStream in = socket.getInputStream();

                // 3. Download and decode the string response using builder
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                in.close();

                // 4. Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                socket.close();

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
        rule26ViewModel = ViewModelProviders.of(this).get(Rule26ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule26, container, false);

        Button button = root.findViewById(R.id.button_rule26);
        button.setOnClickListener(v -> {
            new NetworkAsyncTask().execute("mail.google.com");
        });
        return root;
    }
}