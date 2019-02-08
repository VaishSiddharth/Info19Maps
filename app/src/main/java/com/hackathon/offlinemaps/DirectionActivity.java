package com.hackathon.offlinemaps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.hackathon.offlinemaps.RetrofitUtils.ApiClient;
import com.hackathon.offlinemaps.RetrofitUtils.ApiInterface;
import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResults;
import com.hackathon.offlinemaps.RetrofitUtils.ModelSteps;
import com.hackathon.offlinemaps.SmsUtils.SmsHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
        Intent intent = getIntent();
        String message = intent.getStringExtra("smsbody");
        Log.e(TAG,message);
        List<String> tokens = new ArrayList<String>();
        int startindex=0;
        int endindex=0;
        String startloc="";
        String endloc="";
        StringTokenizer st = new StringTokenizer(message);

        //("---- Split by space ------");
        while (st.hasMoreElements()) {
            tokens.add(st.nextElement().toString());
        }
        for(int i=0;i<tokens.size();i++){
            if(tokens.get(i).equalsIgnoreCase("START"))
                startindex=i;
            if(tokens.get(i).equalsIgnoreCase("END"))
                endindex=i;
        }
        for (int i=startindex+1;i<endindex;i++) {
            startloc=startloc+tokens.get(i)+" ";
        }
        for (int i=endindex+1;i<tokens.size();i++) {
            endloc=endloc+tokens.get(i)+" ";
        }
        Toast.makeText(getApplicationContext(),startloc+"    "+endloc,Toast.LENGTH_LONG).show();
        Call<ModelAllResults> call = apiService.getAllDirectionData(startloc, endloc, API_KEY);
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
                        SmsHelper.sendDebugSms(String.valueOf("9149386335"), SmsHelper.SMS_CONDITION + " "+message);
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
