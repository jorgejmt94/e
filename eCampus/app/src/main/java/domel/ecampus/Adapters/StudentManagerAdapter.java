package domel.ecampus.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

import domel.ecampus.Activity.StudentActivity;
import domel.ecampus.Activity.SubjectActivity;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;


public class StudentManagerAdapter extends ArrayAdapter{


    private ArrayList<Student> students;
    private BaseActivity activity;

    public StudentManagerAdapter(Context context, int resource, BaseActivity activity) {

        super(context, resource);
        this.activity = activity;
        students = activity.getApp().getStudents();
    }

    public int getCount(){

        return students.size();
    }

    public Student getItem(int i){

        return students.get(i);
    }


    public View getView(final int position, View convertView, ViewGroup parent){

        View row = convertView;

        if(row == null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_student_manager, parent, false);
            row.setClickable(true);
        }
        //set the info of the students
        AppCompatImageView image = (AppCompatImageView) row.findViewById(R.id.student_image);
        AppCompatTextView name = (AppCompatTextView) row.findViewById(R.id.student_name);
        AppCompatTextView age = (AppCompatTextView) row.findViewById(R.id.student_age);
        AppCompatTextView speciality = (AppCompatTextView) row.findViewById(R.id.student_speciality);
        AppCompatImageButton bin = (AppCompatImageButton) row.findViewById(R.id.delete_student);
        Student student = getItem(position);

        //for compatibily while whole app refactor is done
        if(student.getPath() == null){
            if(image != null) image.setImageResource(student.getImage());
        }else{
            if(student.getPath().startsWith(Tools.ASSETS_PREFIX)){
                String path = student.getPath().substring(Tools.ASSETS_PREFIX_LEN);
                try {
                    image.setImageDrawable(Drawable.createFromStream(getContext().getAssets().open(path), null));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(image != null) image.setImageURI(Uri.parse(student.getPath()));
            }
        }

        name.setText(StringUtils.capitalize(student.getName()));
        age.setText(StringUtils.capitalize(student.getAgeString()));
        speciality.setText(StringUtils.capitalize(student.getSpecialty()));

        //if click teh row go to the student preview
        row.setClickable(true);
        row.setTag(student.getId());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), StudentActivity.class);
                intent.putExtra("id", (int)v.getTag());
                getContext().startActivity(intent);

            }
        });


        //delete button
        bin.setTag(student.getId());
        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //launch alert dialog to ask for deletion.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getContext().getResources().getString(R.string.alert_delete_title));
                builder.setMessage(getContext().getResources().getString(R.string.alert_delete_msg_student));
                builder.setCancelable(true);
                builder.setPositiveButton(getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //MyApplication.deleteSubject(getItem(position)); //this is what should go for data consistance and stuff
                        int item = (int)v.getTag();
                        activity.getApp().deleteStudent(activity.getApp().getStudentById(item));
                        notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton(getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();
            }
        });


        return row;
    }
}
