package com.example.chandrakantchaturvedi.epaisedemo.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chandrakantchaturvedi on 30/5/18.
 */

public class FetchDataTasks extends AsyncTask<Void, Void, String> {

    private final Context mContext;
    private final OnLoadListener mListener;
    private final String url;
    private ProgressDialog mProgressDialog;

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(mContext, null, "loading data", true, true);
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public FetchDataTasks(Context context, OnLoadListener listener, String url1) {
        mContext = context;
        mListener = listener;
        this.url = url1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       showProgressDialog();
    }

    @Override
    protected String doInBackground(Void... params) {
        BufferedReader reader = null;

        try {

            URL myUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) myUrl
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            int statusCode = conn.getResponseCode();
            if (statusCode != 200) {
                return null;
            }

            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            String response = buffer.toString();
            Log.e("response", response);

            return response;

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String ServiceResponse) {
        super.onPostExecute(ServiceResponse);
        dismissProgressDialog();

        if (ServiceResponse != null) {
            // mListener.onLoadComplete(ServiceResponse);
            deserializeServiceResponse(ServiceResponse);
        }
    }

    private void deserializeServiceResponse(String response) {
        ArrayList<SongItem> songItems = new ArrayList<>();


        try {
            JSONObject responseJson = new JSONObject(response);
            JSONArray dataJson = responseJson.getJSONArray("results");


            for (int i = 0; i < dataJson.length(); i++) {
                SongItem songItem = new SongItem();

                JSONObject dataJsonJSONObject = dataJson.getJSONObject(i);

                songItem.setArtistId(dataJsonJSONObject.optInt("artistId"));
                songItem.setWrapperType(dataJsonJSONObject.optString("wrapperType"));
                songItem.setKind(dataJsonJSONObject.optString("kind"));
                songItem.setCollectionId(dataJsonJSONObject.optInt("collectionId"));
                songItem.setTrackId(dataJsonJSONObject.optInt("trackId"));
                songItem.setArtistName(dataJsonJSONObject.optString("artistName"));
                songItem.setCollectionName(dataJsonJSONObject.optString("collectionName"));
                songItem.setTrackName(dataJsonJSONObject.optString("trackName"));
                songItem.setCollectionCensoredName(dataJsonJSONObject.optString("collectionCensoredName"));
                songItem.setTrackCensoredName(dataJsonJSONObject.optString("trackCensoredName"));
                songItem.setArtistViewUrl(dataJsonJSONObject.optString("artistViewUrl"));
                songItem.setCollectionViewUrl(dataJsonJSONObject.optString("collectionViewUrl"));
                songItem.setTrackViewUrl(dataJsonJSONObject.optString("trackViewUrl"));
                songItem.setPreviewUrl(dataJsonJSONObject.optString("previewUrl"));
                songItem.setArtworkUrl30(dataJsonJSONObject.optString("artworkUrl30"));
                songItem.setArtworkUrl60(dataJsonJSONObject.optString("artworkUrl60"));
                songItem.setArtworkUrl100(dataJsonJSONObject.optString("artworkUrl100"));
                songItem.setCollectionPrice(dataJsonJSONObject.optDouble("collectionPrice"));
                songItem.setTrackPrice(dataJsonJSONObject.optDouble("trackPrice"));
                songItem.setReleaseDate(dataJsonJSONObject.optString("releaseDate"));
                songItem.setCollectionExplicitness(dataJsonJSONObject.optString("collectionExplicitness"));
                songItem.setTrackExplicitness(dataJsonJSONObject.optString("trackExplicitness"));
                songItem.setDiscCount(dataJsonJSONObject.optInt("discCount"));
                songItem.setDiscNumber(dataJsonJSONObject.optInt("discNumber"));
                songItem.setTrackCount(dataJsonJSONObject.optInt("trackCount"));
                songItem.setTrackNumber(dataJsonJSONObject.optInt("trackNumber"));
                songItem.setTrackTimeMillis(dataJsonJSONObject.optInt("trackTimeMillis"));
                songItem.setCountry(dataJsonJSONObject.optString("country"));
                songItem.setCurrency(dataJsonJSONObject.optString("currency"));
                songItem.setPrimaryGenreName(dataJsonJSONObject.optString("primaryGenreName"));
                songItem.setIsStreamable(dataJsonJSONObject.optBoolean("isStreamable"));

                songItems.add(songItem);


            }

            mListener.onLoadComplete(songItems);

        } catch (JSONException e) {
            e.printStackTrace();

            mListener.onError("Some Thing went wrong");
        }

    }

    public interface OnLoadListener {

        void onLoadComplete(ArrayList<SongItem> songItems);

        void onError(String error);

    }
}
