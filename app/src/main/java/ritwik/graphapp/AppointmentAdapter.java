package ritwik.graphapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private Context mCtx;
    private List<Appointment> appointmentList;

    public AppointmentAdapter(Context mCtx, List<Appointment> appointmentList) {
        this.mCtx = mCtx;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        // Instantiate layout XML file into its corresponding view objects
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.appointments_list_layout, null);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder viewHolder, int index) {
        Appointment appointment = appointmentList.get(index);
        // TODO: BUG FIX, currently the appointment's attributes are null

        // Binding the date to the viewholder
        viewHolder.textViewReason.setText(appointment.reason);
        viewHolder.textViewWeight.setText(appointment.weight);
        viewHolder.textViewDate.setText(appointment.date);

//        viewHolder.textViewReason.setText("hi");
//        viewHolder.textViewWeight.setText("test");
//        viewHolder.textViewDate.setText("test");
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }


    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReason;
        TextView textViewWeight;
        TextView textViewDate;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: BUG FIX, currently we can't seem to find by id
            this.textViewReason = itemView.findViewById(R.id.textViewReason);
            this.textViewWeight = itemView.findViewById(R.id.textViewWeight);
            this.textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }

}
