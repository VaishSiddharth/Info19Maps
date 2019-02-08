package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelDirections {
    
    @SerializedName("steps")
    private ArrayList<ModelSteps> steps;
    
    @SerializedName("distance")
    private HashMap<String, Object> distance;
    
    @SerializedName("duration")
    private HashMap<String, Object> duration;
    
    public ModelDirections(ArrayList<ModelSteps> steps, HashMap<String, Object> distance, HashMap<String, Object> duration) {
        this.steps = steps;
        this.distance = distance;
        this.duration = duration;
    }
    
    public ArrayList<ModelSteps> getSteps() {
        return steps;
    }
    
    public HashMap<String, Object> getDistance() {
        return distance;
    }
    
    public HashMap<String, Object> getDuration() {
        return duration;
    }
}

