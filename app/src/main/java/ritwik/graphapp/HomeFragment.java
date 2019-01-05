package ritwik.graphapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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

        String nfcString = getArguments() == null ?
                null : getArguments().getString("nfcData");
        Log.d("nfcString", nfcString == null ? "null" : nfcString);
        if (nfcString != null) {
            textViewNfcReadData.setText(nfcString);
        }

        return view;
    }
}
