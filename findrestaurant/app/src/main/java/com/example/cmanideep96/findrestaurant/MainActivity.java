package com.example.cmanideep96.findrestaurant;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView app_name = (TextView)findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/GreatVibes-Regular.otf");
        app_name.setTypeface(face);
    }
    public void process(View view){
        Intent intent = new Intent(MainActivity.this,restaurant_view.class);

        Bundle b = new Bundle();
        EditText textView =(EditText)findViewById(R.id.city_view);
//        Inserts a String value into the mapping of this Bundle
        b.putString("name", textView.getText().toString());
        intent.putExtras(b);

        startActivity(intent);
    }
    public void history(View view){
        Intent intent = new Intent(MainActivity.this,login.class);
        Bundle b = new Bundle();
        b.putString("key","history");
        intent.putExtras(b);
        startActivity(intent);
    }
}
