package com.hackathon.offlinemaps;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.hackathon.offlinemaps.RetrofitUtils.ApiClient;
import com.hackathon.offlinemaps.RetrofitUtils.ApiInterface;
import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResults;
import com.hackathon.offlinemaps.RetrofitUtils.ModelAllResultsAutoPlaces;
import com.hackathon.offlinemaps.RetrofitUtils.ModelDescription;
import com.hackathon.offlinemaps.RetrofitUtils.ModelSteps;
import com.hackathon.offlinemaps.SmsUtils.CommonConst;
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
        
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("smsbody");
        Log.e(TAG, message);
        
        //code to extract start and end location
        List<String> tokens = new ArrayList<String>();
        int startindex = 0;
        int endindex = 0;
        String startloc = "";
        String endloc = "";
        StringTokenizer st = new StringTokenizer(message);
        
        //("---- Split by space ------");
        while (st.hasMoreElements()) {
            tokens.add(st.nextElement().toString());
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equalsIgnoreCase("START"))
                startindex = i;
            if (tokens.get(i).equalsIgnoreCase("END"))
                endindex = i;
        }
        for (int i = startindex + 1; i < endindex; i++) {
            startloc = startloc + tokens.get(i) + " ";
        }
        for (int i = endindex + 1; i < tokens.size(); i++) {
            endloc = endloc + tokens.get(i) + " ";
        }
        //code of extract ENDED
        
        //code for autocomplete places
        Call<ModelAllResultsAutoPlaces> callplace = apiService.getAllAutoCompleteData(startloc, API_KEY);
        final String finalStartloc = startloc;
        endloc = endloc.replace("<#>", "");
        final String finalEndloc = endloc.replace(/*Replace hash */ "1VX4II6FyO7", "");
        callplace.enqueue(new Callback<ModelAllResultsAutoPlaces>() {
            @Override
            public void onResponse(Call<ModelAllResultsAutoPlaces> call2, Response<ModelAllResultsAutoPlaces> response) {
                ArrayList<ModelDescription> predictions = response.body().getPredictions();
                ArrayList<String> predict = new ArrayList<>();
                for (ModelDescription description : predictions) {
                    predict.add(description.getDescription());
                }
                if (predict.size() == 1) {
                    //add code for directions send
                    Toast.makeText(getApplicationContext(), finalStartloc + "    " + finalEndloc, Toast.LENGTH_LONG).show();
                    Call<ModelAllResults> call = apiService.getAllDirectionData(finalStartloc, finalEndloc, API_KEY);
                    Log.e("TestActivity", "Reached getPlaces ");
                    call.enqueue(new Callback<ModelAllResults>() {
                        @Override
                        public void onResponse(@NonNull Call<ModelAllResults> call, Response<ModelAllResults> response) {
                            if (response.body() != null) {
                                Log.e("TestActivity", String.valueOf(response.body().getRoute().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions()));
                                
                                ArrayList<ModelSteps> steps = response.body().getRoute().get(0).getLegs().get(0).getSteps();
                                final ArrayList<String> finalMessage = new ArrayList<>();
                                
                                ArrayList<String> htmlInstruction = new ArrayList<>();
                                for (int i = 0; i < steps.size(); i++) {
                                    ModelSteps step = steps.get(i);
                                    htmlInstruction.add(step.getHtmlInstructions());
                                    //Log.e(TAG, (Html.fromHtml(Html.fromHtml(step.getHtmlInstructions()).toString())) + "\n\n");
                                    //Log.e(TAG, String.valueOf(step.getDistance().get("text")) + "\n");
                                    
                                    String htmlText = String.valueOf(Html.fromHtml(Html.fromHtml(step.getHtmlInstructions()).toString()));
                                    String distance = String.valueOf(step.getDistance().get("text"));
                                    
                                    String finalString = htmlText;
                                    if (i == steps.size() - 1)
                                        
                                        //remove "for" for the last step;
                                        finalString = htmlText + " for " + distance;
                                    
                                    else
                                        
                                        finalString = htmlText + " for " + distance + " then";
                                    
                                    finalMessage.add(finalString);
                                    
                                }
                                
                                for (String message : finalMessage) {
                                    //Log.e(TAG, message+"\n");
                                    //SmsHelper.sendDebugSms(String.valueOf(CommonConst.number), SmsHelper.SMS_CONDITION + " " + message);
                                }
                                
                                for(int i =0 ; i < finalMessage.size(); i++)
                                {
                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            SmsHelper.sendDebugSms(String.valueOf(CommonConst.number),   finalMessage.get(finalI));
                                        }
                                    }, 5000*i);
                                }
                                
                                Log.e(TAG, "The length of steps is " + steps.size());
                                
                            }
                        }
                        
                        @Override
                        public void onFailure(Call<ModelAllResults> call, Throwable t) {
                            Log.e("TestActivity", t.toString());
                        }
                        
                    });
                } else {
                    for (String message : predict) {
                        SmsHelper.sendDebugSms(String.valueOf(CommonConst.number), SmsHelper.SMS_CONDITION + "Type in a little more detail\n" + message);
                    }
                }
            }
            
            @Override
            public void onFailure(Call<ModelAllResultsAutoPlaces> call, Throwable t) {
            
            }
        });
        
        //directions send code
        /*Toast.makeText(getApplicationContext(),startloc+"    "+endloc,Toast.LENGTH_LONG).show();
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
                        SmsHelper.sendDebugSms(String.valueOf("8765114937"), SmsHelper.SMS_CONDITION + " "+message);
                    }
                    

                }
            }
            @Override
            public void onFailure(Call<ModelAllResults> call, Throwable t) {
                Log.e("TestActivity", t.toString());
            }
            
        });*/
    }
}
