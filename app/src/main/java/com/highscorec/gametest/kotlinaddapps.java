package com.highscorec.gametest;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class kotlinaddapps  extends Application {

    @Override

    public void onCreate(){
        super.onCreate();
        MobileAds.initialize(this);

    }

}
