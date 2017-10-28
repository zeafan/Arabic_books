package com.app.mohamedgomaa.arabic_books;


import android.app.Activity;

public class UpLoadData extends Thread {
    Activity activity;

    @Override
    public void run() {
        super.run();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
