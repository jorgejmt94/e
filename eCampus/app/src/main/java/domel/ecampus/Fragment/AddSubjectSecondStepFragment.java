package domel.ecampus.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import domel.ecampus.Adapters.RegisteredStudentsAdapter;
import domel.ecampus.Base.BaseActivity;
import domel.ecampus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubjectSecondStepFragment extends Fragment {


    public AddSubjectSecondStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject_second_step, container, false);

        ListViewCompat list = (ListViewCompat) view.findViewById(R.id.list_students);
        RegisteredStudentsAdapter adapter = new RegisteredStudentsAdapter(getContext(), R.layout.adapter_registered_students, (BaseActivity)getActivity() );
        list.setAdapter(adapter);

        return view;
    }

}
