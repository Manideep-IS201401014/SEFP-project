package com.example.cmanideep96.findrestaurant;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmanideep96 on 21/09/2016.
 */
public class aboutrestaurant extends AppCompatActivity {
    public String restaurantName;
    LinearLayout linearmain;
    CheckBox checkbox;
    String s="";
    String check ="";
    int g=0;
    ArrayList<Integer> k = new ArrayList<Integer>();
    ArrayList<String> summary = new ArrayList<String>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abtrestaurant);
        Bundle b = getIntent().getExtras();
        restaurantName = b.getCharSequence("name").toString();
        RetrieveFeedTask abtrest = new RetrieveFeedTask();
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.otf");
        TextView app_name = (TextView) findViewById(R.id.app_name);
        app_name.setTypeface(face);
        linearmain = (LinearLayout) findViewById(R.id.linear);
        abtrest.execute();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void reset(View view){
        s="";
        check="";
        Toast toast=Toast.makeText(this,"summary cleared",Toast.LENGTH_SHORT);
        toast.show();
        TextView textView = (TextView)findViewById(R.id.ordersummary);
        textView.setText("");
    }
    public void checkout(View view){
        Intent intent = new Intent(aboutrestaurant.this,testlogin.class);

        Bundle b = new Bundle();
//        Inserts a String value into the mapping of this Bundle
        if(check!="") {
            b.putString("summ", check);
            b.putString("cost", g + "");
            b.putString("name",restaurantName);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        else{
            Toast toast =Toast.makeText(this,"Cart is empty",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void submit(View view) {

        for (int i = 0; i < k.size(); i++) {
            CheckBox Checkbox = (CheckBox) findViewById(k.get(i));
            boolean bat = Checkbox.isChecked();
            if (bat == true) {
                s=s+Checkbox.getText()+" ";
            }
        }
        String[] price = s.split("Rs.");

        int f=0;
        for(int i=0;i<price.length;i++){
            if(i!=0){
                try {


                    f = f + Integer.parseInt(price[i].substring(0, 3));
                }catch (Exception e) {
                    f = f + Integer.parseInt(price[i].substring(0, 2));
                }
            }

        }
        int[] count =new int[summary.size()];
        for (int i=0;i<summary.size();i++) {
            String str = s;
            String findStr = summary.get(i);
            int lastIndex = 0;
            count[i] = 0;
            while (lastIndex != -1) {

                lastIndex = str.indexOf(findStr, lastIndex);

                if (lastIndex != -1) {
                    count[i]++;
                    lastIndex += findStr.length();
                }
            }
        }
        String strsum="";
        check="";
        for(int i=0;i<summary.size();i++){
            if(count[i]!=0){
                strsum=strsum+count[i]+" "+summary.get(i)+"\n";
                check=check+count[i]+" "+summary.get(i)+",";
            }
        }
        TextView textView = (TextView) findViewById(R.id.ordersummary);
       textView.setText("Total cost:"+f+"\n"+strsum);
        g=f;
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "aboutrestaurant Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cmanideep96.findrestaurant/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "aboutrestaurant Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cmanideep96.findrestaurant/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject> {

        private Exception exception;
        ProgressBar progressBar;

        protected void onPreExecute() {


        }

        protected JSONObject doInBackground(Void... urls) {

            try {


                String MY_API = "http://192.168.43.38/abtrest.php/?name=" + restaurantName;
                URL url = new URL(MY_API);
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


        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(JSONObject data) {

            try {
                if (data != null) {
                    JSONArray details = data.getJSONArray("result");
                    String name = details.getJSONObject(0).getString("name");
//                    String name = details.getJSONObject(0).getString("name")+","+details.getJSONObject(1).getString("name");
                    TextView textView = (TextView) findViewById(R.id.nametextview);
                    textView.setText(name);
                    String desc = details.getJSONObject(0).getString("describe");
                    TextView textView1 = (TextView) findViewById(R.id.description);
                    textView1.setText("About: \n" + desc);
                    String phone = details.getJSONObject(0).getString("phone");
                    TextView textView2 = (TextView) findViewById(R.id.contact);
                    textView2.setText("Phone no:" + phone);

                    JSONArray details2 = data.getJSONArray("restaurantmenu");

                    ArrayList<String> a1 = new ArrayList<String>();
                    int v = details2.length();

                    int j = 0;
                    while (j != v) {
                        summary.add(details2.getJSONObject(j).getString("menu"));
                        String S = details2.getJSONObject(j).getString("menu") + "       " + "Rs." + details2.getJSONObject(j).getString("price") + "/-";

                        a1.add(S);

                        j = j + 1;
                    }

                    for (int i = 0; i < a1.size(); i++) {
                        checkbox = new CheckBox(aboutrestaurant.this);
                        k.add(View.generateViewId());
                        checkbox.setId(k.get(i));
                        checkbox.setTextSize(16);
                        checkbox.setText(a1.get(i));
                        linearmain.addView(checkbox);
                    }


                }
            } catch (JSONException e) {
                Log.e("MainActivity", "Cannot process JSON results", e);

            }
        }
    }
}
