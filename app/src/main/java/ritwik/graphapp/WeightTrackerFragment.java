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

import java.util.ArrayList;

public class WeightTrackerFragment extends Fragment  {

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

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 30f));
        yValues.add(new Entry(1, 20f));
        yValues.add(new Entry(2, 30f));
        yValues.add(new Entry(3, 60f));
        yValues.add(new Entry(4, 50f));
        yValues.add(new Entry(5, 25f));
        yValues.add(new Entry(6, 40f));
        yValues.add(new Entry(7, 45f));

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

        mTextMessage = (TextView) view.findViewById(R.id.message);

        /*mChart1 = (LineChart) view.findViewById(R.id.linechart2);

//        mChart.setOnChartGestureListener(WeightLog.this);
//        mChart.setOnChartValueSelectedListener(WeightLog.this);

        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(false);

        ArrayList<Entry> yValues2 = new ArrayList<>();

        yValues2.add(new Entry(0, 30f));
        yValues2.add(new Entry(1, 40f));
        yValues2.add(new Entry(2, 50f));
        yValues2.add(new Entry(3, 30f));
        yValues2.add(new Entry(4, 70f));
        yValues2.add(new Entry(5, 35f));
        yValues2.add(new Entry(6, 60f));
        yValues2.add(new Entry(7, 35f));

        LineDataSet set2 = new LineDataSet(yValues2, "Data Set 2");

        set2.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(set2);

        LineData data2 = new LineData(dataSets2);

        mChart1.setData(data2);*/

        return view;
    }

}
