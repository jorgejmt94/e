package domel.ecampus.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.StatusInstance;
import domel.ecampus.R;


public class WelcomeActivity  extends BaseActivity {

    private final static int START_DELAY = 2000;
    private Timer t;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        t = new Timer();

    }

    @Override
    protected void onResume(){
        super.onResume();
        getApp().loadStatusFromDisk();
        getApp().updateIndexs();
        if(getApp().isRemembered()){
            task = new TimerTask(){
                @Override
                public void run() {
                    //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            t.schedule(task, START_DELAY);
        }else{
            task = new TimerTask(){
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            t.schedule(task, START_DELAY);
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        task.cancel();
    }


}