package domel.ecampus.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import domel.ecampus.Adapters.SubjectThemeAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Component.NoStartSpaceInputFilter;
import domel.ecampus.Model.SubjectTheme;
import domel.ecampus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubjectThirdStepFragment extends Fragment {


    SubjectThemeAdapter adapter;
    AppCompatEditText input;
    AppCompatTextView numberEdit;


    public AddSubjectThirdStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject_third_step, container, false);

        numberEdit = (AppCompatTextView) view.findViewById(R.id.add_theme_enumeration);

        ListViewCompat list = (ListViewCompat) view.findViewById(R.id.list_themes);
        adapter = new SubjectThemeAdapter(getContext(), R.layout.adapter_subject_themes, (BaseActivity)getActivity());
        list.setAdapter(adapter);

        AppCompatImageButton but = (AppCompatImageButton) view.findViewById(R.id.add_theme_button);
        input = (AppCompatEditText) view.findViewById(R.id.add_theme_input);
        input.setFilters(new InputFilter[]{new NoStartSpaceInputFilter(input)});
        input.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        numberEdit.setText((getActivity().getString(R.string.theme_numeration, adapter.getCount() + 1)));


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText().length() == 0){
                    input.setError(getString(R.string.error_field_required));
                    input.requestFocus();
                }else{
                    adapter.add(new SubjectTheme(input.getText().toString()));
                    adapter.notifyDataSetChanged();
                    if ((getActivity().findViewById(R.id.add_theme_enumeration)) != null) {
                        ((AppCompatTextView)getActivity().findViewById(R.id.add_theme_enumeration))
                                .setText(getActivity().getString(R.string.theme_numeration, adapter.getCount() + 1));
                    }
                    input.getText().clear();
                }
            }
        });

        return view;
    }

}
