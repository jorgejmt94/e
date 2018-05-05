package domel.ecampus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import domel.ecampus.Adapters.StudentManagerAdapter;
import domel.ecampus.Adapters.SubjectManagerAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;

public class StudentManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);

        ListView listView = (ListView)  findViewById(R.id.listv_student);

        StudentManagerAdapter studentAdapter = new StudentManagerAdapter(
                StudentManagerActivity.this,
                R.layout.adapter_subject_manager,
                this
        );

        if (listView != null) {
            listView.setAdapter(studentAdapter);
        }

        //add button
        ImageButton addStudentButton = (ImageButton) findViewById(R.id.addStudent_toolbar);
        addStudentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentManagerActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }

        });

        //back button go to main menu
        ImageView backToolbarButton = (ImageView) findViewById(R.id.back_toolbar);
        backToolbarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();

            }

        });


    }

    //finish this activity going back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
