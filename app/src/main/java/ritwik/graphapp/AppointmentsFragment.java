package ritwik.graphapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsFragment extends Fragment {

    List<Appointment> appointmentList;
    AppointmentAdapter appointmentAdapter;
    RecyclerView recyclerView;
    String date, weight, reason;

    public AppointmentsFragment(){
        //Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Data structure for cardview
        final List<Appointment> appointmentList = new ArrayList<>(); // TODO: List should be sorted

        appointmentList.add(new Appointment(0, "11 Oct 2018", "99kg", "Finger stuck in fidget spinner"));
        appointmentList.add(new Appointment(0, "11 Sep 2018", "20kg", "Both arms broken"));

        // Set adapter
        final AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getContext(), appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

        // Set button listener
        Button addAppointmentButton = rootView.findViewById(R.id.buttonAddAppointment);
        addAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //onAddItem(v);
                Intent input_form = new Intent(getActivity(), AppointmentInputActivity.class);
                startActivity(input_form);

            }
        });

        //check bundle for new input
        Bundle bundle = getArguments();
        if (bundle != null) {
            date = bundle.getString("date");
            weight = bundle.getString("weight");
            reason = bundle.getString("reason");
            appointmentList.add(new Appointment(0, date, weight, reason));
            appointmentAdapter.notifyDataSetChanged();
        } else {
            appointmentList.add(new Appointment(0, "231", "sddsdq", "dofijsoifsd"));
            appointmentAdapter.notifyDataSetChanged();
        }

        return rootView;
    }

    /*public void onAddItem(View v) {
//        appointmentList.add(new Appointment(0, "231", "sddsdq", "dofijsoifsd"));

        //int id = appointmentList.size() + 1;

        // Method to open New Appointment Form.
        Intent input_form = new Intent(getActivity(), AppointmentInputActivity.class);
        startActivity(input_form);

        date = AppointmentInputActivity.getIntent().getExtras().getString("date");
        weight = getActivity().getIntent().getExtras().getString("weight");
        reason = getActivity().getIntent().getExtras().getString("reason");

//        appointmentList.add(new Appointment(0, date, weight, reason));
//        appointmentAdapter.notifyDataSetChanged();


    }*/

}
