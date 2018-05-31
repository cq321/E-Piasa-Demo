package com.example.chandrakantchaturvedi.epaisedemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chandrakantchaturvedi.epaisedemo.R;
import com.example.chandrakantchaturvedi.epaisedemo.Tasks.ImageDownloaderTask;
import com.example.chandrakantchaturvedi.epaisedemo.Utils;
import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

public class SongDetailsActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView tvDate;
    private TextView mArtistName;
    private TextView mTrackName;
    private TextView collectionCensoredName;
    private TextView collectionPrice;
    private TextView trackPrice;
    private TextView collectionExplicitness;
    private TextView trackExplicitness;
    private TextView songType;
    private TextView country;
    private TextView songDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SongItem songItem = (SongItem) getIntent().getSerializableExtra("passModel");
        initView();
        setData(songItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setData(SongItem songItem) {
        if (!TextUtils.isEmpty(songItem.getArtworkUrl100())) {
            mImage.setTag(songItem.getArtworkUrl100());
            new ImageDownloaderTask(mImage).execute(songItem.getArtworkUrl100());
        }
        if (!TextUtils.isEmpty(songItem.getReleaseDate())) {
            tvDate.setText("Release Date : " + Utils.getTime(songItem.getReleaseDate()));
        }
        if (!TextUtils.isEmpty(songItem.getArtistName())) {
            mArtistName.setText(songItem.getArtistName());
        }
        if (!TextUtils.isEmpty(songItem.getTrackName())) {
            mTrackName.setText("Track Name :" + songItem.getTrackName());
        }
        if (!TextUtils.isEmpty(songItem.getCollectionCensoredName())) {
            collectionCensoredName.setText("collectionCensoredName : " + songItem.getCollectionCensoredName());
        }
        collectionPrice.setText("collectionPrice : " + songItem.getCollectionPrice() + " " + songItem.getCurrency());

        trackPrice.setText("Track Price  : " + songItem.getTrackPrice() + " " + songItem.getCurrency());

        if (!TextUtils.isEmpty(songItem.getCollectionExplicitness())) {
            collectionExplicitness.setText("collectionExplicitness : " + songItem.getCollectionExplicitness());
        }

        if (!TextUtils.isEmpty(songItem.getCountry())) {
            country.setText("Country : " + songItem.getCountry());
        }
        if (!TextUtils.isEmpty(songItem.getTrackExplicitness())) {
            trackExplicitness.setText("trackExplicitness : " + songItem.getTrackExplicitness());
        }
        if (!TextUtils.isEmpty(songItem.getPrimaryGenreName())) {
            songType.setText("Song Type : " + songItem.getPrimaryGenreName());
        }

        int millis = songItem.getTrackTimeMillis();
        int seconds = (millis / 1000) % 60;

        long minutes = ((millis - seconds) / 1000) / 60;
        songDuration.setText("Song Duration : " + minutes + ":" + seconds);


    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
        tvDate = (TextView) findViewById(R.id.date);
        mArtistName = (TextView) findViewById(R.id.singer_name);
        mTrackName = (TextView) findViewById(R.id.track_name);
        collectionCensoredName = (TextView) findViewById(R.id.collectionCensoredName);
        collectionPrice = (TextView) findViewById(R.id.collectionPrice);
        trackPrice = (TextView) findViewById(R.id.trackPrice);
        collectionExplicitness = (TextView) findViewById(R.id.collectionExplicitness);
        trackExplicitness = (TextView) findViewById(R.id.trackExplicitness);
        songType = (TextView) findViewById(R.id.songType);
        country = (TextView) findViewById(R.id.country);
        songDuration = (TextView) findViewById(R.id.song_duration);

    }


}
