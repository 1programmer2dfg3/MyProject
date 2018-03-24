package com.example.y.lodingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.y.lodingview.view.LodingBarView;

public class MainActivity extends AppCompatActivity {


    private LodingHandler progressHandler = new LodingHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn);
        final LodingBarView progressBarView = (LodingBarView) findViewById(R.id.view);

        assert button != null;
        assert progressBarView != null;
        progressBarView.setPercent(false);
        progressBarView.setStyle(1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressHandler.sendEmptyMessageDelayed(LodingHandler.UPDATE, LodingHandler.TIME);
            }
        });


        progressHandler.setProgress(new LodingHandler.Progress() {
            @Override
            public void setSchedule(int schedule) {
                progressBarView.setCurrentProgress(schedule);
            }

            @Override
            public void onSuccess() {
            }
        });


    }
}
