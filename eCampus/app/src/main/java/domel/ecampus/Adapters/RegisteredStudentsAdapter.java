package domel.ecampus.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.io.IOException;
import java.util.ArrayList;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;


public class RegisteredStudentsAdapter extends ArrayAdapter<Student> {

    private ArrayList<Student> students;
    private ArrayList<Student> selected_students;
    private BaseActivity activity;

    public RegisteredStudentsAdapter(Context context, int resource, BaseActivity activity) {

        super(context, resource);
        this.activity = activity;
        students = activity.getApp().getStudents();
        selected_students = new ArrayList<>();
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
            row = inflater.inflate(R.layout.adapter_registered_students, parent, false);
            row.setClickable(true);
        }


        AppCompatImageView image = (AppCompatImageView) row.findViewById(R.id.profile_picture);
        AppCompatTextView name = (AppCompatTextView) row.findViewById(R.id.student_name);
        AppCompatCheckBox check = (AppCompatCheckBox) row.findViewById(R.id.checkbox);
        Student student = getItem(position);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatCheckBox cbox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
                cbox.setChecked(!cbox.isChecked());
            }
        });

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

        name.setText(student.getName());
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selected_students.add(getItem(position));
                }else{
                    selected_students.remove(getItem(position));
                }
            }
        });


        return row;
    }

    public ArrayList<Student> getSelectedStudents() {
        return selected_students;
    }
}
