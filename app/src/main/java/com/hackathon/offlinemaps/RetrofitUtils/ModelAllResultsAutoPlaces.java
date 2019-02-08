package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelAllResultsAutoPlaces {
    @SerializedName("predictions")
    private ArrayList<ModelDescription> predictions;
    @SerializedName("status")
    private String status;

    public ArrayList<ModelDescription> getPredictions() {
        return predictions;
    }

    public String getStatus() {
        return status;
    }

}
