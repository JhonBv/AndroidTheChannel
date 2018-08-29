package uk.co.clysma.jhonbarreiro.dapp.Services;
import java.io.IOException;
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
import android.os.AsyncTask;
import android.os.Message;
import android.util.JsonReader;
import android.widget.TextView;


public class ApiMessagesService extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

    private ArrayList<MessagesModel> messagesList;
    private String _Url;
    private String _token;
    private TextView _myResponse;


    public ApiMessagesService(){}

    public ApiMessagesService(TextView textView, String myUrl) {
        this._myResponse = textView;
        this._Url = myUrl;
    }//end of Constructor



    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... daParams) {
        _token = daParams[0].get(0);
        ArrayList<String> daResponse = new ArrayList<String>();
        //JB. Attempt to obtain today's date.
        Date todays = new Date();

        try {
            daResponse = GetMessages();
        } catch (Exception e) {

            daResponse.add("Semantha detected an Error: " + "/n happened on" + todays.toString() + "/n" + e.toString());
        }

        return daResponse;
    }
    //JB. This method returns a lost of messages
    private ArrayList<String> GetMessages() throws Exception {
        OkHttpClient client = new OkHttpClient();
        ArrayList<String> messgs= new ArrayList<String>();

        Request request = new Request.Builder()
                .url(_Url)
                .addHeader("Authorization", "bearer " + _token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            //JSONParser parse = new JSONParser();
            messgs.add(response.message());
            return messgs;
        }
        messgs.add("Pailas");
        return messgs;
    }

    public List<String> readJsonStream(InputStream in) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

public List<String> readMessagesArray(JsonReader reader) throws IOException {
    List<String> messages = new ArrayList<String>();
    reader.beginArray();
    while (reader.hasNext()) {
        messages.add(ReadMessage(reader).toString());
    }
    reader.endArray();
    return messages;
}
    public List<String> ReadMessage(JsonReader reader) throws IOException {

        String FileName = null;
        String FileUrl = null;
        String DateAdded = null;
        Boolean isApproved = false;
        String Location = null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("FileName")) {
                FileName = reader.nextString();
            } else if (name.equals("FileUrl")) {
                FileUrl = reader.nextString();
            } else if (name.equals("DateAdded")) {
                DateAdded = reader.nextString();
            } else if (name.equals("isApproved")) {
                isApproved = reader.nextBoolean();
            } else if (name.equals("Location")) {
                Location = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        List<String> daList =new ArrayList<String>();
        daList.add(FileName);
        daList.add(FileUrl);
        daList.add(DateAdded);
        daList.add(isApproved.toString());
        daList.add(Location);
        //return new Message(FileName, FileUrl, DateAdded, isApproved, Location);
        return daList;

    }

    @Override
    protected void onPostExecute(ArrayList<String> temp) {

    }
}
