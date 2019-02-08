package com.hackathon.offlinemaps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.hackathon.offlinemaps.RetrofitUtils.ApiClient;
import com.hackathon.offlinemaps.RetrofitUtils.ApiInterface;
import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResults;
import com.hackathon.offlinemaps.RetrofitUtils.ModelSteps;
import com.hackathon.offlinemaps.SmsUtils.SmsHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionActivity extends AppCompatActivity {
    
    private static final String API_KEY = "AIzaSyBIZgWCuEGpS7mkV7Ozx4HzwVjKE35tuL0";
    private static final String TAG = DirectionActivity.class.getSimpleName();

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
        
        
        Call<ModelAllResults> call = apiService.getAllDirectionData("iiitm campus", "dd mall", API_KEY);
        Log.e("TestActivity", "Reached getPlaces ");
        call.enqueue(new Callback<ModelAllResults>() {
            @Override
            public void onResponse(@NonNull Call<ModelAllResults> call, Response<ModelAllResults> response) {
                if (response.body() != null) {
                    Log.e("TestActivity", String.valueOf(response.body().getRoute().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions()));

                    ArrayList<ModelSteps> steps = response.body().getRoute().get(0).getLegs().get(0).getSteps();
                    ArrayList<String> finalMessage = new ArrayList<>();

                    ArrayList<String> htmlInstruction = new ArrayList<>();
                    for ( ModelSteps step: steps)
                    {
                        htmlInstruction.add(step.getHtmlInstructions());
                        Log.e(TAG,  (Html.fromHtml(Html.fromHtml(step.getHtmlInstructions()).toString()))+"\n\n");
                        Log.e(TAG, String.valueOf(step.getDistance().get("text"))+"\n");

                        String htmlText = String.valueOf(Html.fromHtml(Html.fromHtml(step.getHtmlInstructions()).toString()));
                        String distance = String.valueOf(step.getDistance().get("text"));

                        String finalString = htmlText+" for "+distance+" then";
                        finalMessage.add(finalString);

                    }

                    for ( String message : finalMessage)
                    {
                        SmsHelper.sendDebugSms(String.valueOf("8765114937"), SmsHelper.SMS_CONDITION + " "+message);
                    }

                    Log.e(TAG, "The length of steps is "+steps.size());

                }
            }
            @Override
            public void onFailure(Call<ModelAllResults> call, Throwable t) {
                Log.e("TestActivity", t.toString());
            }
            
        });
    }
}
