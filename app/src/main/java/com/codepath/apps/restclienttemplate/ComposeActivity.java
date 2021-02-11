package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    public static final int MAX_TWEET_LENGTH = 280;
    EditText etCompose;
    Button btnTweet;
    TextView tvCharCount;

    TwitterClient client ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        tvCharCount = findViewById(R.id.tvCharCount);

        // set a click listener
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // grab the text the user entered in the compose tweet box
                String tweetContent = etCompose.getText().toString();

                // reject if no tweet
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,
                            "Please enter something to tweet",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // reject if too long
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(ComposeActivity.this,
                            "Tweet is longer than 280 chars",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // if reach this point, users tweet is accepted
                //Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();

                // make API call to twitter
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        // i is for info
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet says: " + tweet.body);
                            // this intent is used for passing the composed tweet back to the
                            // TimelineActivity
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            // set result code and bundle data fro response
                            setResult(RESULT_OK, intent);
                            // close activity and pass data to parent
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        // e is for error
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });


            }
        });

        // listen for any changes in text and keep a live count of num chars in compose body
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(ComposeActivity.this, "afterTextChanged", Toast.LENGTH_SHORT).show();
                tvCharCount.setText(etCompose.length() + "/280");
            }
        });

    }
}