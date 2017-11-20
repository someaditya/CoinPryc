package com.indianbitcoiner.coinpryc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
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

import static com.indianbitcoiner.coinpryc.R.id.text;

public class SixthActivity extends AppCompatActivity {

    private EditText amount;
    private Button button;
    int count =0;
    public String name,market,rank,price,change =null;

    public Double marketcap,pricecap,iamount,ichange=0.00;

    TextView text1;
    TextView text2;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(getApplicationContext(), "Connecting ", Toast.LENGTH_SHORT).show();
        amount = (EditText) findViewById(R.id.editText4);


        spinner = (ProgressBar)findViewById(R.id.progressBar);

        //iamount = amount;
        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iamount = Double.parseDouble(amount.getText().toString());
                new SixthActivity.AltcoinRetrieve().execute();
            }
        });

        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.floatingActionButton4);
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianbitcoiner.com/appbuyglobal"));
                startActivity(intent);
            }
        });

        Toast.makeText(getApplicationContext(), "Applying Algorithm", Toast.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cpmenu:
                Intent activitystart = new Intent(SixthActivity.this,SecondActivity.class);
                SixthActivity.this.startActivity(activitystart);
                return true;

            case R.id.usdmenu:
                // User chose the "Settings" item, show the app settings UI...
                Intent activity = new Intent(SixthActivity.this,ThirdActivity.class);
                SixthActivity.this.startActivity(activity);
                return true;

            case R.id.inrmenu:
                Intent activity3 = new Intent(SixthActivity.this,SecondActivity.class);
                SixthActivity.this.startActivity(activity3);
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            case R.id.altcoinmenu:
                Intent activity1 = new Intent(SixthActivity.this,FourthActivity.class);
                SixthActivity.this.startActivity(activity1);
                return true;
            case R.id.portfoliomenu:
                Intent activity2 = new Intent(SixthActivity.this,FifthActivity.class);
                SixthActivity.this.startActivity(activity2);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.investment:
                Intent activity4 = new Intent(SixthActivity.this,SixthActivity.class);
                SixthActivity.this.startActivity(activity4);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.newsmenu:
                Intent activity6 = new Intent(SixthActivity.this,NewsActivity.class);
                SixthActivity.this.startActivity(activity6);
                return true;
            case R.id.about:
                Intent activity7 = new Intent(SixthActivity.this,AboutActivity.class);
                SixthActivity.this.startActivity(activity7);
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

    private class AltcoinRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res;

        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);

          //  spinner.setVisibility(View.VISIBLE);
            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here



            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=40");

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

                        Log.d("Coinpryc ", stringBuilder.toString());

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
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_LONG).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            Double ret =0.00;
            Double coinint = 0.00;
            int j=0;
            int flag =0;
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            else {
                try {

                    JSONArray jsonArray = (JSONArray) new JSONTokener(response).nextValue();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        // Users user = new Users();
                        name = json_data.getString("name");
                        //symbol = json_data.getString("symbol");
                        rank = json_data.getString("rank");
                        market = json_data.getString("market_cap_usd");

                        price = json_data.getString("price_usd");
                        change = json_data.getString("percent_change_24h");

                        pricecap = Double.parseDouble(price);
                        ichange = Double.parseDouble(change);

                        marketcap = Double.parseDouble(market);
                        marketcap = marketcap / 1000000000;

                        System.out.println("  ");

                        System.out.println("Name " + name + " : " + rank + " " + marketcap + " " + pricecap + " " + change + "/n");

                        ret = investment(name, rank, marketcap, pricecap, ichange);


                        if (flag == 0) {

                            coinint = coinint + ret;
                            if (ret != 0.00) {


                                if (coinint <= iamount) {
                                    if (coinint < iamount) {
                                        count++;
                                        System.out.println("Investing " + ret + " in Coin " + name + " which is Coin no. " + count + " Total Investment is now " + coinint);
                                        addData(name, ret);

                                    } else {
                                        count++;
                                        System.out.println("Investing " + ret + " in Coin " + name + " which is Coin no. " + count + " Total Investment is now " + coinint);
                                        System.out.println("Investment Value Matched , Portfolio Ready " + coinint);
                                        addData(name, ret);
                                        flag = 1;
                                        //break;

                                    }

                                } else {

                                    //  count--;
                                    System.out.println("Investing " + ret + " in Coin " + name + " which is Coin no. " + count + " Total Investment is now " + coinint);
                                    coinint = coinint - ret;
                                    System.out.println("Investment Value exceeded , Reducing back to " + coinint);


                                }
                            }
                        } else {
                            break;
                        }


                        System.out.println("Investment Amount " + ret);

                        System.out.println("Total Investment Intended  " + iamount + "\n\n");


                    }

                } catch (JSONException e) {
                    // Appropriate error handling code
                }
                Log.i("INFO", response);
            }
            spinner.setVisibility(View.GONE);

        }


    }

    void  addData(String name,Double amount)
    {

        LinearLayout Ll = (LinearLayout) findViewById(R.id.linearlayoutinv);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);

        TextView label = new TextView(this);

        label.setText("You can invest Rs."+amount+" in "+name+ " ");
        if (amount==((iamount*0.4)/2))
        {
            label.setTextColor(Color.BLUE);
        }
        else if(amount==((iamount*0.4)/4))
        {
            label.setTextColor(Color.CYAN);
         }
         else
        {
            label.setTextColor(Color.MAGENTA);
        }
        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(5, 5, 5, 5);
        label.setBackgroundColor(Color.WHITE);

        params.setMargins(5, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label, params);


    }

    Double investment(String name,String rank,Double marketcap,Double price,Double ichange) {

        Double retam =0.00;
        Double bluechip =0.00;
        Double penny =0.00;
        Double mid =0.00;
        Double game =0.00;

        Integer i = Integer.parseInt(rank);

        System.out.println("Index  "+i);

        if (i<=15) {

            System.out.println("In Top Ten ");

            if (marketcap >= 25) {



                    System.out.println("Market Cap greater than 25 Billion");
                    System.out.println("Price is :"+price);
                    bluechip = iamount * 0.40;
                    bluechip = bluechip /2;
                    //count++;
                    retam =bluechip;

            }
            else
            {
                System.out.println("Price is :"+price);

                if (price <= 1)
                {

                    System.out.println("Price less than One Dollar - Penny Stock- But in Top 15");
                    penny = iamount * 0.40;
                    penny = penny/4;
                    //count++;
                    retam = penny;


                }

                else {

                    if(ichange>=5) {
                        System.out.println("Price greater than Dollar - Top 15 - Change more than 5%");
                        mid = iamount * 0.20;
                        mid = mid / 4;
                        //count++;
                        retam = mid;
                    }
                }
            }
        }
        else {

            System.out.println("Out of Top Ten ");

            if (price > 10) {

                if (ichange >=  5) {

                    System.out.println("Greater than 10$ Last 24hr Change greater than 5 %");
                    mid = iamount * 0.40;
                    mid = mid / 4;
                    //count++;
                    retam = mid;
                } else {


                    if (marketcap >= 0.15) {
                        System.out.println("Greater than 10$ 24hr Change less than 15% but market 150m");
                        penny = iamount * 0.20;
                        penny = penny / 8;
                        retam = penny;

                    }
                }

            } else {

                if (ichange < 5) {

                    System.out.println("Lesser than 10$ Last 24hr Change less than 5 %");
                    mid = iamount * 0.40;
                    mid = mid / 4;
                    //count++;
                    retam = mid;
                } else {



                    if (marketcap >= 0.15) {
                        System.out.println("Lesser than 10$ 24hr Change less than 15% but market 150m");
                        penny = iamount * 0.20;
                        penny = penny / 8;
                        retam = penny;

                    }

                }

            }
        }

        return retam;
    }
}
