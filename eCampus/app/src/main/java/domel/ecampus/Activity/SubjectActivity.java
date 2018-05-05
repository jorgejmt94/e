package domel.ecampus.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

import domel.ecampus.Adapters.StudentManagerAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.Model.SubjectTheme;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class SubjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);


        //go to subject menu activity
        ImageView backToolbarButton = (ImageView) findViewById(R.id.back_toolbar);
        if (backToolbarButton != null) {
            backToolbarButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubjectActivity.this, SubjectManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

            });
        }

        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.subject_name);
        AppCompatTextView description = (AppCompatTextView) findViewById(R.id.subject_description);
        final Subject subject = getApp().getSubjectById((int)getIntent().getExtras().getInt("id"));

        if (name != null) {
            name.setText(StringUtils.capitalize(subject.getName()));
        }

        if (description != null) {
            description.setText(StringUtils.capitalize(subject.getDescription()));
        }

        ViewGroup themes_wrapper = (ViewGroup) findViewById(R.id.themes_wrapper);

        if (subject.getThemes().size() > 0) {

            int index = 1;

            for (SubjectTheme t : subject.getThemes()) {

                AppCompatTextView theme_row = new AppCompatTextView(this);
                String row_text = index++ + ". " + StringUtils.capitalize(t.getName());
                theme_row.setText(row_text);
                theme_row.setPadding(16, 8, 8, 8);
                theme_row.setGravity(Gravity.CENTER_VERTICAL);


                if (themes_wrapper != null) {
                    themes_wrapper.addView(theme_row, themes_wrapper.getChildCount(), new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                }

            }
        } else {

            AppCompatTextView no_themes = new AppCompatTextView(this);
            no_themes.setText(R.string.no_themes_for_subject);
            no_themes.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            no_themes.setPadding(0, 32, 0, 0);
            no_themes.setGravity(Gravity.CENTER_HORIZONTAL);

            if (themes_wrapper != null) {
                themes_wrapper.addView(no_themes, themes_wrapper.getChildCount(), new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
            }

        }

        ViewGroup students_wrapper = (ViewGroup) findViewById(R.id.students_wrapper);
        int index = 1;
        if (subject.getStudents().size() > 0) {

            for (Student student : subject.getStudents()) {

                View v = getLayoutInflater().inflate(R.layout.row_enrolled_students, students_wrapper, false);

                AppCompatImageView student_image = (AppCompatImageView) v.findViewById(R.id.imageView);
                AppCompatTextView student_name = (AppCompatTextView) v.findViewById(R.id.textView2);
                AppCompatTextView student_spec = (AppCompatTextView) v.findViewById(R.id.textView3);

                //for compatibily while whole app refactor is done
                if(student.getPath() == null){
                    if(student_image != null) student_image.setImageResource(student.getImage());
                }else{
                    if(student.getPath().startsWith(Tools.ASSETS_PREFIX)){
                        String path = student.getPath().substring(Tools.ASSETS_PREFIX_LEN);
                        try {
                            student_image.setImageDrawable(Drawable.createFromStream(getAssets().open(path), null));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(student_image != null) student_image.setImageURI(Uri.parse(student.getPath()));
                    }
                }
                student_name.setText(student.getName());
                student_spec.setText(student.getSpecialty());

                v.setTag(student.getId());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SubjectActivity.this, StudentActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("id", (int) v.getTag());
                        startActivity(intent);
                        //((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //not changing transition just yet cause it goes really slow even tho im lowing the time of the animation.
                    }
                });

                if (students_wrapper != null) {
                    students_wrapper.addView(v);
                }

            }

        } else {

            AppCompatTextView no_themes = new AppCompatTextView(this);
            no_themes.setText(R.string.no_students_for_subject);
            no_themes.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            no_themes.setPadding(0, 32, 0, 0);
            no_themes.setGravity(Gravity.CENTER_HORIZONTAL);

            if (students_wrapper != null) {
                students_wrapper.addView(no_themes, students_wrapper.getChildCount(), new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
            }

        }

        //delete subject
        AppCompatImageButton bin = (AppCompatImageButton) findViewById(R.id.delete_toolbar);
        if (bin != null) {
            bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launch alert dialog to ask for deletion.
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);
                    builder.setTitle(getApplicationContext().getResources().getString(R.string.alert_delete_title));
                    builder.setMessage(getApplicationContext().getResources().getString(R.string.alert_delete_msg_subject));
                    builder.setCancelable(true);

                    builder.setPositiveButton(getApplicationContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getApp().deleteSubject(subject);
                            Intent intent = new Intent(SubjectActivity.this, SubjectManagerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton(getApplicationContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
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
