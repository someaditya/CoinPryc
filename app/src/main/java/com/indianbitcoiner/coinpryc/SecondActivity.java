package com.indianbitcoiner.coinpryc;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.util.Locale;


public class SecondActivity extends AppCompatActivity {

    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;
    public TextView textView5;
    public TextView textView6;
    public TextView textView7;
    public TextView textView8;
    public TextView textView9;
    public TextView textView10;
    public ProgressBar spinner;
    private ShareActionProvider mShareActionProvider;

    public double min = 0.00;
    public double max = 0.00;
    int flag = 0;
    int flag1 = 0;
    public Long forex=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Support for Toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Floating Action Button Code

        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianbitcoiner.com/appbuyindia"));
                startActivity(intent);
                            }
        });

        final ImageButton button1 = (ImageButton) findViewById(R.id.imageButton1);
        final ImageButton button2 = (ImageButton) findViewById(R.id.imageButton2);
        final ImageButton button3 = (ImageButton) findViewById(R.id.imageButton3);
        final ImageButton button4 = (ImageButton) findViewById(R.id.imageButton4);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://link.zebpay.com/ref/REF48456999"));
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unocoin.com/?referrerid=228628"));
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://coinsecure.in"));
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://paxful.com/roots/buy-bitcoin/index?affiliate=86JYM79DQvg"));
                startActivity(intent);
            }
        });



        //Declaration of Text Views and Spinner

        textView1 = (TextView) findViewById(R.id.textViewZeb2);
        textView2 = (TextView) findViewById(R.id.textViewZeb3);
        textView3 = (TextView) findViewById(R.id.textViewUno2);
        textView4 = (TextView) findViewById(R.id.textViewUno3);
        textView5 = (TextView) findViewById(R.id.textViewSec2);
        textView6 = (TextView) findViewById(R.id.textViewSec3);
        textView7 = (TextView) findViewById(R.id.textViewGlobal1);
        textView8 = (TextView) findViewById(R.id.textViewGlobal2);
        textView9 = (TextView) findViewById(R.id.textViewpax2);
        textView10 = (TextView) findViewById(R.id.textViewpax3);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        // Call for Asynctasks

        Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show();
        new ForexRetrieve().execute();
        new ZebpayRetrieve().execute();
        new UnocoinRetrieve().execute();
        new CoinsecRetrieve().execute();
        new PaxfulRetrieve().execute();
        new PaxfulSell().execute();
        new CoinmarketRetrieve().execute();


    }

    // Menu inflater
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Options Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cpmenu:
                Intent activitystart = new Intent(SecondActivity.this,SecondActivity.class);
                SecondActivity.this.startActivity(activitystart);
                return true;
            case R.id.usdmenu:
                // User chose the "Settings" item, show the app settings UI...
                Intent activity = new Intent(SecondActivity.this,ThirdActivity.class);
                SecondActivity.this.startActivity(activity);
                return true;

            case R.id.inrmenu:
                Intent activity3 = new Intent(SecondActivity.this,SecondActivity.class);
                SecondActivity.this.startActivity(activity3);
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            case R.id.altcoinmenu:
                Intent activity1 = new Intent(SecondActivity.this,FourthActivity.class);
                SecondActivity.this.startActivity(activity1);
                return true;
            case R.id.portfoliomenu:
                Intent activity2 = new Intent(SecondActivity.this,FifthActivity.class);
                SecondActivity.this.startActivity(activity2);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.investment:
                Intent activity4 = new Intent(SecondActivity.this,SixthActivity.class);
                SecondActivity.this.startActivity(activity4);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.newsmenu:
                Intent activity6 = new Intent(SecondActivity.this,NewsActivity.class);
                SecondActivity.this.startActivity(activity6);
                return true;
            case R.id.about:
                Intent activity5 = new Intent(SecondActivity.this,AboutActivity.class);
                SecondActivity.this.startActivity(activity5);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case  R.id.rate:
              Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.indianbitcoiner.coinpryc"));
               startActivity(intent);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
       // return super.onOptionsItemSelected(item);
    }

    // Asynctask to get response from Zebpay

    private class ZebpayRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res;

        protected void onPreExecute() {

            textView1.setText("");
            textView2.setText("");
            spinner.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {


            try {

                URL url = new URL("https://www.zebapi.com/api/v1/market/ticker/btc/inr");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                res = urlConnection.getResponseCode();
                System.out.println(" The response code is"+res);

                if(res<400) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        System.out.println(stringBuilder);
                        Log.d("Zebpay ", stringBuilder.toString());

                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else
                {
                    return null;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_LONG).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            String zebpaybuy=null;
            String zebpaysell=null;
            if (response == null) {
                response = "THERE WAS AN ERROR";
                zebpaybuy = "0";
                zebpaysell = "0";
                Toast.makeText(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    zebpaybuy = object.getString("buy");
                    zebpaysell = object.getString("sell");
                    Toast.makeText(getApplicationContext(), "Retrieving Data", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    // Appropriate error handling code
                }
                Log.i("INFO", response);
            }

                textView1.setText(zebpaybuy);
                textView2.setText(zebpaysell);

                min = Double.parseDouble(zebpaybuy);
                System.out.println("The Minimum price is " + min);

                if (Double.parseDouble(zebpaybuy) !=0 && Double.parseDouble(zebpaybuy) <= min ) {
                    flag = 1;
                }

                max = Double.parseDouble(zebpaysell);
                System.out.println("The Maximum price is " + max);

                if (Double.parseDouble(zebpaysell) >= max) {
                    flag1 = 1;
                }
        }
    }

    // Asynctask to get Response from Unocoin

    private class UnocoinRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res;

        protected void onPreExecute() {

            textView3.setText(" ");
            textView4.setText(" ");
            spinner.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {

            try {

                URL url = new URL("https://www.unocoin.com/trade?all");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                res = urlConnection.getResponseCode();

                System.out.println(" Unocoin The response code is "+res);

                if (res < 400) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        System.out.println("Unocoin" + stringBuilder);
                        bufferedReader.close();
                        return stringBuilder.toString();
                    }
                    finally {
                        urlConnection.disconnect();
                    }
                } else {
                    return null;
                }
            }
            catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {

            String unocoinbuy=null;
            String unocoinsell=null;

            if (response == null) {
                response = "THERE WAS AN ERROR";
                unocoinbuy="0";
                unocoinsell="0";
                Toast.makeText(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    unocoinbuy = object.getString("buy");
                    unocoinsell = object.getString("sell");

                } catch (JSONException e) {
                    // Appropriate error handling code
                }
                Toast.makeText(getApplicationContext(), "Retrieving Data", Toast.LENGTH_SHORT).show();
            }
            Log.i("INFO", response);
            textView3.setText(unocoinbuy);
            textView4.setText(unocoinsell);

            if(min==0)
            {
                min =Double.parseDouble(unocoinbuy);
            }

            System.out.println(" Value "+Double.parseDouble(unocoinbuy));

            if (Double.parseDouble(unocoinbuy)!= 0 && Double.parseDouble(unocoinbuy)<=min)
            {
                min = Double.parseDouble(unocoinbuy);

                flag =2;
            }
            System.out.println("The Minimum price is "+min);


            if (Double.parseDouble(unocoinsell)>=max)
            {
                max = Double.parseDouble(unocoinsell);
                flag1 =2;
            }
        }
    }

    //Asynctask to retrieve from CoinSecure

    private class CoinsecRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res = 0;

        protected void onPreExecute() {

            textView5.setText("");
            textView6.setText("");
            spinner.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.coinsecure.in/v1/exchange/ticker");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                res = urlConnection.getResponseCode();

                System.out.println(" Coinsecure: The response code is "+res);

                if (res <400) {

                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }

                        Log.d("Coinsecure ", stringBuilder.toString());

                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else
                {
                    return null;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            long zebpaybuy=0;
            long zebpaysell=0;
            String coinsec1=null;
            String coinsec2=null;

            if (response == null) {
                response = "THERE WAS AN ERROR";
                coinsec1 = "0";
                coinsec2 = "0";
                Toast.makeText(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONObject message = object.getJSONObject("message");
                    zebpaybuy = message.getLong("ask");
                    zebpaysell = message.getLong("bid");
                    System.out.println("Ask : " + zebpaybuy + "Bid:" + zebpaysell);

                    zebpaybuy = (zebpaybuy / 100);
                    zebpaysell = zebpaysell / 100;
                    coinsec1 = Long.toString(zebpaybuy);
                    coinsec2 = Long.toString(zebpaysell);
                    Toast.makeText(getApplicationContext(), "Retrieving Data", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {

                }
            }
            Log.i("INFO", response);
            textView5.setText(coinsec1);
            textView6.setText(coinsec2);

            if(min==0)
            {
                min =Double.parseDouble(coinsec1);
            }

            if (Double.parseDouble(coinsec1)!=0 && Double.parseDouble(coinsec1)<=min)
            {
                min = Double.parseDouble(coinsec1);
                flag =3;
            }
            System.out.println("The Minimum price is "+min);
            if (Double.parseDouble(coinsec2)>=max)
            {
                max = Double.parseDouble(coinsec2);
                flag1 =3;
            }

        }
    }


    //Aynctask for Paxful Buy

    private class PaxfulRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res;

        protected void onPreExecute() {

            textView9.setText("");
            textView10.setText("");
            spinner.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {


            try {
                URL url = new URL("https://paxful.com/buy-bitcoin/with-any-payment-method/INR?format=json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                res = urlConnection.getResponseCode();
                System.out.println(" Paxful: The response code is "+res);

                if (res<400) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }

                        Log.d("Paxful ", stringBuilder.toString());
                        bufferedReader.close();
                        return stringBuilder.toString();

                    }
                    finally {
                        urlConnection.disconnect();
                    }
                }
                else
                {
                    return  null;
                }
            } catch (Exception e) {

                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();

                return null;


            }
        }

        protected void onPostExecute(String response) {

            String paxfulbuy1=null;
            Long paxfulbuy2;

            if (response == null) {
                response = "THERE WAS AN ERROR";
                paxfulbuy1 = "0";
                Toast.makeText(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray arr = object.getJSONArray("data");

                    for (int i = 0; i < arr.length() - 1; i++) {
                        JSONObject objects = arr.getJSONObject(i);
                        paxfulbuy1 = objects.getString("currency_code");

                        System.out.println("Found Currency " + paxfulbuy1);
                        if (paxfulbuy1.equals("INR")) {
                            paxfulbuy2 = objects.getLong("fiat_price_per_btc");
                            paxfulbuy1 = paxfulbuy2.toString();
                            System.out.println("Price in INR:" + paxfulbuy2);
                            break;

                        } else if (paxfulbuy1.equals("USD")) {

                            paxfulbuy2 = objects.getLong("fiat_price_per_btc");
                            System.out.println("Price in Currency:" + paxfulbuy2);
                            paxfulbuy2 = paxfulbuy2 * forex;
                            paxfulbuy1 = paxfulbuy2.toString();

                            System.out.println("After Forex :" + paxfulbuy1);
                            break;

                        } else {
                            paxfulbuy1 = "NA";
                        }
                        Toast.makeText(getApplicationContext(), "Retrieving Data", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.e("Error", e.getMessage(), e);
                    // Appropriate error handling code
                }

            }
            Log.i("INFO", response);
            textView9.setText(paxfulbuy1);

            if (min==0)
            {
                min = Double.parseDouble(paxfulbuy1);
            }

            if (Double.parseDouble(paxfulbuy1)!= 0 && Double.parseDouble(paxfulbuy1)<=min)
            {
                min = Double.parseDouble(paxfulbuy1);
                flag =4;
            }
            System.out.println("The Minimum price is "+min);



        }
    }

    //Asynctask for Paxfull Sell


    private class PaxfulSell extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res=0;

        protected void onPreExecute() {

            textView10.setText("");
            spinner.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {

            try {

                URL url = new URL("https://paxful.com/sell-bitcoin/with-any-payment-method/INR?format=json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                res = urlConnection.getResponseCode();

                if (res<400) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        System.out.println(stringBuilder);
                        Log.d("Paxful ", stringBuilder.toString());

                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else
                {
                    return null;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {

            String paxfulbuy1=null;
            Long paxfulbuy2;

            if (response == null) {
                response = "THERE WAS AN ERROR";
                paxfulbuy1 ="0";
                Toast.makeText(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray arr = object.getJSONArray("data");

                    for (int i = 0; i < arr.length() - 1; i++) {
                        JSONObject objects = arr.getJSONObject(i);
                        paxfulbuy1 = objects.getString("currency_code");

                        System.out.println("Found Currency " + paxfulbuy1);
                        if (paxfulbuy1.equals("INR")) {
                            paxfulbuy2 = objects.getLong("fiat_price_per_btc");
                            paxfulbuy1 = paxfulbuy2.toString();
                            System.out.println("Price in INR:" + paxfulbuy2);


                        } else if (paxfulbuy1.equals("USD")) {

                            paxfulbuy2 = objects.getLong("fiat_price_per_btc");
                            System.out.println("Price in Currency:" + paxfulbuy2);
                            paxfulbuy2 = paxfulbuy2 * forex;
                            paxfulbuy1 = paxfulbuy2.toString();

                            System.out.println("After Forex :" + paxfulbuy1);


                        } else {
                            paxfulbuy1 = "NA";
                        }

                    }
                    Toast.makeText(getApplicationContext(), "Retrieving Data", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Log.e("Error", e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    // Appropriate error handling code
                }
            }
            Log.i("INFO", response);
            textView10.setText(paxfulbuy1);
            if (Double.parseDouble(paxfulbuy1)>=max)
            {
                max = Double.parseDouble(paxfulbuy1);
                flag1 =4;
            }
        }
    }

    //Asynctask for Forex

    private class ForexRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res=0;

        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {


            try {
                URL url = new URL("http://api.fixer.io/latest?base=USD");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                res = urlConnection.getResponseCode();
                if (res < 400) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        System.out.println("Forex :" + stringBuilder);
                        //Log.d("Forex",stringBuilder.toString());

                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } else {
                    return null;
                }
            }
            catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {

            if (response == null) {
                response = "THERE WAS AN ERROR";
                forex = Long.parseLong("1");
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONObject rates = object.getJSONObject("rates");
                    forex = rates.getLong("INR");

                    System.out.println("Rates " + rates);
                    System.out.println("Forex Rate:" + forex);


                } catch (JSONException e) {
                    // Appropriate error handling code
                    Log.e("Error", e.getMessage());
                }
                Log.i("INFO", response);
            }

        }
    }


    //Asynctask for Coinmarket API

    private class CoinmarketRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res =0;

        protected void onPreExecute() {

            textView7.setText("");
            textView8.setText("");
            spinner.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/global/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                res = urlConnection.getResponseCode();
                if (res<400)
                {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    System.out.println(stringBuilder);
                    Log.d("CoinmarketCap",stringBuilder.toString());

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }}
                else{
                    return  null;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {

            Long marketcap = null;
            Long marketvol = null;
            if (response == null) {
                response = "THERE WAS AN ERROR";
                marketcap = Long.parseLong("0");
                marketvol = Long.parseLong("0");
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    marketcap = object.getLong("total_market_cap_usd");
                    marketvol = object.getLong("total_24h_volume_usd");
                    marketcap = marketcap * forex;
                    marketvol = marketvol * forex;

                    System.out.println(forex);
                    // zebpaysell = object.getString("sell");

                } catch (JSONException e) {
                    // Appropriate error handling code
                }
                Log.i("INFO", response);
            }
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("en", "IN"));
            //new DecimalFormat("##,##,##0").format(amount);
            textView7.setText(formatter.format(marketcap));
            textView8.setText(formatter.format(marketvol));
            spinner.setVisibility(View.GONE);
            setcolor();
            setcolormax();


        }
    }

    void setcolor()
    {
        System.out.println("The Minimum price is "+min);
        System.out.println("The flag is "+flag);

        switch (flag)
        {
            case 1:
                textView1.setTextColor(Color.GREEN);
                break;

            case 2:
                textView3.setTextColor(Color.GREEN);
                break;

            case 3:
                textView5.setTextColor(Color.GREEN);
                break;

            case 4:
                textView9.setTextColor(Color.GREEN);
                break;
            default:
                break;

        }
    }
    void setcolormax()
    {
        System.out.println("The Minimum price is "+max);
        System.out.println("The flag is "+flag1);

        switch (flag1)
        {
            case 1:
                textView2.setTextColor(Color.GREEN);
                break;

            case 2:
                textView4.setTextColor(Color.GREEN);
                break;

            case 3:
                textView6.setTextColor(Color.GREEN);
                break;

            case 4:
                textView10.setTextColor(Color.GREEN);
                break;
            default:
                break;

        }
    }

}


