package com.example.yaokouakou.yakmetstation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;

public class MainActivity extends Activity {

    EditText stationName;
    Button boutonValider;
    TextView infoMeteoEtat;
    TextView infoMeteoTemps;
    TextView infoMeteoTempeMatin;
    TextView infoMeteoTempeAprem;
    TextView infoMeteoVitVent;
    TextView infoMeteoNivNeige;
    String ouverte,temperatureMatin, temperatureMidi, vent, neige, temps ;
    JSONObject jsonObj;
    String result;
    Switch myOffonButton;
    String champs;
    Intent i ;
    SharedPreferences pref ;
    Uri gmmIntentUri ;




    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        infoMeteoEtat = (TextView) findViewById(R.id.etatstatski);
        infoMeteoTemps = (TextView) findViewById(R.id.letemps_id);
        infoMeteoTempeMatin = (TextView) findViewById(R.id.tempemat_id);
        infoMeteoTempeAprem = (TextView) findViewById(R.id.tempeaprem_id);
        infoMeteoVitVent = (TextView) findViewById(R.id.vitessevent_id);
        infoMeteoNivNeige = (TextView) findViewById(R.id.nivneige_id);
        myOffonButton = (Switch) findViewById(R.id.onoff_id);
        myOffonButton.setChecked(true);
        stationName = (EditText) findViewById(R.id.stationName);

        pref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String restore = pref.getString("station", "");
        if (restore.equals("")){}else {

      stationName.setText(restore);}

        boutonValider = (Button) findViewById(R.id.butValide);

    }


    public void updateWidgets(String result) {

        this.result = result;

        try {
            jsonObj = new JSONObject(result);
            ouverte = jsonObj.getString("ouverte");
            temperatureMatin = jsonObj.getString("temperatureMatin");
            temperatureMidi = jsonObj.getString("temperatureMidi");
            vent = jsonObj.getString("vent");
            neige = jsonObj.getString("neige");
            temps = jsonObj.getString("temps");

            infoMeteoEtat.setText(ouverte);
            infoMeteoTemps.setText(temps);
            infoMeteoTempeMatin.setText(temperatureMatin);
            infoMeteoTempeAprem.setText(temperatureMidi);
            infoMeteoVitVent.setText(vent);
            infoMeteoNivNeige.setText(neige);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ;


    public void buttonClicked(View view) {
        String station = stationName.getText().toString();

        //new requeteHttp().execute("Hello from another thread ");

        URL url = null;
        try {
            url = new URL("http://snowlabri.appspot.com/snow?station=gourette");
            //url = new URL("snow,,,,,,?station=gourette");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new requeteHttp(this).execute(url);

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("CV", "onSaveInstanceState");
        outState.putString("currentData", result);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("CV", "onRestoreInstanceState");
        updateWidgets(savedInstanceState.getString("currentData"));

    }



    public void onoffBouton(View view){

        Log.i("CV", "etat bouton" + myOffonButton.isChecked());
        //Log.i("CV", "########### string #########     " + this.getString(R.string.reqjsonouverte));



    }


    public void buttonClickedforlocate(View view) {


        champs = stationName.getText().toString();
        Log.i("CV", "valeur champs" + champs);



        pref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("station", champs);
        editor.commit();
        Toast.makeText(MainActivity.this, champs+ " : sauvegarder ", Toast.LENGTH_SHORT).show();


        gmmIntentUri = Uri.parse("geo:0,0?q=" + champs);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);




/*
        try {
            i = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
            Log.i("CV", " la valeur entréev est :" + champs);
        } catch (PackageManager.NameNotFoundException e) {

        }*/
    }


  public void selectClick (View view) {

      Intent i = new Intent(this, StationListActivity.class);
      startActivityForResult(i,2);

  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){

            String s = data.getStringExtra("MESSAGE");
            stationName.setText(s);
            Toast.makeText(this, "onActivityResult : " + s, Toast.LENGTH_SHORT).show();
        }
    }
}