package com.uninorte.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean verificarRed(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
           // Toast.makeText(this,"Newtwork is available",Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this,"Newtwork not available",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void onClickVerificarRed(View view) {
        if(verificarRed()==true){
            new GetData().execute();
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String response = getData();
            if(response != null){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()){
                pDialog.dismiss();

            }
        }
    }

    protected static String getData(){
        Log.d("TAG","get");
        try{
            URL url = null;
            url = new URL ("https://raw.githubusercontent.com/lsv/fifa-worldcup-2018/master/data.json");
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader((new InputStreamReader(yc.getInputStream())));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                Log.d("TAG", inputLine);
            in.close();
            return inputLine;

        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
