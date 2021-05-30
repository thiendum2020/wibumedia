package com.example.wibumedia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mylib.charts.BarChart;
import com.example.mylib.components.Legend;
import com.example.mylib.components.XAxis;
import com.example.mylib.components.YAxis;
import com.example.mylib.data.BarData;
import com.example.mylib.data.BarDataSet;
import com.example.mylib.data.BarEntry;
import com.example.mylib.data.Entry;
import com.example.mylib.formatter.IAxisValueFormatter;
import com.example.mylib.highlight.Highlight;
import com.example.mylib.interfaces.datasets.IBarDataSet;
import com.example.mylib.interfaces.datasets.IDataSet;
import com.example.mylib.listener.OnChartValueSelectedListener;
import com.example.mylib.utils.Fill;
import com.example.mylib.utils.MPPointF;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.Models.User;
import com.example.wibumedia.Retrofit.ApiInterface;
import com.example.wibumedia.Retrofit.Common;
import com.example.wibumedia.custom.DayAxisValueFormatter;
import com.example.wibumedia.custom.MyAxisValueFormatter;
import com.example.wibumedia.custom.XYMarkerView;
import com.example.wibumedia.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;


public class ChartActivity extends DemoBase implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    class ChartData {
        private User user;
        private int countPost;

        public ChartData(User user, int countPost) {
            this.user = user;
            this.countPost = countPost;
        }

        public ChartData() {
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getCountPost() {
            return countPost;
        }

        public void setCountPost(int countPost) {
            this.countPost = countPost;
        }
    }
    private final String key = "VSBG";
    private BarChart chart;
    private SeekBar seekBarX;
    private TextView tvX;

    ApiInterface service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = Common.getGsonService();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bar_chart);

        setTitle("BarChartActivity");

        tvX = findViewById(R.id.tvXMax);

        seekBarX = findViewById(R.id.seekBar1);

        seekBarX.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // setting data
        seekBarX.setProgress(6);

        // chart.setDrawLegend(false);
    }

    private void setData(int count) {
        float start = 1;

        ArrayList<BarEntry> values = new ArrayList<>();
        //================================================================================================================//
        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (12);

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "");

            set1.setDrawIcons(false);

            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

            List<Fill> gradientFills = new ArrayList<>();
            gradientFills.add(new Fill(startColor1, endColor1));
            gradientFills.add(new Fill(startColor2, endColor2));
            gradientFills.add(new Fill(startColor3, endColor3));
            gradientFills.add(new Fill(startColor4, endColor4));
            gradientFills.add(new Fill(startColor5, endColor5));

            set1.setFills(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (chart.isPinchZoomEnabled())
                    chart.setPinchZoom(false);
                else
                    chart.setPinchZoom(true);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
                chart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                chart.invalidate();
                break;
            }
            case R.id.animateX: {
                chart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                chart.animateY(2000);
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(2000, 2000);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));

        setData(seekBarX.getProgress());
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivity");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private final RectF onValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        chart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + chart.getLowestVisibleX() + ", high: "
                        + chart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }
}