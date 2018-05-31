package com.example.chandrakantchaturvedi.epaisedemo.presenter;

import android.content.Context;

import com.example.chandrakantchaturvedi.epaisedemo.Interface.GetDataContract;
import com.example.chandrakantchaturvedi.epaisedemo.Intractor;
import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

import java.util.List;

/**
 * Created by chandrakantchaturvedi on 29/5/18.
 */

public class Presenter implements GetDataContract.Presenter, GetDataContract.onGetDataListener {
    private GetDataContract.View mGetDataView;
    private Intractor mIntractor;
    public Presenter(GetDataContract.View mGetDataView){
        this.mGetDataView = mGetDataView;
        mIntractor = new Intractor(this);
    }
    @Override
    public void getDataFromURL(Context context, String url) {
        mIntractor.callServerData(context,url);
    }

    @Override
    public void onSuccess(String message, List<SongItem> allData) {
        mGetDataView.onGetDataSuccess(message, allData);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
