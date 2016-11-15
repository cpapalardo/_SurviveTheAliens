package com.ep4.survivethealiens.Helper;

import android.os.Handler;
import android.util.Log;

import static android.R.attr.value;

/**
 * Created by Carla on 12/10/2016.
 */

public class DistanceLogWriter {
    private final static int INTERVAL = 1000 * 60 * 1; //1 minutes

    Handler handler = new Handler();
    boolean stop = false;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("MyLog", String.valueOf(value));

            if (!stop) {
                handler.postDelayed(runnable, INTERVAL);
            }
        }
    };

    // start logging by calling this method
    public void print() {
        handler.post(runnable);
    }

    // stop logging by calling this method
    public void printStop() {
        stop = true;
    }
}
