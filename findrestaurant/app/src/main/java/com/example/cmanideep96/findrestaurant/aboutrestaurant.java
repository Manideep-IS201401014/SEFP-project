package com.example.cmanideep96.findrestaurant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmanideep96 on 21/09/2016.
 */
public class aboutrestaurant extends AppCompatActivity{
    public String restaurantName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abtrestaurant);
        Bundle b = getIntent().getExtras();
        restaurantName = b.getCharSequence("name").toString();
        RetrieveFeedTask abtrest =new RetrieveFeedTask();

        abtrest.execute();


    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;
        ProgressBar progressBar;

        protected void onPreExecute() {



        }

        protected JSONObject doInBackground(Void... urls) {

            try {
                String OPEN_WEATHER_MAP = "http://192.168.1.74/abtrest.php/?name="+restaurantName;
                URL url = new URL(OPEN_WEATHER_MAP);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                // extracting api

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());




                return data;
            } catch (Exception e) {
                return null;
            }

        }


        protected void onPostExecute(JSONObject data) {

            try {
                if (data != null) {
                    JSONArray details = data.getJSONArray("result");
                    String name =details.getJSONObject(0).getString("name");
//                    String name = details.getJSONObject(0).getString("name")+","+details.getJSONObject(1).getString("name");
                    TextView textView = (TextView)findViewById(R.id.nametextview);
                    textView.setText(name);
                    String desc =details.getJSONObject(0).getString("describe");
                    TextView textView1 = (TextView)findViewById(R.id.description);
                    textView1.setText("about: \n"+desc);
                    String phone = details.getJSONObject(0).getString("phone");
                    TextView textView2 =(TextView)findViewById(R.id.contact);
                    textView2.setText("Phone no:"+phone);




                }
                    }

             catch (JSONException e) {
                Log.e("MainActivity", "Cannot process JSON results", e);

            }
        }
    }
}
