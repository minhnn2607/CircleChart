package vn.nms.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import vn.nms.circle_chart.CircleChart;


public class ExampleActivity extends AppCompatActivity  {
    CircleChart circleChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleChart = findViewById(R.id.circleChart);
        SeekBar seekBar = findViewById(R.id.seekBar);

        circleChart.setMaxVue(100);
        circleChart.setCurrentValue(0);
        seekBar.setMax(100);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                circleChart.setCurrentValue(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
