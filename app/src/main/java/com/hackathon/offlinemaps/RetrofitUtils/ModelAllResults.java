package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelAllResults {
    @SerializedName("routes")
    private ArrayList<ModelRouteResult> route;
    @SerializedName("status")
    private String status;
    
    public ArrayList<ModelRouteResult> getRoute() {
        return route;
    }
    
    public String getStatus() {
        return status;
    }
}
