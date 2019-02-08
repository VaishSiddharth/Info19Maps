package com.hackathon.offlinemaps.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

public class ModelDescription {
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }
}
