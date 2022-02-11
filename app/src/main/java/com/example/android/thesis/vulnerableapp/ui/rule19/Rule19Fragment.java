package com.example.android.thesis.vulnerableapp.ui.rule19;

import static android.content.Context.INPUT_METHOD_SERVICE;

import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.android.thesis.vulnerableapp.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Rule19Fragment extends Fragment {

    String SERVER_IP = "127.0.0.1";
    int SERVER_PORT = 8080;
    Thread Thread1 = null;
    EditText etIP, etPort;
    TextView tvMessages;
    EditText etMessage;
    Button btnSend;
    Button btnConnect;
    private Rule19ViewModel rule19ViewModel;
    private PrintWriter output;
    private BufferedReader input;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule19ViewModel = new ViewModelProvider(this).get(Rule19ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule19, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        // Hide keyboard when touching somewhere else
        root.findViewById(R.id.linearLayout_rule19_container).setOnTouchListener((v, event) -> {
            v.performClick();
            assert context != null;
            assert activity != null;
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            View focusView = activity.getCurrentFocus();
            if(focusView != null)
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            return true;
        });

        try {
            etIP = root.findViewById(R.id.etIP);      // uncomment these for custom ip_address:port
            etPort = root.findViewById(R.id.etPort);
            tvMessages = root.findViewById(R.id.tvMessages);
            etMessage = root.findViewById(R.id.etMessage);
            btnConnect = root.findViewById(R.id.btnConnect);
            btnSend = root.findViewById(R.id.btnSend);

            btnConnect.setOnClickListener(v -> {
                tvMessages.setText("");
                SERVER_IP = etIP.getText().toString().trim();         // uncomment these for custom ip_address:port
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
                Thread1 = new Thread(new Thread1());
                Thread1.start();
            });

            btnSend.setOnClickListener(v -> {
                String message = etMessage.getText().toString().trim();
                if (!message.isEmpty())
                    new Thread(new Thread3(message)).start();
            });
        } catch (Exception e) {
            Log.e("çççç", e.getMessage());
        }
        return root;
    }

    class Thread1 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                getActivity().runOnUiThread(() -> tvMessages.setText("Connected\n"));
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null)
                        getActivity().runOnUiThread(() -> tvMessages.append("server: " + message + "\n"));
                    else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private final String message;
        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message + "\n");
            output.flush();
            getActivity().runOnUiThread(() -> {
                tvMessages.append("client: " + message + "\n");
                etMessage.setText("");
            });
        }
    }
}