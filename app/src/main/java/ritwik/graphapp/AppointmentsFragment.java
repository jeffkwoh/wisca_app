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

    public static AppointmentsFragment newInstance(String date, String weight, String reason){
        AppointmentsFragment apptFrag = new AppointmentsFragment();

        //create bundle and pass in date, weight and reason into bundle
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putString("weight", weight);
        bundle.putString("reason", reason);
        apptFrag.setArguments(bundle);

        return apptFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //Add dummy data to appointment list
        appointmentList.add(new Appointment(0, "11 Apr 2018", "99kg", "Weight increased beyond acceptable level"));
        appointmentList.add(new Appointment(1, "11 May 2018", "20kg", "Sudden weight drop"));
        appointmentList.add(new Appointment(2, "13 May 2018", "19kg", "Having fever and nausea"));
        appointmentList.add(new Appointment(3, "22 Jun 2018", "21.5kg", "Review drug prescription amount"));
        appointmentList.add(new Appointment(4, "01  Jul 2018", "23.4kg", "Scheduled check-up"));
        appointmentList.add(new Appointment(5, "01 Aug 2018", "26.8kg", "Scheduled check-up"));
        appointmentList.add(new Appointment(6, "06 Sept 2018", "33.4kg", "Sudden weight increase"));
        appointmentList.add(new Appointment(7, "19 Oct 2018", "23.4kg", "Scheduled check-up"));


        // TODO: bundle is always NULL. Need to figure out why and fix.
        //check bundle for new input
        Bundle bundle = getArguments();
        if (bundle != null) {
            String date = bundle.getString("date");
            String weight = bundle.getString("weight");
            String reason = bundle.getString("reason");
            //Add user input to data structure
            appointmentList.add(new Appointment(0, date, weight, reason));
        }

        // Set button listener
        Button addAppointmentButton = rootView.findViewById(R.id.buttonAddAppointment);
        addAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Open New Appointments Form
                Intent input_form = new Intent(getActivity(), AppointmentInputActivity.class);
                startActivity(input_form);

            }
        });

        // Set adapter
        appointmentAdapter = new AppointmentAdapter(getContext(), appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

        return rootView;
    }
}
