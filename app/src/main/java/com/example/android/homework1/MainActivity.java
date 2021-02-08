package com.example.android.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button button_start;
    private static String api_url = "http://madlibz.herokuapp.com/api/random";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNextActivity(view);
            }
        });
    }

    public void launchNextActivity(View view){

        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    Intent intent = new Intent(MainActivity.this, InputActivity.class);

                    JSONObject json = new JSONObject(new String(responseBody));

                    // pass blanks array
                    // JSONObject blanks = json.getJSONObject("blanks");
                    intent.putExtra("blanks", json.getString("blanks"));

                    // pass title
                    intent.putExtra("title", json.getString("title"));

                    // pass mad lib sentences
                    // JSONObject sentences = json.getJSONObject("values");
                    intent.putExtra("sentences", json.getString("value"));

                    // pass json file to intent
                    intent.putExtra("jsonObject", json.toString());
                    startActivity(intent);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }
}