package com.example.android.thesis.vulnerableapp.ui.rule6;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.thesis.vulnerableapp.R;

public class Rule6Fragment extends Fragment {
    private Rule6ViewModel rule6ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule6ViewModel = new ViewModelProvider(this).get(Rule6ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule6, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        String attackerControlledUrl = "https://b7a3-79-24-191-236.ngrok.io/default=<script>alert(1111111)</script>";

        WebView webView = (WebView) root.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());  // put it otherwise cannot run javascript
        webView.addJavascriptInterface(new WebAppInterface(context), "Android");
        webView.loadUrl(attackerControlledUrl);

        return root;
    }
}
