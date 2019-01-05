package ritwik.graphapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AppointmentInputActivity extends AppCompatActivity {

    //public final int id;

    EditText dateInput;
    EditText weightInput;
    EditText reasonInput;

    String date, weight, reason;

    Button createAppointmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_input);
        FragmentManager manager = getSupportFragmentManager();
        final android.support.v4.app.FragmentTransaction t = manager.beginTransaction();
        final AppointmentsFragment apptFrag = new AppointmentsFragment();

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

                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                bundle.putString("weight", weight);
                bundle.putString("reason", reason);
                apptFrag.setArguments(bundle);

                t.replace(R.id.relativeLayout2, apptFrag);
                t.commit();

            }
        });
    }

}
