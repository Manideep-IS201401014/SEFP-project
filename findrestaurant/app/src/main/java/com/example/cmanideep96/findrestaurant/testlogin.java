package com.example.cmanideep96.findrestaurant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cmanideep96 on 25/11/2016.
 */
public class testlogin extends AppCompatActivity{
    Bundle b;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlogin);
        b = getIntent().getExtras();
        RetrieveFeedTask retrieveFeedTask=new RetrieveFeedTask();
        retrieveFeedTask.execute();
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;


        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);


        }

        protected JSONObject doInBackground(Void... urls) {

            try {
                String j= URLEncoder.encode("", "UTF-8");
                String MY_API = "http://192.168.43.38/login2.php";

                URL url = new URL(MY_API);
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
            if(data!=null){
                try {
                    String details = data.getString("result");
                    if(details.compareTo("true")==0){
                        Intent intent = new Intent(testlogin.this,ordersummary.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                    else if(details.compareTo("false")==0){
                        Intent intent = new Intent(testlogin.this,login.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
