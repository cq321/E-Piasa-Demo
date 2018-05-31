package com.example.chandrakantchaturvedi.epaisedemo.Interface;

import android.content.Context;

import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

import java.util.List;

/**
 * Created by chandrakantchaturvedi on 29/5/18.
 */

public interface GetDataContract {
    interface View {
        void onGetDataSuccess(String message, List<SongItem> list);

        void onGetDataFailure(String message);
    }

    interface Presenter {
        void getDataFromURL(Context context, String url);
    }

    interface Interactor {
        void callServerData(Context context, String url);

    }

    interface onGetDataListener {
        void onSuccess(String message, List<SongItem> list);

        void onFailure(String message);
    }
}
