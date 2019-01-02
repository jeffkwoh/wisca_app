package ritwik.graphapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsFragment extends Fragment {
    List<Appointment> appointmentList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Set Layout Manger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Mockdata for recycler view
        List<Appointment> appointmentList = new ArrayList<>(); // TODO: List should be sorted
        appointmentList.add(new Appointment(0, "11 Oct 2018", "99kg", "Finger stuck in fidget spinner"));
        appointmentList.add(new Appointment(0, "11 Sep 2018", "20kg", "Both arms broken"));

        // Set adapter
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getContext(), appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

        return rootView;
    }
}
