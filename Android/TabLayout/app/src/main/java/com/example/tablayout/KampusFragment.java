package com.example.tablayout;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class KampusFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private WebView _webView;

    public KampusFragment() {
        // Required empty public constructor
    }

    public static KampusFragment newInstance(String param1, String param2) {
        KampusFragment fragment = new KampusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kampus, container, false);

        _webView = view.findViewById(R.id.kampusWebView);

        WebSettings webSettings = _webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        _webView.setWebViewClient(new WebViewClient());
        _webView.addJavascriptInterface(new WebAppInterface(), "Android");

        _webView.loadUrl("https://stmikpontianak.cloud/011100862/angular011100862");

        return view;
    }

    private class WebAppInterface {

        @JavascriptInterface
        public void showNotification(String title, String message) {
            if (getContext() == null) return;

            NotificationManager manager =
                    (NotificationManager) requireContext().getSystemService(NotificationManager.class);

            String channelId = "channel_webview";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "WebView Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(requireContext(), channelId)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true);

            manager.notify(1, builder.build());
        }

        @JavascriptInterface
        public void showCall() {
            if (getContext() == null) return;

            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                dialPhone();
            } else {
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PERMISSION
                );
            }
        }

        @JavascriptInterface
        public void showWhatsApp() {
            if (getContext() == null) return;

            String url = "https://wa.me/6281353016098";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }

        @JavascriptInterface
        public void showCamera() {
            if (getContext() == null) return;

            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION
                );
            }
        }
    }

    private void dialPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:08123456789"));
        startActivity(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}