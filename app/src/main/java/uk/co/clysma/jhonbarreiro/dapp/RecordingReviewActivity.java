package uk.co.clysma.jhonbarreiro.dapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import uk.co.clysma.jhonbarreiro.dapp.Services.MessageUploadService;

public class RecordingReviewActivity extends AppCompatActivity {

    ImageButton buttonPlayLastRecordAudio;
    ImageButton buttonStopPlayingRecording;
    ImageButton buttonSendMessage;
    ImageButton buttonDeleteMesage;
    MediaPlayer mediaPlayer;
    ImageView noInternetMsg;
    ImageView sentSuccess;
    ImageView successMsg;
    ImageView deletingMsg;


    private String myResponse;

    String AudioSavePathInDevice = null;
    String Location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_review);

        buttonPlayLastRecordAudio =  findViewById(R.id.btnrecordsuccess);
        buttonStopPlayingRecording = findViewById(R.id.btnstopplaying);
        buttonSendMessage= findViewById(R.id.btnsendmsg);
        buttonDeleteMesage=findViewById(R.id.btndeletemsg);
        sentSuccess=findViewById(R.id.messagesent);
        successMsg =findViewById(R.id.recordsuccess);
        deletingMsg = findViewById(R.id.msgdeleting);
        noInternetMsg = findViewById(R.id.msgnointernet);


        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("daLocation");
        String msg1 = extras.getString("filepath");
        Location = msg;
        AudioSavePathInDevice = msg1;


        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStopPlayingRecording.setVisibility(View.VISIBLE);
                buttonStopPlayingRecording.setEnabled(true);
                buttonPlayLastRecordAudio.setEnabled(false);
                buttonPlayLastRecordAudio.setVisibility(View.INVISIBLE);


                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

            }
        });

//JB. Stop playing
        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                buttonStopPlayingRecording.setEnabled(false);
                buttonStopPlayingRecording.setVisibility(View.INVISIBLE);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonPlayLastRecordAudio.setVisibility(View.VISIBLE);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();

                }
            }
        });

        //if the user likes the message, then send it to the API
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String daLocationViejo = Location;

                if(isNetworkAvailable()) {


                    successMsg.setVisibility(View.INVISIBLE);
                    sentSuccess.setVisibility(View.VISIBLE);

                    new MessageUploadService(AudioSavePathInDevice, daLocationViejo).execute();

                    //sentSuccess.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i = new Intent(getApplicationContext(), MessageCentreActivity.class);
                            i.putExtra("daLocation", Location);
                            i.putExtra("showImg", "sent");
                            startActivity(i);
                        }
                    }, 2000);
                }

                else{

                    noInternetMsg.setVisibility(View.VISIBLE);
                }

            }
        });

//Else, delete the message
        buttonDeleteMesage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                deletingMsg.setVisibility(View.VISIBLE);
                File file = new File(AudioSavePathInDevice);
                boolean deleted = file.delete();

                if(deleted) {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), MessageCentreActivity.class);
                            i.putExtra("daLocation", Location);
                            i.putExtra("showImg", "deleted");
                            startActivity(i);
                        }
                    }, 1500);

                }//File dir = getFilesDir();
            }
        });


    }//OnCreate Ends here

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}//Activity class ends here
