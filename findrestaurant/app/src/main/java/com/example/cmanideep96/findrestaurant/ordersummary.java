package com.example.cmanideep96.findrestaurant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmanideep96 on 05/11/2016.
 */
public class ordersummary extends AppCompatActivity {
    public RetrieveFeedTask ordersumm = new RetrieveFeedTask();
    String summary ="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        TextView app_name = (TextView)findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/GreatVibes-Regular.otf");
        app_name.setTypeface(face);
        Bundle b = getIntent().getExtras();
        summary=b.getCharSequence("summ").toString();
        String cost=b.getCharSequence("cost").toString();
        TextView textView = (TextView)findViewById(R.id.order);
        String[] price = summary.split(",");
        String r="";
        for(int i=0;i<price.length;i++){
            r=r+price[i]+"\n";
        }
        textView.setText("Total Cost: RS."+cost+"\n\n"+r);
        ordersumm.execute();
    }



    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;


        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);


        }

        protected JSONObject doInBackground(Void... urls) {

            try {
                String j=URLEncoder.encode(summary, "UTF-8");
                String OPEN_WEATHER_MAP = "http://10.0.5.62/insert.php/?summary=" + j;

                URL url = new URL(OPEN_WEATHER_MAP);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                //connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());

                // This value will be 404 if the request was not
                // successful


                return data;
            } catch (Exception e) {
                return null;
            }

        }


        protected void onPostExecute(JSONObject data) {

        }
    }
}
