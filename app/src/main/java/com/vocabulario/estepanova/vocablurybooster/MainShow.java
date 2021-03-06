package com.vocabulario.estepanova.vocablurybooster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainShow extends AppCompatActivity{
    SharedPreferences preferences;

    private Dictionary currentDictionary;


    private TextView
            newWord,
            newTrans;

    private Button
            btnGotIt;

    private Integer count;

    private ProgressBar progressBar;

    private ConstraintLayout touchArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));

        touchArea = (ConstraintLayout) findViewById(R.id.touch_area);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        newWord = (TextView) findViewById(R.id.newWordView);
        newTrans = (TextView) findViewById(R.id.newTransView);

        btnGotIt = (Button) findViewById(R.id.gotBtn);

        //on first create, if there's no intent, you should get it from level choice

        final Intent saveIntent = getIntent();

        if (saveIntent.getExtras() == null) {
            initLevelChoice();
        } else {//create an instance of a dictionary
            currentDictionary = (Dictionary) saveIntent.getSerializableExtra("dictionary");
        }

        count=0;

        touchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (count==15) {
                    initWordCheck();
                } else {
                    wordShow();
                }
            }
        });

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if count < 15, show one mord word
                if (count==15) {
                    initWordCheck();
                } else {
                    wordShow();
                }


            }
        });

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(currentDictionary.getProgress());

        wordShow();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.feedback){
            startFeedback();
            return true;
        } else if (item.getItemId()==R.id.activity_welcome){
            startWelcome();
            return true;
        }
        //if the chosen mode is "general", go back to  mode choice, else to topics choice
         else if (currentDictionary.getTopic().equals("general")){
            return(super.onOptionsItemSelected(item));
        }else {
            initTopicsChoice();
            return true;
        }
    }

    private void wordShow() {

        //if count < 15, show one mord word

            String randomKey = currentDictionary.showNewWord(); // get a random word from unlearned array

            if (randomKey.equals("")){
                initWordCheck();
            } else {
                String value = currentDictionary.showTranslation(randomKey); // get the translation for that word
                newWord.setText(randomKey);
                newTrans.setText(value);
            }

            count++;

    }
    

    private void initWordCheck(){
        savePreferences();

        Intent i = new Intent(this, WordCheck.class);
        i.putExtra("dictionary", currentDictionary);
        startActivity(i);
    }

    private void initLevelChoice() {
        startActivity(
                new Intent(this, LevelChoice.class));
    }

    private void initTopicsChoice(){
        Intent i = new Intent(this, TopicsChoice.class);
        String dict_source = currentDictionary.getSource();
        i.putExtra("source", dict_source);
        startActivity(i);
    }

    private void savePreferences(){
        String saved_source = currentDictionary.getSource()+ "-" + currentDictionary.getTopic();
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentDictionary);
        prefsEditor.putString(saved_source, json);
        prefsEditor.commit();
    }

    private void initModeChoice() {
        Intent i = new Intent(this, ModeChoice.class);
        String dict_source = currentDictionary.getSource();
        i.putExtra("source", dict_source);
        startActivity(i);
    }
    private void startFeedback(){
        Intent i = new Intent(this, Feedback.class);
        this.startActivity(i);

    }

    private void startWelcome(){
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("Rewatch", "rewatch");
        this.startActivity(i);

    }

}
