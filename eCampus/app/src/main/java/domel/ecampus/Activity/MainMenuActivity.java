package domel.ecampus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.deser.Deserializers;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class MainMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //over flow menu (logout)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.overflow_menu_toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_student:
                        Intent intent_st = new Intent(MainMenuActivity.this, AddStudentActivity.class);
                        startActivity(intent_st);
                        return true;
                    case R.id.add_subject:
                        Intent intent_sbj = new Intent(MainMenuActivity.this, AddSubjectActivity.class);
                        startActivity(intent_sbj);
                        return true;
                    case R.id.add_exam:

                        if(getApp().getSubjects().size() == 0){
                            Tools.toast(getApplicationContext(), getString(R.string.no_subjects));
                            return false;
                        }else {
                            Intent intent_ex = new Intent(MainMenuActivity.this, ExamEditorActivity.class);
                            startActivity(intent_ex);
                            return true;
                        }
                    case R.id.exit:
                        finish();
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });


        LinearLayout subject = (LinearLayout)this.findViewById(R.id.linear_subjects);
        LinearLayout student = (LinearLayout)this.findViewById(R.id.linear_students);
        LinearLayout exam = (LinearLayout)this.findViewById(R.id.linear_exam);
        LinearLayout logout = (LinearLayout)this.findViewById(R.id.linear_logout);

        if (subject != null) {
            subject.setOnClickListener(new View.OnClickListener() {
                //go to subject manager activity
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainMenuActivity.this, SubjectManagerActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (student != null) {
            student.setOnClickListener(new View.OnClickListener() {
                //go to student manager activity
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainMenuActivity.this, StudentManagerActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (exam != null) {
            exam.setOnClickListener(new View.OnClickListener() {
                //go to subject manager activity
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainMenuActivity.this, ExamsListActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (logout != null) {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //logout
                    getApp().setRemembered(false);
                    Intent intent_log = new Intent(MainMenuActivity.this, LoginActivity.class);
                    intent_log.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent_log);
                    finish();

                }
            });
        }


    }
    //finish this activity going back
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

}
