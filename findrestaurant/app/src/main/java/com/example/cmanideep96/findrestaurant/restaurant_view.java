package com.example.cmanideep96.findrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by cmanideep96 on 21/09/2016.
 */
public class restaurant_view extends AppCompatActivity {
    public String city_name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    public RetrieveFeedTask restaurantlist = new RetrieveFeedTask();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_layout);
        TextView app_name = (TextView)findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/GreatVibes-Regular.otf");
        app_name.setTypeface(face);
        Bundle b = getIntent().getExtras();
        city_name = b.getCharSequence("name").toString();



        restaurantlist.execute();
    }



    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;


        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);


        }

        protected JSONObject doInBackground(Void... urls) {

            try {
                String OPEN_WEATHER_MAP = "http://10.0.5.62/try2.php/?location=" + city_name;
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
            try {
                if (data != null) {
                    JSONArray details = data.getJSONArray("result");
                    int i = 0;
                    int k= details.length();
                    List<String> recentHistory = new ArrayList<String>();
                        i=0;

                        while(i!=k){
                            char name =details.getJSONObject(i).getString("name").charAt(0);
                            String n=name+"";
                            n=n.toUpperCase();
                            String S=n+details.getJSONObject(i).getString("name").substring(1);
                            recentHistory.add(i,S+"     "+details.getJSONObject(i).getString("locality"));
                            i=i+1;
                        }
//                    String name = details.getJSONObject(0).getString("name")+","+details.getJSONObject(1).getString("name");
                    TextView textView = (TextView)findViewById(R.id.city_view);
                    textView.setText("Restaurants in "+city_name);
                    ArrayAdapter adapter = new ArrayAdapter<String>(restaurant_view.this, R.layout.restlist, recentHistory);

                    ListView listView = (ListView) findViewById(R.id.restaurant_list_view);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {

                            String item = ((TextView)view).getText().toString();
                            Intent intent = new Intent(restaurant_view.this,aboutrestaurant.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle b = new Bundle();
                            b.putString("name",item);
                            intent.putExtras(b);

                            startActivity(intent);
                            restaurantlist.cancel(true);




                        }
                    });
                }
            } catch (JSONException e) {
                Log.e("MainActivity", "Cannot process JSON results", e);

            }
        }
    }
}




