package com.example.sophiataskova.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sophiataskova on 2/14/15.
 */
public class ImageResult implements Parcelable {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.fullUrl = data[0];
        this.thumbUrl = data[1];
        this.title = data[2];
    }

    public ImageResult(JSONObject jsonObject){
        try {
            this.fullUrl = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonArray) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for (int i = 0; i< jsonArray.length(); i++) {
            try {
                results.add(new ImageResult(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.fullUrl,
                this.thumbUrl,
                this.title});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ImageResult createFromParcel(Parcel in) {
            return new ImageResult(in);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
