package com.example.chandrakantchaturvedi.epaisedemo;

import android.content.Context;

import com.example.chandrakantchaturvedi.epaisedemo.Interface.GetDataContract;
import com.example.chandrakantchaturvedi.epaisedemo.Tasks.FetchDataTasks;
import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandrakantchaturvedi on 29/5/18.
 */

public class Intractor implements GetDataContract.Interactor {
    private GetDataContract.onGetDataListener mOnGetDatalistener;
    List<SongItem> allcountry = new ArrayList<>();

    public Intractor(GetDataContract.onGetDataListener mOnGetDatalistener) {
        this.mOnGetDatalistener = mOnGetDatalistener;
    }

    @Override
    public void callServerData(Context context, String url) {
        FetchDataTasks fetchTask = new FetchDataTasks(context, new FetchDataTasks.OnLoadListener() {
            @Override
            public void onLoadComplete(ArrayList<SongItem> songItems) {
                mOnGetDatalistener.onSuccess("", songItems);

            }

            @Override
            public void onError(String error) {
                mOnGetDatalistener.onFailure(error);

            }
        }, url);
        if (Utils.isNetworkAvailable(context)) {
            fetchTask.execute();
        } else {
            Utils.showAlert((context), "Error", "internet connection Error");

        }

    }


}
