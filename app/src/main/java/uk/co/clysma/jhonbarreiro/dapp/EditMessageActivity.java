package uk.co.clysma.jhonbarreiro.dapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditMessageActivity extends AppCompatActivity {

    private String EditUrl = "http://thechannel.azurewebsites.net/messagemodels/Edit/";
    String MsgKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);
        //JB. Receive the item to be edited
        Bundle extras = getIntent().getExtras();
        MsgKey = extras.getString("daKey");



    }
}
