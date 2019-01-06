package ritwik.graphapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AppointmentInputActivity extends AppCompatActivity {

    //public final int id; TODO: id auto-generate and increment

    EditText dateInput;
    EditText weightInput;
    EditText reasonInput;

    String date, weight, reason;

    Button createAppointmentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_input);

        //Read user input from New Appointments form
        dateInput = (EditText) findViewById(R.id.dateInput);
        weightInput = (EditText)findViewById(R.id.weightInput);
        reasonInput = (EditText)findViewById(R.id.reasonInput);

        createAppointmentButton = findViewById(R.id.createAppointmentButton);
        createAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Call method for data input to Cardview.
                date = dateInput.getText().toString();
                weight = weightInput.getText().toString();
                reason = reasonInput.getText().toString();

                //Create a new instance of AppointmentsFragment and pass date, weight and reason
                // TODO: need to pass date, weight and reason to WeightLog and then pass into AppointmentFragment
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                AppointmentsFragment apptFrag = AppointmentsFragment.newInstance(date, weight, reason);
                ft.replace(R.id.fragment_container, apptFrag);
                ft.commit();

            }
        });
    }

}
