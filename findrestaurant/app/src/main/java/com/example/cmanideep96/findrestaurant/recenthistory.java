package com.example.cmanideep96.findrestaurant;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cmanideep96 on 26/11/2016.
 */
public class recenthistory extends AppCompatActivity {
    RetrieveFeedTask retrieveFeedTask =new RetrieveFeedTask();
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        retrieveFeedTask.execute();
        Bundle b = getIntent().getExtras();
         username= b.getCharSequence("username").toString();
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;


        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);


        }

        protected JSONObject doInBackground(Void... urls) {

            try {
//                String j= URLEncoder.encode(summary, "UTF-8");
                String MY_API = "http://192.168.43.38/history.php/?name="+username;

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
                    JSONArray details = data.getJSONArray("result");
                    int k=details.length();
                    String summ="";
                    if(k>0){
                        String name=details.getJSONObject(0).getString("username");
                        TextView textView1 =(TextView)findViewById(R.id.name);
                        textView1.setText("Username:"+name);
                        int i=0;
                        while(i<k){
                        String g="";
                        String hotel=details.getJSONObject(i).getString("hotel");
                        g=details.getJSONObject(i).getString("order");
                        g=g.trim();
                            g= g.substring(0, g.length()-1);
                        String cost=details.getJSONObject(i).getString("cost");
                        summ=summ+hotel+"\n"+g+"  --  RS"+cost+"/-"+"\n\n\n";
                            i=i+1;
                        }
                        TextView textView =(TextView)findViewById(R.id.summary);
                        textView.setText(summ);
                    }
                    else{
                        TextView textView=(TextView)findViewById(R.id.name);
                        textView.setText("No previous transactions");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
