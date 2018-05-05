package domel.ecampus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import domel.ecampus.Adapters.ExamListAdapter;
import domel.ecampus.Adapters.StudentManagerAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Exam;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class ExamsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_list);

        ListView listView = (ListView)  findViewById(R.id.listv_exams);


        ExamListAdapter examAdapter = new ExamListAdapter(
                ExamsListActivity.this,
                R.layout.adapter_exam_list,
                getApp().getExams()
        );

        if (listView != null) {
            listView.setAdapter(examAdapter);
        }

        //add button
        ImageButton addStudentButton = (ImageButton) findViewById(R.id.add_toolbar);
        if (addStudentButton != null) {
            addStudentButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(getApp().getSubjects().size() == 0){
                        Tools.toast(getApplicationContext(), getString(R.string.no_subjects));
                    }else {
                        Intent intent = new Intent(ExamsListActivity.this, ExamEditorActivity.class);
                        startActivity(intent);
                    }
                }

            });
        }

        //back toolbar imageView go to main menu activity
        ImageView backToolbarButton = (ImageView) findViewById(R.id.back_toolbar);
        if (backToolbarButton != null) {
            backToolbarButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ExamsListActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

            });
        }

    }

    //finish this activity going back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
