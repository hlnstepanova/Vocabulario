package com.vocabulario.estepanova.vocablurybooster;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CongratsTopic extends AppCompatActivity{

    private Dictionary currentDictionary;
    private Button btnYayTopic;
    private String dict_source;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrats_topic);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));

        btnYayTopic = (Button) findViewById(R.id.TopicYayBtn);

        Intent saveIntent = getIntent();

        if (saveIntent.getExtras() == null) {
            //do nothing
        } else {
            currentDictionary = (Dictionary) saveIntent.getSerializableExtra("dictionary");
        }

        btnYayTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dict_source = currentDictionary.getSource();

                Intent i = new Intent(v.getContext(), TopicsChoice.class);
                i.putExtra("source", dict_source);
                startActivity(i);

            }
        });


    }

}
