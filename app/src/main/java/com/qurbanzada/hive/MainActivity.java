package com.qurbanzada.hive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qurbanzada.hive.Services.HiveService;
import com.qurbanzada.hive.Utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String subject, message, nextLink;
    private JSONObject jObject, author, hiveDoc, next;
    private JSONArray jsonArray;
    private Object author_name;
    private ArrayList<Object> ci = null;
    private ArrayList<String> detail;
    private TextView mTextMessage,output;
    private  ArrayAdapter adapter;
    private Document doc;
    private static final int LOAD_MY_CONTENT = 100;
    private boolean networkOk;


    private static final String JSON_URL =
//            "https://hive.springernature.com/api/core/v3/people/12819";
//            "http://560057.youcanlearnit.net/services/json/itemsfeed.php";
//            "https://hive.springernature.com/api/core/v3/places/183441/contents";
//            "https://hive.springernature.com/api/core/v3/contents?filter=search(ahmad*qurbanzada)";
//            "https://hive.springernature.com/api/core/v3/contents?filter=author(https://hive.springernature.com/api/core/v3/people/12520)";
//            "https://hive.springernature.com/api/core/v3/contents?filter=author(https://hive.springernature.com/api/core/v3/people/12520)&filter=type(document,discussion)";
            "https://hive.springernature.com/api/core/v3/contents?filter=author(https://hive.springernature.com/api/core/v3/people/12520)&filter=type(document)&count=25&startIndex=1";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    setContentView(R.layout.activity_dashboard);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(HiveService.MY_SERVICE_MESSAGE));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    public void getContent(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent,LOAD_MY_CONTENT);

        networkOk = Network.hasNetworkAccess(this);
        if(networkOk){
            intent = new Intent(this, HiveService.class);
            intent.setData(Uri.parse(JSON_URL));
            startService(intent);
        }
        else{
            Toast.makeText(this,"Network not available", Toast.LENGTH_SHORT).show();
        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            message = intent.getStringExtra(HiveService.MY_SERVICE_PAYLOAD);

            try {
                jObject = new JSONObject(message);
                jsonArray = jObject.getJSONArray("list");

                next = (JSONObject) jObject.get("links");

                nextLink = next.getString("next");

                ci = new ArrayList<>();
                detail = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    author = (JSONObject) jsonArray.getJSONObject(i).get("author");
                    subject = jsonArray.getJSONObject(i).getString("subject");
                    hiveDoc = (JSONObject) jsonArray.getJSONObject(i).get("content");
                    doc = Jsoup.parse(String.valueOf(hiveDoc.get("text")));
                    ci.add(subject);
                    detail.add(doc.text());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(ci != null){
                setData(ci);
            }

        }
    };


    public void setData(ArrayList<Object> ci)
    {

        List<String> list = new ArrayList<>();

          for(Object item:ci){
              list.add((String) item);
          }
        Collections.sort(list);

//        Log.i("List: ",list.toString());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);



//        DataItemAdapter adapter1 = new DataItemAdapter(this,list);
        setContentView(R.layout.activity_main);
//        ListView listView = (ListView) findViewById(android.R.id.list);
//
//        listView.setAdapter(adapter1);


        DataAdapter adapter = new DataAdapter(this,list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, LOAD_MY_CONTENT);
            networkOk = Network.hasNetworkAccess(this);

            if (networkOk) {
                intent = new Intent(this, HiveService.class);
                intent.setData(Uri.parse(nextLink));
                startService(intent);

            }

        }


        return false;
    }

    public ArrayList<Object> getCi() {
        return ci;
    }

}


