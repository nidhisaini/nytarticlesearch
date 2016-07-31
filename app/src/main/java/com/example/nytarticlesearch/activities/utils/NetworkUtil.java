package com.example.nytarticlesearch.activities.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-network-is-connected
 * http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
 */
public class NetworkUtil {
    static Context context;
    boolean connected = false;

    private static NetworkUtil instance = new NetworkUtil();

    public static NetworkUtil getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public Boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            connected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            //Log.v("Not connected to Internet", e.toString());
        }
        return connected;

    }
}
