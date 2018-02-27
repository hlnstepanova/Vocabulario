package com.example.estepanova.vocablurybooster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Congrats extends AppCompatActivity{

    private Dictionary currentDictionary;
    private Button btnYay;
    private String dict_source;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrats);


        btnYay = (Button) findViewById(R.id.YayBtn);

        Intent saveIntent = getIntent();

        if (saveIntent.getExtras() == null) {
            //do nothing
        } else {
            currentDictionary = (Dictionary) saveIntent.getSerializableExtra("dictionary");
        }

        btnYay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dict_source = currentDictionary.getSource();

                Intent i = new Intent(v.getContext(), TopicsChoice.class);
                i.putExtra("source", dict_source);
                startActivity(i);

            }
        });


        Log.i("Topics", dict_source);
    }

}