package domel.ecampus.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class StudentActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        Student st = getApp().getStudentById((int)getIntent().getExtras().getInt("id"));


        //id of the info to the layout
        AppCompatImageView image = (AppCompatImageView) findViewById(R.id.profile_picture);
        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.student_name);
        AppCompatTextView age = (AppCompatTextView) findViewById(R.id.student_birthdate);
        AppCompatTextView speciality = (AppCompatTextView) findViewById(R.id.student_career);
        AppCompatTextView gender = (AppCompatTextView) findViewById(R.id.student_gender);

        //set the info

        //for compatibily while whole app refactor is done
        if(st.getPath() == null){
            if(image != null) image.setImageResource(st.getImage());
        }else{
            if(st.getPath().startsWith(Tools.ASSETS_PREFIX)){
                String path = st.getPath().substring(Tools.ASSETS_PREFIX_LEN);
                try {
                    image.setImageDrawable(Drawable.createFromStream(getAssets().open(path), null));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(image != null) image.setImageURI(Uri.parse(st.getPath()));
            }
        }

        if(name != null) name.setText(StringUtils.capitalize(st.getName()));
        if (age != null) age.setText(StringUtils.capitalize(st.getBithdateString()));
        if(speciality != null) speciality.setText(StringUtils.capitalize(st.getSpecialty()));
        if(gender != null) gender.setText(StringUtils.capitalize(st.getGender()));

        ViewGroup subjects_wrapper = (ViewGroup) findViewById(R.id.subject_wrapper);

        int index = 1;

        for (Subject t : st.getSubjects()){

            AppCompatTextView subject_row = new AppCompatTextView(this);
            String row_text = index++ + ". " + StringUtils.capitalize(t.getName());
            subject_row.setText(row_text);
            subject_row.setPadding(16,8,8,8);
            subject_row.setGravity(Gravity.CENTER_VERTICAL);


            if (subjects_wrapper != null) {
                subjects_wrapper.addView(subject_row, subjects_wrapper.getChildCount(), new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
            }

        }


        if (image != null) {
            image.setOnClickListener(new View.OnClickListener() {
                //go to student manager activity
                @Override
                public void onClick(View view) {
                    Log.d("fullscren", "open");
                    View v = findViewById(R.id.full_screen_wrapper);
                    AppCompatImageView image = (AppCompatImageView) findViewById(R.id.full_screen_image);
                    //for compatibily while whole app refactor is done
                    AppCompatImageView miniature = (AppCompatImageView) view;
                    image.setImageDrawable(miniature.getDrawable());
                    /*Animation bottomDown = AnimationUtils.loadAnimation(StudentActivity.this,
                            R.anim.zoom_in);
                    if (v != null) {
                        v.startAnimation(bottomDown);
                        v.setVisibility(View.VISIBLE);
                    }*/
                    v.setVisibility(View.VISIBLE);

                }
            });
        }

        View close_but = findViewById(R.id.btnClose);
        if (close_but != null) {
            close_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("fullscren", "close");
                    View v = findViewById(R.id.full_screen_wrapper);
                    /*Animation bottomDown = AnimationUtils.loadAnimation(StudentActivity.this,
                            R.anim.zoom_out);
                    if (v != null) {
                        v.startAnimation(bottomDown);
                        v.setVisibility(View.GONE);
                    }*/
                    v.setVisibility(View.GONE);
                }
            });
        }


        //go to main menu button
        ImageView backToolbarButton = (ImageView) findViewById(R.id.back_toolbar);
        if (backToolbarButton != null) {
            backToolbarButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onBackPressed();

                }

            });
        }


    }

    //finish this activity going back
    @Override
    public void onBackPressed() {
        View v = findViewById(R.id.full_screen_wrapper);
        if(v.getVisibility() == View.GONE) {
           super.onBackPressed();
           finish();
       }else{
           v.setVisibility(View.GONE);
       }
    }

}
