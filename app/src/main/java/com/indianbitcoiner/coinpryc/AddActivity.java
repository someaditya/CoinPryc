package com.indianbitcoiner.coinpryc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity  {



    private Button button;
    private EditText subjectEditText;
    private EditText descEditText;
    String name = null;
    List<String> list = new ArrayList<String>();
    ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add);

        subjectEditText = (EditText) findViewById(R.id.editText2);
        descEditText = (EditText) findViewById(R.id.editText3);

        spinner = (ProgressBar) findViewById(R.id.progressBar2);
        new AddActivity.AltcoinRetrieve().execute();

     //   Double a = (Double)findViewById(R.id.editText3);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.select_dialog_item,list);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.coinlist);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);


        button = (Button) findViewById(R.id.button);
      //  final String name = subjectEditText.getText().toString();
        //final String desc = descEditText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SQLiteDatabase db;
                    db = openOrCreateDatabase("PORTFOLIO", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS coinpryc(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),amount DOUBLE,price DOUBLE);");
                    Double a = Double.parseDouble(subjectEditText.getText().toString());
                    //Double a = subjectEditText;
                    System.out.println("a " + a);
                    Double b = Double.parseDouble(descEditText.getText().toString());
                    System.out.println("b " + b);
                    // Cursor c=db.rawQuery("SELECT * FROM bitdata", null);
                    db.execSQL("INSERT INTO coinpryc (name,amount,price) VALUES('" + a + "','" + b + "');");
                    Intent activity = new Intent(AddActivity.this, FifthActivity.class);
                    AddActivity.this.startActivity(activity);
                }
                catch (SQLiteException e)
                {
                    Log.e("SQL Error",e.getMessage(),e);
                }
            }
        });



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
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=600");

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
                   // symbol = json_data.getString("symbol");
                    //rank = json_data.getString("rank");
                    //price = json_data.getString("price_usd");
                    //change = json_data.getString("percent_change_24h");

                    System.out.println(name);
                    list.add(name);
                   // addData();
                    System.out.println("Data Added");
                    //user.setUsername(json_data.getString("username"));
                    //user.setEmail(json_data.getString("email"));
                    //users.add(user);
                }

            } catch (JSONException e) {
                // Appropriate error handling code
            }
            Log.i("INFO", response);
            spinner.setVisibility(View.GONE);


        }
    }


}
