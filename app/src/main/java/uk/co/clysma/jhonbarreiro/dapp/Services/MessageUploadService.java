package uk.co.clysma.jhonbarreiro.dapp.Services;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MessageUploadService extends AsyncTask<Void, Void, Void> {


    private String _FileToUpload;
    private String _LocaLocation;
    private String myResponse;

    public MessageUploadService()
    {

    }

    //Constructor
    public MessageUploadService(String FilePath, String location) {

        this._LocaLocation = location;
        this._FileToUpload = FilePath;

    }//end of Constructor

    @Override
    protected Void doInBackground(Void... arg0) {
        UploadFile("");
        return null;
    }


    public String UploadFile(String FileURL)
    {
        // TODO Auto-generated method stub
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = this._FileToUpload;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        String urlString = "http://thechannel.azurewebsites.net/postit?language=english&location="+this._LocaLocation;
        File dasFile = new File(this._FileToUpload);
        try{
            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(existingFileName) );
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            dos = new DataOutputStream( conn.getOutputStream() );
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + dasFile.getName() + "\"" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
            dos.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0){
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException ex){
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        catch (IOException ioe){
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream( conn.getInputStream() );
            String str;
            while (( str = inStream.readLine()) != null){
                Log.e("Debug","Server Response "+str);
                myResponse=str;
            }
            inStream.close();
        }
        catch (IOException ioex){
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
        return null;
    }

    //JB.
    @Override
    protected void onPostExecute(Void result) {


    }//end onPostExecute()

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }



}//End of Class
