package com.qurbanzada.hive.Services;

/**
 * Created by aqn3130 on 20/07/2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.qurbanzada.hive.Utils.Http;

import java.io.IOException;

public class HiveService extends IntentService {

    public static final String TAG = "HiveService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    public static final String MY_SERVICE_EXCEPTION = "myServiceException";
    private String username = "ahmad.e.qurbanzada@gmail.com";
    private String password = "solars7M";

    String response;

    public HiveService() {
        super("HiveService");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uri = intent.getData();
        Log.i(TAG, "onHandleIntent: " + uri.toString());


        try {

            response = Http.downloadUrl(uri.toString(),username,password);
//            Log.i(TAG, "onHandleIntent: " + response);

        } catch (IOException e) {
            e.printStackTrace();

            Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
            messageIntent.putExtra(MY_SERVICE_EXCEPTION, e.getMessage());
            LocalBroadcastManager manager =
                    LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
            return;
        }

//        Gson gson = new Gson();
////        Gson data = gson.fromJson(response,Gson.class);
//        Email data = gson.fromJson(response,Email.class);




        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
//        messageIntent.putExtra(MY_SERVICE_PAYLOAD, (Parcelable) ci);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, response);

//        messageIntent.putExtra(MY_SERVICE_PAYLOAD, data);

        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}

