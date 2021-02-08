package com.example.android.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    private TextView title;
    private LinearLayout linearLayout;
    private JSONArray blanks;
    private Button button_generate;
    private String sentences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        button_generate = findViewById(R.id.button_generate);
        button_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNextActivity(view);
            }
        });

        Intent intent = getIntent();
        title = findViewById(R.id.textView);


        String titleString = intent.getStringExtra("title");
        title.setText(titleString);

        // get sentences from intent to later pass to next intent
        sentences = intent.getStringExtra("sentences");

        linearLayout = findViewById(R.id.linearLayout_scroll);
        // converting string of blanks back to JSONArray
        String blanksStr = intent.getStringExtra("blanks");
        try {
            blanks = new JSONArray(blanksStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // call function to add an input field for every blank in blanks
        for(int i = 0; i < blanks.length(); i++){
            try {
                Log.println(Log.INFO, "information!!!!!", blanks.get(0).toString());
                addInputField(blanks.get(i).toString(), i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addInputField(String partOfSpeech, int i){
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setText("");
        editText.setId(i);
        linearLayout.addView(editText);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(partOfSpeech);
        linearLayout.addView(textView);
    }

    public void launchNextActivity(View view){
        // check if all input fields have text
        boolean toCont = true;
        for(int i = 0; i < blanks.length(); i++){
            int editTextID_ = getResources().getIdentifier(String.valueOf(i), "id", getPackageName());
            EditText currEditText_ = (EditText)findViewById(editTextID_);
            if(currEditText_.getText().toString().trim().equals("")){
                toCont = false;
                break;
            }
        }
        if(!toCont){
            showToast(view);
        }
        else{
            Intent intent = new Intent(InputActivity.this, MadlibsActivity.class);

            // add mad libs sentences to intent data
            intent.putExtra("sentences", sentences);

            // add user input from editText fields
            intent.putExtra("blanksLength", blanks.length());
            for(int i = 0; i < blanks.length(); i++){
                int editTextID = getResources().getIdentifier(String.valueOf(i), "id", getPackageName());
                EditText currEditText = (EditText)findViewById(editTextID);
                intent.putExtra("value"+i, currEditText.getText().toString().trim());
            }

            startActivity(intent);
        }
    }

    public void showToast(View view){
        // create a toast with a message saying hello
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT); // short -> 2 seconds?
        toast.show();
    }

}
