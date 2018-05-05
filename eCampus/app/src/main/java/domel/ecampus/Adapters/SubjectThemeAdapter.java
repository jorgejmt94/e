package domel.ecampus.Adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.SubjectTheme;
import domel.ecampus.R;


public class SubjectThemeAdapter extends ArrayAdapter<SubjectTheme> {

    private ArrayList<SubjectTheme> themes;
    private BaseActivity activity;

    public SubjectThemeAdapter(Context context, int resource, BaseActivity activity) {

        super(context, resource);
        this.activity = activity;
        themes = new ArrayList<>();


    }

    public int getCount(){

        return themes.size();
    }

    public SubjectTheme getItem(int i){

        return themes.get(i);
    }


    public View getView(final int position, View convertView, ViewGroup parent){

        View row = convertView;

        if(row == null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_subject_themes, parent, false);
            row.setClickable(true);
        }


        AppCompatTextView number = (AppCompatTextView) row.findViewById(R.id.numeration);
        AppCompatTextView name = (AppCompatTextView) row.findViewById(R.id.theme_name);
        SubjectTheme theme = getItem(position);

        number.setText(getContext().getString(R.string.theme_numeration, position + 1));
        name.setText(theme.getName());

        AppCompatImageButton up = (AppCompatImageButton) row.findViewById(R.id.button_up);
        AppCompatImageButton down = (AppCompatImageButton) row.findViewById(R.id.button_down);
        AppCompatImageButton delete = (AppCompatImageButton) row.findViewById(R.id.button_delete);

        up.setTag(position);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                if(pos != 0) {
                    Collections.swap(themes, pos, pos - 1);
                    notifyDataSetChanged();
                }
            }
        });

        down.setTag(position);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                if(pos != themes.size() - 1){
                    Collections.swap(themes, pos, pos + 1);
                    notifyDataSetChanged();
                }
            }
        });

        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                themes.remove(pos);
                notifyDataSetChanged();
                if ((activity.findViewById(R.id.add_theme_enumeration)) != null) {
                    ((AppCompatTextView)activity.findViewById(R.id.add_theme_enumeration))
                            .setText(activity.getString(R.string.theme_numeration, getCount() + 1));
                }
            }
        });

        return row;
    }

    public ArrayList<SubjectTheme> getThemes() {
        return themes;
    }

    @Override
    public boolean isEmpty() {
        return themes.isEmpty();
    }

    @Override
    public void add(SubjectTheme object) {
        themes.add(object);
    }


}
