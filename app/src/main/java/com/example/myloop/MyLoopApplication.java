package com.example.myloop;

import android.app.Application;

import com.parse.Parse;

public class MyLoopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("CGmyrRenhRaFUxkoejDtMWptyuLzB355Rqp8bcd1")
                .clientKey("CsvyOT7jWDUv4X0Pp2X3njtiiSaUHgvpouduvosG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
