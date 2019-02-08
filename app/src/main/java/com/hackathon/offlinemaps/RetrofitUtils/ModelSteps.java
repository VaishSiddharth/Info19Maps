package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ModelSteps
{
    @SerializedName("distance")
    private HashMap<String, Object> distance;
    
    @SerializedName("duration")
    private HashMap<String, Object> duration;
    
    @SerializedName("html_instructions")
    private String htmlInstructions;
    
    @SerializedName("maneuver")
    private String maneuver;
    
    public String getHtmlInstructions() {
        return htmlInstructions;
    }
    
    public String getManeuver() {
        return maneuver;
    }
    
    public HashMap<String, Object> getDistance() {
        return distance;
    }
    
    public HashMap<String, Object> getDuration() {
        return duration;
    }
}
