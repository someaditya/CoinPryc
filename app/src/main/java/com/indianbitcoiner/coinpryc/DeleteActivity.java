package com.indianbitcoiner.coinpryc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private EditText subjectEditText;
    private TextView text ;
    public  int a =0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        subjectEditText = (EditText) findViewById(R.id.editText);

        text = (TextView) findViewById(R.id.textView19);

        button1 = (Button) findViewById(R.id.button3);
        //  final String name = subjectEditText.getText().toString();
        //final String desc = descEditText.getText().toString();
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SQLiteDatabase db;
                    db = openOrCreateDatabase("PORTFOLIO", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS bitdata(id INTEGER PRIMARY KEY AUTOINCREMENT,bitcoin DOUBLE,price DOUBLE);");
                  //  Double a = Double.parseDouble(subjectEditText.getText().toString());
                    //Double a = subjectEditText;
                   // System.out.println("a " + a);
                    //Double b = Double.parseDouble(descEditText.getText().toString());
                    //System.out.println("b " + b);
                    // Cursor c=db.rawQuery("SELECT * FROM bitdata", null);
                    a = Integer.parseInt(subjectEditText.getText().toString());
                    System.out.println("To Delete :"+a);
                    db.execSQL("DELETE FROM bitdata WHERE id ='"+a+"'");
                    Intent activity = new Intent(DeleteActivity.this, FifthActivity.class);
                    DeleteActivity.this.startActivity(activity);
                }
                catch (SQLiteException e)
                {
                    Log.e("SQL Error",e.getMessage(),e);
                }
            }
        });

        button2 = (Button) findViewById(R.id.button4);
        //  final String name = subjectEditText.getText().toString();
        //final String desc = descEditText.getText().toString();
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SQLiteDatabase db;
                    db = openOrCreateDatabase("PORTFOLIO", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS bitdata(id INTEGER PRIMARY KEY AUTOINCREMENT,bitcoin DOUBLE,price DOUBLE);");
                    //Double a = Double.parseDouble(.getText().toString());
                    //Double a = subjectEditText;
                    System.out.println("a " + a);
                    //Double b = Double.parseDouble(descEditText.getText().toString());
                    //System.out.println("b " + b);
                    // Cursor c=db.rawQuery("SELECT * FROM bitdata", null);
                    db.execSQL("DELETE FROM bitdata");
                    Intent activity = new Intent(DeleteActivity.this, FifthActivity.class);
                    DeleteActivity.this.startActivity(activity);
                }
                catch (SQLiteException e)
                {
                    Log.e("SQL Error",e.getMessage(),e);
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SQLiteDatabase db;
                    db = openOrCreateDatabase("PORTFOLIO", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS bitdata(id INTEGER PRIMARY KEY AUTOINCREMENT,bitcoin DOUBLE,price DOUBLE);");

                    Cursor c = db.rawQuery("SELECT * FROM bitdata", null);

                    // Checking if no records found
                    if (c.getCount() == 0) {
                        showMessage("Error", "No records found");
                        return;
                    }
                    // Appending records to a string buffer
                    StringBuffer buffer = new StringBuffer();
                    while (c.moveToNext()) {
                        buffer.append("Transaction Id: " + c.getString(0) + "\n");
                        buffer.append("Bitcoin Amount : " + c.getDouble(1) + "\n");
                      //  totalcoin = c.getDouble(1) + totalcoin;
                        buffer.append("Total Cost : " + c.getDouble(2) + "\n\n");
                       // totalcost = totalcost + c.getDouble(2);

                    }
                    // Displaying all records
                    showMessage("Bitcoin Transaction History\n", buffer.toString());
                } catch (SQLiteException e) {
                    Log.e("SQL Error", e.getMessage(), e);
                   // Toast.makeText(Context)
                }
            }});
        }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
