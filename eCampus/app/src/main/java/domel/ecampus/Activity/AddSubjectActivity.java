package domel.ecampus.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Spinner;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;

import domel.ecampus.Adapters.AddStudentFragmentAdapter;
import domel.ecampus.Adapters.RegisteredStudentsAdapter;
import domel.ecampus.Adapters.SubjectManagerAdapter;
import domel.ecampus.Adapters.SubjectThemeAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Component.RestrictiveViewPager;
import domel.ecampus.Fragment.AddSubjectFirstStepFragment;
import domel.ecampus.Fragment.AddSubjectSecondStepFragment;
import domel.ecampus.Fragment.AddSubjectThirdStepFragment;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class AddSubjectActivity extends BaseActivity {

    private final static int LAST_PAGE = 2;
    private int last_page = -1;
    private float last_position = -1;
    private boolean not_processing = true;

    private RestrictiveViewPager pager;
    private AddStudentFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.number_page);
        title.setText(getString(R.string.page_number) + 1 + getString(R.string.page_number_aux));

        //Set the pager with an adapter
        pager = (RestrictiveViewPager) findViewById(R.id.pager);
        if (pager != null) {
            adapter = new AddStudentFragmentAdapter(getSupportFragmentManager());
            pager.setAdapter(adapter);
        }

        if (pager != null) {
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    Log.d("position", Integer.toString(position));
                    Log.d("last_position", Integer.toString(last_page));
                    if(positionOffset == 0 && positionOffsetPixels == 0 && last_page != position) {

                        switch (position) {

                            case 0:
                                Log.d("inputmethod", "SHOW");
                                imm.toggleSoftInputFromWindow(pager.getWindowToken(), 0, 0);
                                break;
                            case 1:
                                Log.d("inputmethod", "HIDE");
                                imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
                                break;
                            case 2:
                                Log.d("inputmethod", "HIDE");
                                imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
                                break;

                        }
                        last_page = position;
                        Log.d("position_modified_to", Integer.toString(last_page));

                    }else if (position == LAST_PAGE && last_page == LAST_PAGE && not_processing){

                        not_processing = false;
                        submitLastPage();

                    }
                }

                @Override
                public void onPageSelected(int position) {

                    AppCompatTextView title = (AppCompatTextView) findViewById(R.id.number_page);

                    title.setText(getString(R.string.page_number) + " " +(position + 1) + getString(R.string.page_number_aux));
                    if(position == 2){
                        AppCompatImageButton submitButton = (AppCompatImageButton) findViewById(R.id.submit_button);
                        submitButton.setVisibility(View.VISIBLE);
                    }else{
                        AppCompatImageButton submitButton = (AppCompatImageButton) findViewById(R.id.submit_button);
                        submitButton.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //if click the button
            AppCompatImageButton submitButton = (AppCompatImageButton)findViewById(R.id.submit_button);
            if (submitButton != null) {
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitLastPage();

                    }
                });
            }

        }

        //Bind the title indicator to the adapter
        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.pagination);
        if (titleIndicator != null) {
            titleIndicator.setViewPager(pager);
        }

        //back toolbar button, go to main menu activity
        ImageView closeSesionButton = (ImageView) findViewById(R.id.back_toolbar);
        if (closeSesionButton != null) {
            closeSesionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onBackPressed();

                }

            });
        }


    }

    public void submitFormData() {

        AddSubjectFirstStepFragment firstStepFragment = (AddSubjectFirstStepFragment) adapter.getRegisteredFragment(0);
        AddSubjectSecondStepFragment secondStepFragment = (AddSubjectSecondStepFragment) adapter.getRegisteredFragment(1);
        AddSubjectThirdStepFragment thirdStepFragment = (AddSubjectThirdStepFragment) adapter.getRegisteredFragment(2);

        Subject subject = new Subject();

        //process data from first fragment
        AppCompatEditText name = (AppCompatEditText) firstStepFragment.getView().findViewById(R.id.subject_name);
        AppCompatEditText description = (AppCompatEditText) firstStepFragment.getView().findViewById(R.id.subject_description);
        Spinner spinnerDegree = (Spinner) findViewById(R.id.student_degree);
        if (spinnerDegree != null) {
            subject.setDegree(spinnerDegree.getSelectedItem().toString());
        }
        subject.setName(name.getText().toString());
        subject.setDescription(description.getText().toString());

        //process data from third fragment
        SubjectThemeAdapter theme_adapter = (SubjectThemeAdapter)
                ((ListViewCompat) thirdStepFragment.getView().findViewById(R.id.list_themes))
                        .getAdapter();
        subject.addThemes(theme_adapter.getThemes());

        //add image
        subject.setImage(null);

        //At this point the subject itself is fully filled. Persist
        getApp().addSubject(subject);

        //process data from second fragment
        RegisteredStudentsAdapter adapter = (RegisteredStudentsAdapter)
                ((ListViewCompat) secondStepFragment.getView().findViewById(R.id.list_students))
                        .getAdapter();

        ArrayList<Student> reg_students = adapter.getSelectedStudents();
        for (Student s : reg_students) {
            getApp().enroll(s, subject);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void submitLastPage(){

        //launch alert dialog to ask for deletion.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.submit_add_subject_title));
        builder.setMessage(this.getResources().getString(R.string.submit_add_subject));
        builder.setCancelable(true);
        builder.setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddSubjectThirdStepFragment thirdStepFragment =  (AddSubjectThirdStepFragment) adapter.getRegisteredFragment(2);
                SubjectThemeAdapter theme_adapter = (SubjectThemeAdapter)
                        ((ListViewCompat)thirdStepFragment.getView().findViewById(R.id.list_themes))
                                .getAdapter();
                if(!theme_adapter.isEmpty()){
                    //submit the data and close the application
                    submitFormData();
                    Intent intent = new Intent(AddSubjectActivity.this, SubjectManagerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Tools.toast(getApplicationContext(), getString(R.string.subject_create_success));
                    finish();

                }else{
                    AppCompatEditText input = (AppCompatEditText)(thirdStepFragment.getView().findViewById(R.id.add_theme_input));
                    input.setError(getString(R.string.error_theme_required));
                    input.requestFocus();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                not_processing = true;
            }
        });

        builder.create().show();

    }

}
