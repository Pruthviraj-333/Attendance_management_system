package com.example.sampleproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    ImageView img_logo;
    Animation anim_fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img_logo =findViewById(R.id.iv_login_logo);

        anim_fade= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fade);
        img_logo.startAnimation(anim_fade);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isConnected();

        Handler h1 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    public boolean isConnected()
    {
        boolean connected =false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            connected = networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected();
            Toast.makeText(this, ""+connected, Toast.LENGTH_SHORT).show();
            if (connected == false)
            {
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("Check Connection");
                ad.setMessage("You need Internet to use this app");
                ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                ad.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
            }
        }catch (Exception e)
        {
            Toast.makeText(SplashActivity.this, ""+e, Toast.LENGTH_SHORT).show();
        }
        return connected;
    }

}