package com.hackathon.offlinemaps;

import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResults;
import com.hackathon.offlinemaps.RetrofitUtils.ModelRouteResult;

import java.util.ArrayList;

public class Data_Analyze {
    String start_pos="";
    String end_pos="";
    ArrayList<ModelAllResults> route;

    public void results(){
        ModelAllResults modelAllResults=new ModelAllResults();
        modelAllResults.getRoute();
        modelAllResults.getStatus();
        ModelRouteResult modelRouteResult=new ModelRouteResult();
        modelRouteResult.getLegs();
        modelRouteResult.getSummary();

    }
}
