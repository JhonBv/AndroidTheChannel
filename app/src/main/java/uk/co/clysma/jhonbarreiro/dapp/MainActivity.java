package uk.co.clysma.jhonbarreiro.dapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.push.Push;

public class MainActivity extends AppCompatActivity {

Button inbutton;
Button outbutton;
Button adminbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "9253d51b-a4b4-4ad7-b8d5-97a8bdf143d4",
                Analytics.class, Crashes.class);
        AppCenter.start(getApplication(), "9253d51b-a4b4-4ad7-b8d5-97a8bdf143d4", Analytics.class, Crashes.class);

        AppCenter.start(getApplication(), "9253d51b-a4b4-4ad7-b8d5- 97a8bdf143d4", Push.class);

        inbutton = findViewById(R.id.btninside);
        outbutton=findViewById(R.id.btnoutside);
        //adminbutton =findViewById(R.id.btnadminfrom_main);

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

        /*adminbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msgout = "UK";
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                i.putExtra("daLocation", msgout);
                startActivity(i);
            }
        });*/
    }
}
