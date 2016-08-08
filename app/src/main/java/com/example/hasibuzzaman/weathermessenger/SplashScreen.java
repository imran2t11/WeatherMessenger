package com.example.hasibuzzaman.weathermessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

         new Thread()
         {
             @Override
             public void run() {
                 try {
                     sleep(5000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 startActivity(new Intent(SplashScreen.this,MainActivity.class));
             }
         }.start();

    }
}
