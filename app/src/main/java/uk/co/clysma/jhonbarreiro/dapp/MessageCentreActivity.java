package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MessageCentreActivity extends AppCompatActivity {

    ImageButton button;
    ImageButton goTorecordButton;
    String location;
    String message2display;
    ImageView msgSent;
    ImageView msgDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_centre);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("daLocation");
        message2display = extras.getString("showImg");
        location = msg;


        msgSent = findViewById(R.id.imgSent);
        msgDeleted = findViewById(R.id.imgDeleted);

        switch (message2display){
            case"none":
                break;
            case "sent" :
                msgSent.setVisibility(View.VISIBLE);
                break;
            case "deleted":
                msgDeleted.setVisibility(View.VISIBLE);
                break;

        }

        goTorecordButton = findViewById(R.id.imgrecord);

        button = findViewById(R.id.btnlisten);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MessagesActivity.class);
                i.putExtra("daLocation", location);
                startActivity(i);
            }
        });


        goTorecordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecordActivity.class);
                i.putExtra("daLocation", location);
                startActivity(i);
            }
        });
    }
}
