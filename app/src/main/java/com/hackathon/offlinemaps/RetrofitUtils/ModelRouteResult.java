package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelRouteResult {

    @SerializedName("legs")
    private ArrayList<ModelDirections> legs;
    
    public String getSummary() {
        return summary;
    }
    
    @SerializedName("summary")
    private String summary;
    
    public ArrayList<ModelDirections> getLegs() {
        return legs;
    }
}
