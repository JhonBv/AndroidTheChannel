package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

public class MessagesActivity extends AppCompatActivity {

    ImageButton recButton;
    ImageButton gohomeButton;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("daLocation");
        location = msg;

        WebView androidmsgs = (WebView) findViewById(R.id.dawebview);
        androidmsgs.loadUrl("http://thechannel.azurewebsites.net/androidmessages");
        androidmsgs.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = androidmsgs.getSettings();

        webSettings.setJavaScriptEnabled(true);

        recButton = findViewById(R.id.btnrecord);
        recButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecordActivity.class);
                i.putExtra("location", location);
                startActivity(i);
            }
        });

        gohomeButton = findViewById(R.id.btnhome);
        gohomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MessageCentreActivity.class);
                i.putExtra("location", location);
                i.putExtra("showImg","none");
                startActivity(i);
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("http://thechannel.azurewebsites.net/androidmessages")) {

                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}
