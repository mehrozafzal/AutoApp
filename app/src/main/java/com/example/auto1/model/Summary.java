package com.example.auto1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Summary implements Parcelable {

    private String manufacture;
    private String manufactureKey;
    private String model;
    private String year;

    public Summary() {
    }


    public String getManufactureKey() {
        if (manufactureKey.equals(""))
            return "key";
        return manufactureKey;
    }

    public void setManufactureKey(String manufactureKey) {
        this.manufactureKey = manufactureKey;
    }

    public String getManufacture() {
        if (manufacture.equals(""))
            return "manufacturer";
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        if (model.equals(""))
            return "model";
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        if (year.equals(""))
            return "year";
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public static Creator<Summary> getCREATOR() {
        return CREATOR;
    }


    public Summary(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.manufacture = data[0];
        this.model = data[1];
        this.year = data[2];
    }

    public static final Creator<Summary> CREATOR = new Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel in) {
            return new Summary(in);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.manufacture,
                this.model,
                this.year});
    }
}
