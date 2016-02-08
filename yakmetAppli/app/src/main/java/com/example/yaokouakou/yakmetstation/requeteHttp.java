package com.example.yaokouakou.yakmetstation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class requeteHttp extends AsyncTask<URL, Integer , String> {

    MainActivity main;
    ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    public requeteHttp (MainActivity main){
        this.main = main ;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(main);
        pDialog.show();
    }


    @Override
    protected String doInBackground(URL... params) {

        String s = null ;

        try {
            publishProgress(20);
            HttpURLConnection urlConnection = (HttpURLConnection) params[0].openConnection();

            Log.i("CV", "reponse :" + urlConnection.getResponseCode());
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            s = readStream(in);
            Thread.sleep(1000);
            publishProgress(40);
            Thread.sleep(100);
            publishProgress(100);

            // main.updateWidgets(s);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return s;
    }




    @Override
    protected void onPostExecute(String s) {
        //Log.i("CV", s) ;
        if(s==null)
            Toast.makeText(main.getApplicationContext(), "connection failed", Toast.LENGTH_SHORT).show();
        else{
            main.updateWidgets(s);
            super.onPostExecute(s);
        }
        pDialog.dismiss();

        if (main.myOffonButton.isChecked()){
        Vibrator v = (Vibrator) main.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);}


    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.println("pouet");
        pDialog.setProgress(values[0]);

    }




    public static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

}
