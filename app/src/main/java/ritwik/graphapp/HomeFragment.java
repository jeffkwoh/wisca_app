package ritwik.graphapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static ritwik.graphapp.NfcUtils.NfcConstants.BUNDLE_PATIENT_WEIGHT_KEY;
import static ritwik.graphapp.NfcUtils.NfcConstants.BUNDLE_WHEELCHAIR_WEIGHT_KEY;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set Button Handler
        Button buttonNfcRead = (Button) view.findViewById(R.id.buttonNfcRead);
        final TextView textViewNfcReadData = (TextView) view.findViewById(R.id.textViewNfcReadData);
        buttonNfcRead.setOnClickListener((View v) -> {
            textViewNfcReadData.setText("READING");
        });


        Bundle bundleActivity = getArguments();
        if (bundleActivity != null) {
            List<String> wheelchairWeightData = bundleActivity
                    .getStringArrayList(BUNDLE_WHEELCHAIR_WEIGHT_KEY);
            List<String> patientWeightData = bundleActivity
                    .getStringArrayList(BUNDLE_PATIENT_WEIGHT_KEY);

            StringBuilder newString = new StringBuilder();
            appendToStringBuilder(wheelchairWeightData, newString, "Wheelchairweight:\n");
            appendToStringBuilder(patientWeightData, newString, "PatientWeightData:\n");

            textViewNfcReadData.setText(newString.toString());
        }

        return view;
    }

    private void appendToStringBuilder(List<String> wheelchairWeightData,
                                       StringBuilder newString,
                                       String precedingString) {
        if (wheelchairWeightData != null) {
            newString.append(precedingString);
            for (String s : wheelchairWeightData) {
                newString.append(s);
                newString.append('\n');

            }
        }
    }
}
