package com.indianbitcoiner.coinpryc;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity {

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


    public Long forex = null;
    public ProgressBar spinner;

    public  Double min =0.00;
    public  Integer flag =0;
    public  Integer flag1 =0;
    public double max = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);



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


        spinner = (ProgressBar) findViewById(R.id.progressBar1);


        new ThirdActivity.ForexRetrieve().execute();
       // new ThirdActivity.CoinbaseRetrieve().execute();
        new ThirdActivity.BitfinexRetrieve().execute();
        new ThirdActivity.CexRetrieve().execute();
        new ThirdActivity.LakebtcRetrieve().execute();
        new ThirdActivity.BitstampRetrieve().execute();
        new ThirdActivity.CoinmarketRetrieve().execute();

        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianbitcoiner.com/appbuyglobal"));
                startActivity(intent);
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.cpmenu:
                Intent activitystart = new Intent(ThirdActivity.this,SecondActivity.class);
                ThirdActivity.this.startActivity(activitystart);
                return true;

            case R.id.usdmenu:
                // User chose the "Settings" item, show the app settings UI...
                Intent activity = new Intent(ThirdActivity.this,ThirdActivity.class);
                ThirdActivity.this.startActivity(activity);
                return true;

            case R.id.inrmenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity1 = new Intent(ThirdActivity.this,SecondActivity.class);
                ThirdActivity.this.startActivity(activity1);
                return true;
            case R.id.altcoinmenu:
                Intent activity2 = new Intent(ThirdActivity.this,FourthActivity.class);
                ThirdActivity.this.startActivity(activity2);
                return true;
            case R.id.portfoliomenu:
                Intent activity3 = new Intent(ThirdActivity.this,FifthActivity.class);
                ThirdActivity.this.startActivity(activity3);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.investment:
                Intent activity4 = new Intent(ThirdActivity.this,SixthActivity.class);
                ThirdActivity.this.startActivity(activity4);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.newsmenu:
                Intent activity6 = new Intent(ThirdActivity.this,NewsActivity.class);
                ThirdActivity.this.startActivity(activity6);
                return true;

            case R.id.about:
                Intent activity5 = new Intent(ThirdActivity.this,AboutActivity.class);
                ThirdActivity.this.startActivity(activity5);
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

    private class BitfinexRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

            textView1.setText("");
            textView2.setText("");
            spinner.setVisibility(View.VISIBLE);
            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://api.bitfinex.com/v2/ticker/tBTCUSD");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    System.out.println(stringBuilder);
                    Log.d("Bitfinex ", stringBuilder.toString());

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_LONG).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            double zebpaybuy = 0;
            double zebpaysell = 0;
            String bitf1 = null;
            String bitf2 = null;
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONArray array = (JSONArray)new JSONTokener(response).nextValue();
                zebpaybuy = array.getDouble(0);
                zebpaysell = array.getDouble(2);

                bitf1 = Double.toString(zebpaybuy);
                bitf2 = Double.toString(zebpaysell);
                System.out.println("Bid "+zebpaybuy+"ask"+zebpaysell);

            } catch (JSONException e) {
                // Appropriate error handling code
                Log.e("Erooor",e.getMessage(),e);
            }
            Log.i("INFO", response);
            textView1.setText(bitf1);
            textView2.setText(bitf2);
            min = Double.parseDouble(bitf1);
            System.out.println("The Minimum price is "+min);

            if (Double.parseDouble(bitf1)<=min)
            {
                flag =1;
            }

            max = Double.parseDouble(bitf2);
            System.out.println("The Minimum price is "+max);

            if (Double.parseDouble(bitf2)>=max)
            {
                flag1 =1;
            }

        }
    }

    private class CexRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

            textView3.setText("");
            textView4.setText("");
            spinner.setVisibility(View.VISIBLE);

            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://cex.io/api/ticker/BTC/USD");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            String zebpaybuy = null;
            String zebpaysell = null;
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                zebpaybuy = object.getString("bid");
                zebpaysell = object.getString("ask");

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);
            textView3.setText(zebpaybuy);
            textView4.setText(zebpaysell);
            if (Double.parseDouble(zebpaybuy)<=min)
            {
                min = Double.parseDouble(zebpaybuy);

                flag =2;
            }

            if (Double.parseDouble(zebpaysell)>=max)
            {
                max = Double.parseDouble(zebpaysell);
                flag1 =2;
            }

        }
    }


    private class ForexRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);


            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("http://api.fixer.io/latest?base=USD");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {

            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
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

    private class CoinmarketRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

            textView7.setText("");
            textView8.setText("");
            spinner.setVisibility(View.VISIBLE);

            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/global/");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    System.out.println(stringBuilder);
                    Log.d("CoinmarketCap", stringBuilder.toString());

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String response) {

            Long marketcap = null;
            Long marketvol = null;
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                marketcap = object.getLong("total_market_cap_usd");
                marketvol = object.getLong("total_24h_volume_usd");
                // marketcap = marketcap * forex;
                //marketvol = marketvol * forex;

                System.out.println(forex);
                // zebpaysell = object.getString("sell");

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
            //new DecimalFormat("##,##,##0").format(amount);
            textView7.setText(formatter.format(marketcap));
            textView8.setText(formatter.format(marketvol));
            spinner.setVisibility(View.GONE);
            setcolor();
            setcolormax();


        }
    }

    private class LakebtcRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

            textView5.setText("");
            textView6.setText("");
            spinner.setVisibility(View.VISIBLE);
            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://api.lakebtc.com/api_v2/ticker");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    System.out.println(stringBuilder);
                    Log.d("Coinsecure ", stringBuilder.toString());

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            long zebpaybuy = 0;
            long zebpaysell = 0;
            String coinsec1 = null;
            String coinsec2 = null;

            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject message = object.getJSONObject("btcusd");
                zebpaybuy = message.getLong("ask");
                zebpaysell = message.getLong("bid");
                System.out.println("ASk : " + zebpaybuy + "Bid:" + zebpaysell);

                // zebpaybuy=(zebpaybuy);
                //zebpaysell=zebpaysell;
                coinsec1 = Long.toString(zebpaybuy);
                coinsec2 = Long.toString(zebpaysell);

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);
            textView5.setText(coinsec1);
            textView6.setText(coinsec2);

            if (Double.parseDouble(coinsec1)<=min)
            {
                min = Double.parseDouble(coinsec1);
                flag =3;
            }
            if (Double.parseDouble(coinsec2)>=max)
            {
                max = Double.parseDouble(coinsec2);
                flag1 =3;
            }

        }
    }

    private class BitstampRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

            textView9.setText("");
            textView10.setText("");
            spinner.setVisibility(View.VISIBLE);
            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://www.bitstamp.net/api/ticker/");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    // System.out.println(stringBuilder);
                    Log.d("Bitstamp ", stringBuilder.toString());

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            long zebpaybuy = 0;
            long zebpaysell = 0;
            String coinsec1 = null;
            String coinsec2 = null;

            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
               // JSONObject message = object.getJSONObject("btcusd");
                zebpaybuy = object.getLong("ask");
                zebpaysell = object.getLong("open");
                System.out.println("Ask : " + zebpaybuy + "Open:" + zebpaysell);

                // zebpaybuy=(zebpaybuy);
                //zebpaysell=zebpaysell;
                coinsec1 = Long.toString(zebpaybuy);
                coinsec2 = Long.toString(zebpaysell);

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);
            textView9.setText(coinsec1);
            textView10.setText(coinsec2);
            spinner.setVisibility(View.GONE);
            if (Double.parseDouble(coinsec1)<=min)
            {
                min = Double.parseDouble(coinsec1);
                flag =4;
            }

            if (Double.parseDouble(coinsec2)>=max)
            {
                max = Double.parseDouble(coinsec2);
                flag1 =4;
            }


        }

    }

    void setcolor()
    {
        System.out.println("The Minimum price is "+min);
        System.out.println("The flag is "+flag);

        switch (flag)
        {
            case 1:
                System.out.println("This is it"+flag);
                textView1.setTextColor(Color.GREEN);
                break;

            case 2:
                System.out.println("This is it"+flag);
                textView3.setTextColor(Color.GREEN);
                break;

            case 3:
                System.out.println("This is it"+flag);
                textView5.setTextColor(Color.GREEN);
                break;

            case 4:
                System.out.println("This is it"+flag);
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
