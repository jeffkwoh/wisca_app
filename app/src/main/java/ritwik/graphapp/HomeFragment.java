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

        final TextView textViewNfcReadData = (TextView) view.findViewById(R.id.textViewNfcReadData);

        Bundle bundleActivity = getArguments();
        if (bundleActivity != null) {
            renderNfcData(textViewNfcReadData, bundleActivity);
        }

        Button buttonTestWrite = (Button) view.findViewById(R.id.buttonTestWrite);
        buttonTestWrite.setOnClickListener((v) -> {
            NfcWriter writer = (NfcWriter) getActivity();
            writer.writeWheelchairWeightToTag(123);
        });

        return view;
    }

    private void renderNfcData(TextView textViewNfcReadData, Bundle bundleActivity) {
        List<String> wheelchairWeightData = bundleActivity
                .getStringArrayList(BUNDLE_WHEELCHAIR_WEIGHT_KEY);
        List<String> patientWeightData = bundleActivity
                .getStringArrayList(BUNDLE_PATIENT_WEIGHT_KEY);

        StringBuilder newString = new StringBuilder();
        appendToStringBuilder(wheelchairWeightData, newString, "Wheelchairweight:\n");
        appendToStringBuilder(patientWeightData, newString, "PatientWeightData:\n");

        textViewNfcReadData.setText(newString.toString());
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
