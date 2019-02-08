package com.hackathon.offlinemaps.FrontEnd;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hackathon.offlinemaps.R;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class PaperOnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
                Toast.makeText(getApplicationContext(), "Swiped from " + oldElementIndex + " to " + newElementIndex, Toast.LENGTH_SHORT).show();
            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                Toast.makeText(getApplicationContext(), "Swiped out right", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("How to use", "Offline mode,send sms to number:xxxxxxxxxx and recieve directions on sms.",
                Color.parseColor("#678FB4"), R.drawable.hotels, R.drawable.key);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Message Format", "loc destination:place,city\n" +
                "start:place,city\n" +
                "mode:walk/two-wheeler/four-wheeler",
                Color.parseColor("#65B0B4"), R.drawable.banks, R.drawable.wallet);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("About Us", "Mobility\n" +
                "Getting directions to a place is possible with a smart phone(google maps).But you may encounter with the problem of" +
                " slow loading of google maps which troubles you in getting directions .So we came up with a solution where you get " +
                "directions to a place with just an SMS which does not require any internet (mobile data) .User needs to send an SMS " +
                "to a specified number and he will be getting directions in the form of a text message.\n",
                Color.parseColor("#9B90BC"), R.drawable.stores, R.drawable.shopping_cart);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}
