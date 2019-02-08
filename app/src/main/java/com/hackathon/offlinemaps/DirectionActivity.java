package com.hackathon.offlinemaps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hackathon.offlinemaps.RetrofitUtils.ApiClient;
import com.hackathon.offlinemaps.RetrofitUtils.ApiInterface;
import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionActivity extends AppCompatActivity {
    
    private static final String API_KEY = "AIzaSyBIZgWCuEGpS7mkV7Ozx4HzwVjKE35tuL0";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        
        //getDirections
        getDirections();
    }
    
    private void getDirections() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        
        
        Call<ModelAllResults> call = apiService.getAllDirectionData("iiitm campus", "Hazira gwl", API_KEY);
        Log.e("TestActivity", "Reached getPlaces ");
        call.enqueue(new Callback<ModelAllResults>() {
            @Override
            public void onResponse(@NonNull Call<ModelAllResults> call, Response<ModelAllResults> response) {
                if (response.body() != null) {
                    Log.e("TestActivity", String.valueOf(response.body().getRoute().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions()));
                }
            }
            @Override
            public void onFailure(Call<ModelAllResults> call, Throwable t) {
                Log.e("TestActivity", t.toString());
            }
            
        });
    }
}
