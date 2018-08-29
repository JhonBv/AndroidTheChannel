package uk.co.clysma.jhonbarreiro.dapp.Services;

import android.os.AsyncTask;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.clysma.jhonbarreiro.dapp.Models.MessagesModel;

//JB.. AsyncTask.. <INPUT, PROGRESS, RETURN TYPE>.
public class ApiMessages extends AsyncTask<ArrayList<String>, Void, String> {


    private ArrayList<MessagesModel> messagesList;
    private String _Url;
    private String _token;
    private TextView _myResponse;

    public ApiMessages() {

    }//end of Constructor

    //Constructor
    public ApiMessages(TextView textView, String myUrl) {
        this._myResponse = textView;
        this._Url = myUrl;
    }//end of Constructor

    @Override
    protected String doInBackground(ArrayList<String>... daParams) {
        //_Url = daParams[0].get(0);
        _token = daParams[0].get(0);
        String daResponse = "UNDEFINED";
        //JB. Attempt to obtain today's date.
        Date todays = new Date();

        try {
            daResponse = GetMessages();
        } catch (Exception e) {

            daResponse = "Semantha detected an Error: " + "/n happened on" + todays.toString() + "/n" + e.toString();
        }

        return daResponse;
    }//end doInBackground

    private String sendGet() throws Exception {
        String url = _Url;
        //JB. Declare the httpClient builder and initiate it.
        HttpClient httpClient = HttpClientBuilder.create().build();

        //JB. Type of Request (post in this case)
        HttpGet getRequest = new HttpGet(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Authorization", "bearer " + _token));


        HttpResponse response = httpClient.execute(getRequest);
        HttpEntity entity = response.getEntity();

        MessagesModel daModel = new MessagesModel();


        return entity.getContent().toString();
    }//end send

    private String GetMessages() throws Exception {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(_Url)
                .addHeader("Authorization", "bearer " + _token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {

            return response.body().string();
        }
        return "pailas men!";
    }
    @Override
    protected void onPostExecute(String temp) {

        _myResponse.setText(temp);
    }//end onPostExecute()

}//End of Class
