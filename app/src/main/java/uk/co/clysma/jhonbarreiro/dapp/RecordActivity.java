package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity {

    String location;
    ImageButton recordButton;
    ImageButton stopButton;
    ImageView daRecMessage;
    ImageView saveDaMessage;
    ImageButton recordingOn;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recordButton = findViewById(R.id.btnrecordbig);
        stopButton = findViewById(R.id.btnstop);
        daRecMessage = findViewById(R.id.imgmessagerec);
        recordingOn = findViewById(R.id.btnrecordon);
        saveDaMessage = findViewById(R.id.imgmessagesaving);

        stopButton.setEnabled(false);
        stopButton.setVisibility(View.INVISIBLE);

        File f = new File(Environment.getExternalStorageDirectory() + "/TheChannelMessages");
        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("daLocation");
        location = msg;


        //Toast.makeText(RecordActivity.this,"Tap the Handset icon to start recording...",Toast.LENGTH_LONG).show();
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.my_custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        stopButton.setEnabled(false);

        random = new Random();
        //JB. Create directory if it doesn't exist yet.

        if(!f.isDirectory()){

            f.mkdir();
        }

        File file = new File(f, "filename");



        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(RecordActivity.this,"Preparing the recorder...",Toast.LENGTH_LONG).show();

                stopButton.setEnabled(true);
                stopButton.setVisibility(View.VISIBLE);

                recordButton.setVisibility(View.INVISIBLE);
                recordingOn.setVisibility(View.VISIBLE);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                daRecMessage.setVisibility(View.VISIBLE);
                stopButton.setEnabled(true);

                if(checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory() + "/TheChannelMessages" +
                                    CreateRandomAudioFileName(5) + "daMessage.MP3";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    recordButton.setEnabled(false);
                    stopButton.setEnabled(true);

                } else {
                    requestPermission();
                }
                    }
                }, 1500);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                daRecMessage.setVisibility(View.INVISIBLE);

                daRecMessage.setVisibility(View.INVISIBLE);
                saveDaMessage.setVisibility(View.VISIBLE);

                //Toast.makeText(RecordActivity.this,"Saving message...",Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mediaRecorder.stop();

                        stopButton.setEnabled(false);
                        //buttonPlayLastRecordAudio.setEnabled(true);
                        recordButton.setEnabled(true);
                        //buttonStopPlayingRecording.setEnabled(false);

                        if(AudioSavePathInDevice != null)
                        {
                            Intent i = new Intent(getApplicationContext(), RecordingReviewActivity.class);
                            i.putExtra("daLocation", location);
                            i.putExtra("filepath", AudioSavePathInDevice);
                            startActivity(i);
                        }
                    }
                }, 3000);

            }
        });
        
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RecordActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(RecordActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RecordActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }


}
