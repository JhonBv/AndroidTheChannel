package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import uk.co.clysma.jhonbarreiro.dapp.Services.ApiLoginModule;
import uk.co.clysma.jhonbarreiro.dapp.Services.ApiMessages;

public class LoginActivity extends AppCompatActivity {

    private EditText usernametxt;
    private EditText passwordtxt;
    private EditText txtvwReturnedToken;
    SharedPreferences sharedpreferences;
    String apiUrl;

    //DELETE AFTER
    Button testAdminActivity;
    TextView testResults;
    TextView testTokenHolder;
    String[] MeessageArray;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DELET AFTER
        testAdminActivity = (Button)findViewById(R.id.btnTestlogin);
        testTokenHolder = (TextView)findViewById(R.id.txtMATCHTOKEN);
        testResults= (TextView)findViewById(R.id.txtTestResultlogin);


        Button button = (Button) findViewById(R.id.btnlogin);
        //JB. Set the login details from EditTexts
        usernametxt = (EditText) findViewById(R.id.txtusername);
        passwordtxt = (EditText) findViewById(R.id.txtpassword);
        txtvwReturnedToken = (EditText) findViewById(R.id.txtToken);
        apiUrl = getString(R.string.baseUrlAddressLogin);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //JB. Arraylist to store username and password
                ArrayList<String> details = new ArrayList<String>();
                //JB. Set the login details from EditTexts
                EditText ttusr = (EditText) findViewById(R.id.txtusername);
                EditText ttpswr = (EditText) findViewById(R.id.txtpassword);

                //JB. Add data entered in the fields to the array list.
                details.add(ttusr.getText().toString());
                details.add(ttpswr.getText().toString());

                String url = apiUrl;

                //JB. Pass on the ArrayList with set properties. the txtvwReturnedToken is of type EditText  ;)
                new ApiLoginModule(txtvwReturnedToken, url).execute(details);
                testTokenHolder.setText(txtvwReturnedToken.getText());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //GotoMainActivity();
                    }
                }, 3000);
            }
        });

        testAdminActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> details = new ArrayList<String>();
                details.add(testTokenHolder.getText().toString());
                String url="http://thechannel.azurewebsites.net/Messages/all";

                new ApiMessages(testResults, url).execute(details);
            }
        });

    }//onCreate
    public void GotoMainActivity() {

        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("token", txtvwReturnedToken.getText().toString());
        startActivity(intent);
    }
}
