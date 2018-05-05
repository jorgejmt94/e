package domel.ecampus.Base;

import android.support.v7.app.AppCompatActivity;

import domel.ecampus.MyApplication;


public class BaseActivity extends AppCompatActivity {

    public MyApplication getApp(){
        return (MyApplication)this.getApplication();
    }
}
