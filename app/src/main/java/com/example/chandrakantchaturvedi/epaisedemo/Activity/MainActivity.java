package com.example.chandrakantchaturvedi.epaisedemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.chandrakantchaturvedi.epaisedemo.Interface.GetDataContract;
import com.example.chandrakantchaturvedi.epaisedemo.R;
import com.example.chandrakantchaturvedi.epaisedemo.adapter.SongListAdapter;
import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;
import com.example.chandrakantchaturvedi.epaisedemo.presenter.Presenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetDataContract.View {

    private Presenter mPresenter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new Presenter(MainActivity.this);
        mPresenter.getDataFromURL(MainActivity.this, "https://itunes.apple.com/search?term=Michael+jackson");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onGetDataSuccess(String message, List<SongItem> list) {
        //Toast.makeText(this,"Size of List "+list.size(),Toast.LENGTH_LONG).show();
        SongListAdapter songAdapter = new SongListAdapter(getApplicationContext(), list, new SongListAdapter.launchScreen() {
            @Override
            public void passModel(SongItem songItem) {
                launchNewScreen(songItem);

            }
        });
        recyclerView.setAdapter(songAdapter);

    }

    private void launchNewScreen(SongItem songItem) {
        Intent intent = new Intent(this, SongDetailsActivity.class);
        intent.putExtra("passModel", songItem);
        startActivity(intent);
    }

    @Override
    public void onGetDataFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
