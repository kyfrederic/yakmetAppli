package com.example.yaokouakou.yakmetstation;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kouakou on 16/11/15.
 */
public class StationListActivity extends ListActivity {

    EditText ed ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.stationlist_activity);

         //ed = (EditText) findViewById(R.id.maliste);

        String[] values = new String[] { "Ancelle","Areches Beaufort","Artouste","Arvieux en Queyras","Ascou Pailhères","Auris en oisans","Auron","Aussois","Autrans","Avoriaz","Ax 3 Domaines","Ancelle","Areches Beaufort","Artouste","Arvieux en Queyras"
                ,"Ascou Pailhères","Auris en oisans","Auron","Aussois","Autrans","Avoriaz","Ax 3 Domaines","Ancelle","Areches Beaufort","Artouste","Arvieux en Queyras"
                ,"Ascou Pailhères","Auris en oisans","Auron","Aussois","Autrans","Avoriaz","Ax 3 Domaines","Ancelle","Areches Beaufort","Artouste","Arvieux en Queyras"
                ,"Ascou Pailhères","Auris en oisans","Auron","Aussois","Autrans","Avoriaz","Ax 3 Domaines" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.stationlist_activity, values);
        setListAdapter(adapter);

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);

        Intent intent = new Intent();
        intent.putExtra("MESSAGE",selectedItem);
        setResult(2,intent);
        Toast.makeText(this, "selectedItem : " + selectedItem, Toast.LENGTH_SHORT).show();
        finish();//finishing activity


    }
}

