package com.indianbitcoiner.coinpryc;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import static android.R.attr.label;
import static android.R.attr.name;


public class FourthActivity extends AppCompatActivity {
    public ProgressBar spinner;
    public TableLayout t1;
    String name = null;
    String symbol = null;
    String rank = null;
    String price = null;
    String change = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        System.out.println("Spinner is " + spinner);


        t1 = (TableLayout) findViewById(R.id.altcointable);

        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.floatingButton);
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianbitcoiner.com/appbuyglobal"));
                startActivity(intent);
            }
        });


        addHeader();
        new AltcoinRetrieve().execute();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.cpmenu:
                Intent activitystart = new Intent(FourthActivity.this,SecondActivity.class);
                FourthActivity.this.startActivity(activitystart);
                return true;

            case R.id.usdmenu:
                // User chose the "Settings" item, show the app settings UI...
                Intent activity = new Intent(FourthActivity.this, ThirdActivity.class);
                FourthActivity.this.startActivity(activity);
                return true;

            case R.id.inrmenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity1 = new Intent(FourthActivity.this, SecondActivity.class);
                FourthActivity.this.startActivity(activity1);
                return true;


            case R.id.portfoliomenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity3 = new Intent(FourthActivity.this, FifthActivity.class);
                FourthActivity.this.startActivity(activity3);
                return true;
            case R.id.altcoinmenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity4 = new Intent(FourthActivity.this, FourthActivity.class);
                FourthActivity.this.startActivity(activity4);
                return true;
            case R.id.investment:
                Intent activity5 = new Intent(FourthActivity.this,SixthActivity.class);
                FourthActivity.this.startActivity(activity5);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.newsmenu:
                Intent activity6 = new Intent(FourthActivity.this,NewsActivity.class);
                FourthActivity.this.startActivity(activity6);
                return true;
            case R.id.about:
                Intent activity7 = new Intent(FourthActivity.this,AboutActivity.class);
                FourthActivity.this.startActivity(activity7);
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

        protected void onPreExecute() {


            spinner.setVisibility(View.VISIBLE);
            // textView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=50");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_LONG).show();
                return null;


            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {

                JSONArray jsonArray = (JSONArray) new JSONTokener(response).nextValue();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    // Users user = new Users();
                    name = json_data.getString("name");
                    symbol = json_data.getString("symbol");
                    rank = json_data.getString("rank");
                    price = json_data.getString("price_usd");
                    change = json_data.getString("percent_change_24h");

                    System.out.println(name + symbol + rank + price);
                    addData();
                    System.out.println("Data Added");
                    //user.setUsername(json_data.getString("username"));
                    //user.setEmail(json_data.getString("email"));
                    //users.add(user);
                }

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);


        }
    }

    void addHeader() {
        /** Create a TableRow dynamically **/
        TableRow tr = new TableRow(this);
        spinner.setVisibility(View.VISIBLE);


        /** Creating a TextView to add to the row **/
        TextView label = new TextView(this);
       // label.setFontFeatureSettings(); ="sans-serif-condensed";

        label.setTextSize(22);
        label.setText("Name");
        label.setTypeface(Typeface.create("SANS-SERIF-CONDENSED",Typeface.BOLD));
        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(5, 5, 5, 5);
        label.setBackgroundColor(Color.CYAN);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label, params);
        tr.addView(Ll); // Adding textView to tablerow.


        //TableRow tr1=new TableRow(this);
        /** Creating Qty Button **/
        TextView uname = new TextView(this);
        uname.setText("Symbol");
        uname.setTypeface(Typeface.create("SANS-SERIF-CONDENSED",Typeface.BOLD));
        uname.setTextSize(22);
        uname.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        uname.setPadding(5, 5, 5, 5);
        uname.setBackgroundColor(Color.CYAN);
        LinearLayout L2 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L2.addView(uname, params);
        tr.addView(L2); // Adding textview to tablerow.


        //TableRow tr2=new TableRow(this);
        /** Creating Qty Button **/
        TextView rank1 = new TextView(this);
        rank1.setText("Rank");
        rank1.setTypeface(Typeface.create("SANS-SERIF-CONDENSED",Typeface.BOLD));
        rank1.setTextSize(22);
        rank1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        rank1.setPadding(5, 5, 5, 5);
        rank1.setBackgroundColor(Color.CYAN);
        LinearLayout L3 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L3.addView(rank1, params);
        tr.addView(L3);

        //TableRow tr3=new TableRow(this);
        /** Creating Qty Button **/
        TextView uname1 = new TextView(this);
        uname1.setTextSize(22);
        uname1.setText("Price");
        uname1.setTypeface(Typeface.create("SANS-SERIF-CONDENSED",Typeface.BOLD));
        uname1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        uname1.setPadding(5, 5, 5, 5);
        uname1.setBackgroundColor(Color.CYAN);
        LinearLayout L4 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L4.addView(uname1, params);
        tr.addView(L4);

        // Add the TableRow to the TableLayout
        t1.addView(tr);


    }

    void addData() {
        /** Create a TableRow dynamically **/
        TableRow tr1 = new TableRow(this);
        spinner.setVisibility(View.VISIBLE);

        /** Creating a TextView to add to the row **/
        TextView label = new TextView(this);
        label.setText(name);

        if (Double.parseDouble(change)<=0)
        {
            label.setTextColor(Color.RED);
        }
        else {
            label.setTextColor(Color.GREEN);

        }

        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(5, 5, 5, 5);
        label.setBackgroundColor(Color.WHITE);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label, params);
        tr1.addView(Ll); // Adding textView to tablerow.


        //TableRow tr1=new TableRow(this);
        /** Creating Qty Button **/
        TextView uname = new TextView(this);
        uname.setText(symbol);
        uname.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        uname.setPadding(5, 5, 5, 5);
        uname.setBackgroundColor(Color.WHITE);
        LinearLayout L2 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L2.addView(uname, params);
        tr1.addView(L2);
        // Adding textview to tablerow.

        //TableRow tr2=new TableRow(this);
        /** Creating Qty Button **/
        TextView rank1 = new TextView(this);
        rank1.setText(rank);
        rank1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        rank1.setPadding(5, 5, 5, 5);
        rank1.setBackgroundColor(Color.WHITE);
        LinearLayout L3 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L3.addView(rank1, params);
        tr1.addView(L3);


        //TableRow tr3=new TableRow(this);
        /** Creating Qty Button **/
        TextView uname1 = new TextView(this);
        uname1.setText(price);
        uname1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        uname1.setPadding(5, 5, 5, 5);
        uname1.setBackgroundColor(Color.WHITE);
        LinearLayout L4 = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        L4.addView(uname1, params);
        tr1.addView(L4);
        // Add the TableRow to the TableLayout
        t1.addView(tr1);

        spinner.setVisibility(View.GONE);


    }
}