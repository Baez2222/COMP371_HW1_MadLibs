package com.example.android.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class MadlibsActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private JSONArray sentences;
    private ArrayList<String> blanksList;
    private String textViewtext = "";
    private TextView textView;
    private Button button_goHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_madlibs);

        button_goHome = findViewById(R.id.button2);
        button_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(MadlibsActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        linearLayout = findViewById(R.id.linearLayout_madlibsScroll);
        textView = findViewById(R.id.textView_madlibs);

        // get madlibs data from intent
        try {
            sentences = new JSONArray(intent.getStringExtra("sentences"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int blanksLength = intent.getIntExtra("blanksLength", 0);
        blanksList = new ArrayList<>();
        for (int i = 0; i < blanksLength; i++) {
            blanksList.add(intent.getStringExtra("value" + i));
        }

        // get data into 1 string
        for (int i = 0; i < blanksLength; i++) {
            try {
                textViewtext += sentences.get(i).toString();
                textViewtext += blanksList.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(blanksLength < sentences.length()){
            try {
                textViewtext += sentences.get(blanksLength).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // display data string to activity
        textView.setText(textViewtext);
    }
}
