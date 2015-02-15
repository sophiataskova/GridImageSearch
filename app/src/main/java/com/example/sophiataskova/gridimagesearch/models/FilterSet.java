package com.example.sophiataskova.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sophiataskova on 2/14/15.
 */
public class FilterSet implements Parcelable {

    private String sizeFilter;
    private String typeFilter;
    private String colorFilter;
    private String siteFilter;

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    public FilterSet(String sizeFilter, String typeFilter, String colorFilter, String siteFilter) {
        this.sizeFilter = sizeFilter;
        this.typeFilter = typeFilter;
        this.colorFilter = colorFilter;
        this.siteFilter = siteFilter;
    }

    public FilterSet(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.sizeFilter = data[0];
        this.typeFilter = data[1];
        this.colorFilter = data[2];
        this.siteFilter = data[3];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.sizeFilter,
                this.typeFilter,
                this.colorFilter,
                this.siteFilter});
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
