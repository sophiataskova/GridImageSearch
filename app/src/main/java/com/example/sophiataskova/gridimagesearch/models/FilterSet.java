package com.example.sophiataskova.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sophiataskova on 2/14/15.
 */
public class FilterSet implements Parcelable {
    public void setSizeFilter(String sizeFilter) {
        this.sizeFilter = sizeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getSizeFilter() {
        return sizeFilter;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    private String sizeFilter;
    private String typeFilter;
    private String colorFilter;


    @Override
    public int describeContents() {
        return 0;
    }

    public FilterSet(String sizeFilter, String typeFilter, String colorFilter) {
        this.sizeFilter=sizeFilter;
        this.typeFilter=typeFilter;
        this.colorFilter=colorFilter;
    }

    public FilterSet(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.sizeFilter = data[0];
        this.typeFilter = data[1];
        this.colorFilter = data[2];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.sizeFilter,
                this.typeFilter,
                this.colorFilter});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FilterSet createFromParcel(Parcel in) {
            return new FilterSet(in);
        }

        public FilterSet[] newArray(int size) {
            return new FilterSet[size];
        }
    };
}
