package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AdminActivity extends AppCompatActivity {

    Button button;
    EditText tokenText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("token");

        if(msg == null || msg.isEmpty()){
            RedirectToLogin();
        }

        WebView androidmsgs = (WebView) findViewById(R.id.adminwv);
        androidmsgs.loadUrl("http://thechannel.azurewebsites.net/messagemodels");

        androidmsgs.setWebViewClient(new daWebViewClient());
        WebSettings webSettings = androidmsgs.getSettings();
        webSettings.setJavaScriptEnabled(true);




        button = findViewById(R.id.adminbackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    //JB. In case NO token is passed
    private void RedirectToLogin()
    {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
    private class daWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (Uri.parse(url).getHost().equals("http://thechannel.azurewebsites.net/messagemodels")) {

                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}




