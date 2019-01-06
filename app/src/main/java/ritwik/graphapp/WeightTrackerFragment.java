package ritwik.graphapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ritwik.graphapp.NfcUtils.NfcConstants.BUNDLE_PATIENT_WEIGHT_KEY;

public class WeightTrackerFragment extends Fragment {

    private TextView mTextMessage;

    private static final String TAG = "WeightLog";

    private LineChart mChart, mChart1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        //setContentView(R.layout.fragment_tracker);

        mTextMessage = (TextView) view.findViewById(R.id.message);

        mChart = (LineChart) view.findViewById(R.id.linechart);

//        mChart.setOnChartGestureListener(WeightLog.this);
//        mChart.setOnChartValueSelectedListener(WeightLog.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> coordinates = new ArrayList<>();

        setUpDummyCoords(coordinates);
        List<String> patientWeightDateList = getArguments() == null ?
                null : getArguments().getStringArrayList(BUNDLE_PATIENT_WEIGHT_KEY);

        if (patientWeightDateList != null) {
            coordinates.addAll(parseToEntries(patientWeightDateList));
        }
        Collections.sort(coordinates, (a, b)-> Math.round(a.getX() - b.getX()));
        LineDataSet set1 = new LineDataSet(coordinates, "Data Set 1");

        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

        mTextMessage = (TextView) view.findViewById(R.id.message);

        return view;
    }

    private List<Entry> parseToEntries(List<String> patientWeightDateList) {
        List<Entry> entryList = new ArrayList<>();
        for (String s : patientWeightDateList) {
            Entry e = parseWeightDateString(s);
            if (e == null) {
                continue;
            }
            entryList.add(e);
        }
        return entryList;
    }

    private Entry parseWeightDateString(String patientWeightDate) {
        int delimiterIndex = patientWeightDate.indexOf(',');
        int weightInGrams = Integer.parseInt(
                patientWeightDate.substring(0, delimiterIndex).trim());

        String dateString =
                patientWeightDate
                        .substring(delimiterIndex + 1, patientWeightDate.length());
        SimpleDateFormat parser = new SimpleDateFormat("dd-mm-yyyy");
        // TODO: Support graph with years
        int month = -1;
        Date date;
        try {
            date = parser.parse(dateString);
            SimpleDateFormat formatter = new SimpleDateFormat("mm");
            month = Integer.parseInt(formatter.format(date));

        } catch (ParseException e) {
            return null;
        }

        return new Entry(month, weightInGrams / 1000f);
    }

    private void setUpDummyCoords(ArrayList<Entry> coordinates) {
        coordinates.add(new Entry(2, 26.2f));
        coordinates.add(new Entry(3, 27.1f));
        coordinates.add(new Entry(4, 27.9f));
        coordinates.add(new Entry(5, 28.6f));
    }

}
