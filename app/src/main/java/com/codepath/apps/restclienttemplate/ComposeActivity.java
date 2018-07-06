package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bSubmit;
    TwitterClient client;
    EditText etTweet;
    TextView tvCharCount;
    Context context;
    int charCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        bSubmit = findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(this);
        context = this;
        etTweet = findViewById(R.id.etTweet);
        tvCharCount = findViewById(R.id.tvCharCount);
        tvCharCount.setTextColor(Color.GREEN);
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCount = 140 - s.length();
                tvCharCount.setText(Integer.toString(charCount));
                if (charCount>-1) {
                    tvCharCount.setTextColor(Color.GREEN);
                    bSubmit.setText("Tweet");
                } else {
                    tvCharCount.setTextColor(Color.RED);
                    bSubmit.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (charCount == 140)
            Toast.makeText(this, "No characters!", Toast.LENGTH_LONG).show();
        else if (charCount>-1) {
            client = TwitterApp.getRestClient(this);
            etTweet = findViewById(R.id.etTweet);
            client.sendTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response);
                        Intent intent = new Intent();
                        intent.putExtra("Tweet", Parcels.wrap(tweet));
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Too many characters!", Toast.LENGTH_LONG).show();
        }
    }
}
