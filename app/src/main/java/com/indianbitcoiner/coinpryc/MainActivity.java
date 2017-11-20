package com.indianbitcoiner.coinpryc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        final Button button = (Button) findViewById(R.id.enterbutton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                goToSecondActivity();
//            }
//        });
        spinner=(ProgressBar)findViewById(R.id.progressBarMain);
        spinner.setVisibility(View.VISIBLE);
        if(isNetworkConnected()) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                            startActivity(i);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();


        }
        else
        {
            Toast.makeText(getApplicationContext(), "Can't Connect to Internet", Toast.LENGTH_SHORT).show();
        }
       // spinner.setVisibility(View.GONE);



      //  goToSecondActivity();

        //textView = (TextView) findViewById(R.id.textViewZeb2);
    }

    public void goToSecondActivity() {

        Intent intent = new Intent(this, SecondActivity.class);


        startActivity(intent);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }


}
