package com.codepath.apps.restclienttemplate;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet tweet;
    ImageView ivAvi;
    TextView tvWho;
    TextView tvWhere;
    TextView tvWhat;
    TextView tvWhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        tvWho = findViewById(R.id.tvWho);
        tvWhere = findViewById(R.id.tvWhere);
        tvWhat = findViewById(R.id.tvWhat);
        tvWhen = findViewById(R.id.tvWhen);
        ivAvi = findViewById(R.id.ivAvi);

        tvWho.setText(tweet.user.name);
        tvWhere.setText("@" + tweet.user.screenName);
        tvWhat.setText(tweet.body);
        tvWhen.setText(tweet.createdAt);
        Glide.with(this).load(tweet.user.profileImageUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivAvi);
    }
}
