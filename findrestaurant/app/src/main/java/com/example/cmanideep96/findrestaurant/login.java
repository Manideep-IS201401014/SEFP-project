package com.example.cmanideep96.findrestaurant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
public class login extends AppCompatActivity{
    String username="";
    String password="";
    RetrieveFeedTask retrieveFeedTask=new RetrieveFeedTask();
    Bundle b;
    String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView app_name = (TextView)findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/GreatVibes-Regular.otf");
        app_name.setTypeface(face);
        b = getIntent().getExtras();
        try {

            key= b.getCharSequence("key").toString();

        }catch (Exception e) {

        }

    }
    public void Login(View view){
        EditText user =(EditText)findViewById(R.id.username);
        username=user.getText().toString();
        EditText pass=(EditText)findViewById(R.id.password);
        password=pass.getText().toString();
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
                String MY_API = "http://192.168.43.38/login1.php/?user=" + username+"&pass="+password;

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
                        if(key!=""){
                            Intent intent = new Intent(login.this,recenthistory.class);
                            b.putString("username", username);
                            intent.putExtras(b);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(login.this, ordersummary.class);
                            b.putString("username", username);
                            intent.putExtras(b);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else{
                        Toast toast =Toast.makeText(login.this,"Credentials are not correct",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
