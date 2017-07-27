package com.qurbanzada.hive;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        output = (TextView) findViewById(R.id.textView);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }



    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
    }
}
