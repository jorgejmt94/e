package domel.ecampus.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import domel.ecampus.Component.NoStartSpaceInputFilter;
import domel.ecampus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubjectFirstStepFragment extends Fragment {


    public AddSubjectFirstStepFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_subject_first_step, container, false);

        AppCompatEditText name = (AppCompatEditText) view.findViewById(R.id.subject_name);
        AppCompatEditText description = (AppCompatEditText) view.findViewById(R.id.subject_description);

        Spinner spinner = (Spinner) view.findViewById(R.id.student_degree);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.degrees, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        name.setFilters(new InputFilter[]{new NoStartSpaceInputFilter(name)});
        description.setFilters(new InputFilter[]{new NoStartSpaceInputFilter(description)});

        return view;
    }

}
