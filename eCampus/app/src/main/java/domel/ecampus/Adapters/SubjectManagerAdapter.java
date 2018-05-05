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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import domel.ecampus.Activity.SubjectActivity;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Subject;
import domel.ecampus.MyApplication;
import domel.ecampus.R;
import domel.ecampus.Tools;

public class SubjectManagerAdapter extends ArrayAdapter {

    private ArrayList<Subject> subjects;
    private BaseActivity activity;

    public SubjectManagerAdapter(Context context, int resource, ArrayList<Subject> arraySubjects, BaseActivity activity) {

        super(context, resource);
        this.activity = activity;
        subjects = arraySubjects;
    }

    public void populateList(ArrayList<Subject> arraySubjects){

        subjects.clear();
        subjects.addAll(arraySubjects);
    }

    public int getCount(){

        return subjects.size();
    }

    public Subject getItem(int i){

        return subjects.get(i);
    }


    public View getView(final int position, View convertView, ViewGroup parent){

        View row = convertView;

        if(row == null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_subject_manager, parent, false);
            row.setClickable(true);
        }

        AppCompatImageView image = (AppCompatImageView) row.findViewById(R.id.subject_image);
        AppCompatTextView name = (AppCompatTextView) row.findViewById(R.id.subject_name);
        AppCompatTextView description = (AppCompatTextView) row.findViewById(R.id.subject_description);
        Subject subject = getItem(position);

        if(subject.getImage() == null){
            image.setImageResource(R.mipmap.la_salle_logo);
        }else{
            try {
                String image_path = subject.getImage().substring(Tools.ASSETS_PREFIX_LEN);
                image.setImageDrawable(Drawable.createFromStream(getContext().getAssets().open(image_path), null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        name.setText(StringUtils.capitalize(subject.getName()));
        description.setText(StringUtils.capitalize(subject.getDescription()));

        row.setClickable(true);
        row.setTag(subject.getId());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SubjectActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("id", (int)v.getTag()); //since we work all time with the same collections we can use the position
                                                   //this should be replaced with some id to query for the real item on a more real situation
                getContext().startActivity(intent);
                //((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //not changing transition just yet cause it goes really slow even tho im lowing the time of the animation.
            }
        });

        //delete subject
        AppCompatImageButton bin = (AppCompatImageButton) row.findViewById(R.id.delete_subject);
        bin.setTag(subject.getId());
        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //launch alert dialog to ask for deletion.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getContext().getResources().getString(R.string.alert_delete_title));
                builder.setMessage(getContext().getResources().getString(R.string.alert_delete_msg_subject));
                builder.setCancelable(true);
                builder.setPositiveButton(getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.getApp().deleteSubject(getItem(position));
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
