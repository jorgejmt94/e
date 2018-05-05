package domel.ecampus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import domel.ecampus.Adapters.SubjectManagerAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;

public class SubjectManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_manager);

        AppCompatImageButton plus_button = (AppCompatImageButton) findViewById(R.id.addStudent_toolbar  );
        if (plus_button != null) {
            plus_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SubjectManagerActivity.this, AddSubjectActivity.class);
                    startActivity(intent);
                }
            });
        }


        ListView listView = (ListView)  findViewById(R.id.listv_subject);


        SubjectManagerAdapter subjectAdapter = new SubjectManagerAdapter(
                SubjectManagerActivity.this,
                R.layout.adapter_subject_manager,
                getApp().getSubjects(), this
        );

        if (listView != null) {
            listView.setAdapter(subjectAdapter);
        }

        //back button
        ImageView backToolbarButton = (ImageView) findViewById(R.id.back_toolbar);
        if (backToolbarButton != null) {
            backToolbarButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubjectManagerActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
