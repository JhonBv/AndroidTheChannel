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
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class ApiLoginModule extends AsyncTask<ArrayList<String>, Void, String> {

    //JB. Declare the list view to which data will be bound.
    private TextView _myToken;
    private String ttxUsrName;
    private String ttxPsswrd;
    private String myResponse;
    private String _Url;

    //Constructor
    public ApiLoginModule() {

    }//end of Constructor


    //Constructor
    public ApiLoginModule(TextView textView, String myUrl) {
        this._myToken = textView;
        this._Url = myUrl;
    }//end of Constructor


    @Override
    protected String doInBackground(ArrayList<String>... params) {
        String daResponse = "UNDEFINED";

        ttxUsrName = params[0].get(0);
        ttxPsswrd = params[0].get(1);

        //JB. Attempt to obtain today's date.
        Date todays = new Date();

        try {
            daResponse = sendPost();
        } catch (Exception e) {

            daResponse = "Semantha detected an Error: " +"/n happened on" + todays.toString() +"/n"  + e.toString();
        }

        return daResponse;
    }//end doInBackground

    // HTTP POST request
    private String sendPost() throws Exception {

        String url = _Url;

        //JB. Declare the httpClient builder and initiate it.
        HttpClient httpClient = HttpClientBuilder.create().build();
        //JB. Type of Request (post in this case)
        HttpPost postRequest = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);
        params.add(new BasicNameValuePair("username", ttxUsrName));
        params.add(new BasicNameValuePair("password", ttxPsswrd));
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("client_id", "1c5ab665d80f4065959f119e5be783c6"));
        params.add(new BasicNameValuePair("client_secret", "sjIuML-TNZGEMm4sydCI6J_xV0yshdzkgAoxhOe9rho"));

        postRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity entity = response.getEntity();


        //handle response here...
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(instream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            //JB. Create a Json object and obtain the result
            JSONObject topLevel = new JSONObject(builder.toString());
            //JSONObject main = topLevel.getJSONObject("");

            try {
                //JB. Obtain the returned token
                myResponse = String.valueOf(topLevel.getString("access_token"));
            } finally {
                //JB. Run regardless..
                instream.close();
            }
        }

        //JB. Return the returned Token!
        return myResponse;
    }


    //JB.
    @Override
    protected void onPostExecute(String temp) {

        _myToken.setText(temp);
    }//end onPostExecute()

}
