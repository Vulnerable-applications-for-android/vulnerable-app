package com.example.android.thesis.vulnerableapp.ui.rule7;

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

public class Rule7Fragment extends Fragment {
    private Rule7ViewModel rule7ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rule7ViewModel = new ViewModelProvider(this).get(Rule7ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_rule7, container, false);

        final Context context = this.getContext();
        final Activity activity = getActivity();

        String attackerControlledUrl = "https://b7a3-79-24-191-236.ngrok.io/default=<script>alert(1111111)</script>";

        WebView webView = (WebView) root.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());  // put it otherwise cannot run javascript
        webView.setWebViewClient(new CustomClient());   // sets CustomClient as WebViewClient (it will handle any request / notification)
        webView.loadUrl(attackerControlledUrl);

        return root;
    }
}
