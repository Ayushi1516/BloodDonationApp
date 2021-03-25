package com.example.blooddonationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash{

    @Override
    public void initSplash(ConfigSplash configSplash) {
    configSplash.setBackgroundColor(R.color.strokeColor);
    configSplash.setAnimCircularRevealDuration(2000);
    configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
    configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);
    configSplash.setLogoSplash(R.drawable.first);
    configSplash.setAnimCircularRevealDuration(2000);

    }

    @Override
    public void animationsFinished() {
        Intent intent=new Intent(SplashScreen.this,LoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

