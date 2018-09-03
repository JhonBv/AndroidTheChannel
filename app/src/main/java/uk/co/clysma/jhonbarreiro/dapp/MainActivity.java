package uk.co.clysma.jhonbarreiro.dapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

Button inbutton;
Button outbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inbutton = findViewById(R.id.btninside);
        outbutton=findViewById(R.id.btnoutside);


        inbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msgin = "Detention";
                Intent i = new Intent(getApplicationContext(), MessageCentreActivity.class);
                i.putExtra("daLocation", msgin);
                i.putExtra("showImg","none");
                startActivity(i);
            }
        });

        outbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msgout = "UK";
                Intent i = new Intent(getApplicationContext(), MessageCentreActivity.class);
                i.putExtra("daLocation", msgout);
                i.putExtra("showImg","none");
                startActivity(i);
            }
        });

    }
}
