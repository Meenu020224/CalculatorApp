package com.example.simple_calculator3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity2 extends AppCompatActivity {
    private TextView historyTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        historyTextView = findViewById(R.id.historyTextView);
        backButton = findViewById(R.id.backButton);

        // Get history data from intent
        String historyData = getIntent().getStringExtra("history_data");

        // Display history data
        historyTextView.setText(historyData);

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity and return to the previous screen
            }
        });
    }
}
