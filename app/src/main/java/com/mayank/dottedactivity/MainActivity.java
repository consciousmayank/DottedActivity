package com.mayank.dottedactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * Created by Mayank Joshi on 9/5/16.
 */
public class MainActivity extends AppCompatActivity {

    Button startButton, hideButton;
    CustomDottedProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton= (Button) findViewById(R.id.btn_start);
        hideButton= (Button) findViewById(R.id.btn_hide);
        progressBar= (CustomDottedProgressBar) findViewById(R.id.dotsProgressBar);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.startForward();
            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.stop();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
