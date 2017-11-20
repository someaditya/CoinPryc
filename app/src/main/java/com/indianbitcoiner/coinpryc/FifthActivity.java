package com.indianbitcoiner.coinpryc;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static java.security.AccessController.getContext;

public class FifthActivity extends AppCompatActivity {


    public TextView text1;
    public TextView text2;

    public TextView text3;
    public TextView text4;

    Double totalcoin = 0.00;
    Double totalcost = 0.00;
    Double price = 0.00;
    Double profit = 0.00;
    public Double finalprice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        text1 = (TextView) findViewById(R.id.textView10);
        text2 = (TextView) findViewById(R.id.textView10);

        text3 = (TextView) findViewById(R.id.textView10);
        text4 = (TextView) findViewById(R.id.textView10);

        text1.setText(" ");
        text2.setText(" ");
        text3.setText(" ");
        text4.setText(" ");


        System.out.println(" Current Price main is " + price);


        System.out.println(" Current Price main is ");

        final Button button = (Button) findViewById(R.id.button1);
        final TextView text = (TextView) findViewById(R.id.textView19);

        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity = new Intent(FifthActivity.this, FifthActivity.class);
                FifthActivity.this.startActivity(activity);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity = new Intent(FifthActivity.this, AddActivity.class);
                FifthActivity.this.startActivity(activity);
            }
        });
        final Button button1 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity = new Intent(FifthActivity.this, DeleteActivity.class);
                FifthActivity.this.startActivity(activity);
            }
        });

        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.floatingButton);
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianbitcoiner.com/appbuyindia"));
                startActivity(intent);
            }
        });

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
            totalcoin = c.getDouble(1) + totalcoin;
            buffer.append("Total Cost : " + c.getDouble(2) + "\n\n");
            totalcost = totalcost + c.getDouble(2);

        }
        // Displaying all records
        showMessage("Bitcoin Transaction History\n", buffer.toString());


        new FifthActivity.ZebpayRetrieve().execute();


        text1.setText(totalcoin.toString());
        text2.setText(totalcost.toString());



//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent alarmIntent = new Intent(FifthActivity.this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(  FifthActivity.this, 0, alarmIntent, 0);
//        Calendar alarmStartTime = Calendar.getInstance();
//
//        System.out.println("Inside Set Alarm");
//
//        alarmStartTime.set(Calendar.HOUR_OF_DAY, 16);
//        alarmStartTime.set(Calendar.MINUTE, 26);
//        alarmStartTime.set(Calendar.SECOND, 00);
//        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);


    }
    //  showMessage("Student Details", buffer.toString());


    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(Drawable "@d")

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cpmenu:
                Intent activitystart = new Intent(FifthActivity.this,SecondActivity.class);
                FifthActivity.this.startActivity(activitystart);
                return true;

            case R.id.usdmenu:
                // User chose the "Settings" item, show the app settings UI...
                Intent activity = new Intent(FifthActivity.this, ThirdActivity.class);
                FifthActivity.this.startActivity(activity);
                return true;

            case R.id.inrmenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity1 = new Intent(FifthActivity.this, SecondActivity.class);
                FifthActivity.this.startActivity(activity1);
                return true;


            case R.id.portfoliomenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity3 = new Intent(FifthActivity.this, FifthActivity.class);
                FifthActivity.this.startActivity(activity3);
                return true;
            case R.id.altcoinmenu:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent activity4 = new Intent(FifthActivity.this, FourthActivity.class);
                FifthActivity.this.startActivity(activity4);
                return true;
            case R.id.investment:
                Intent activity5 = new Intent(FifthActivity.this,SixthActivity.class);
                FifthActivity.this.startActivity(activity5);
                // User chose the "Favorite" action, mark the current item'
                // as a favorite...
                return true;
            case R.id.newsmenu:
                Intent activity6 = new Intent(FifthActivity.this,NewsActivity.class);
                FifthActivity.this.startActivity(activity6);
                return true;
            case R.id.about:
                Intent activity7 = new Intent(FifthActivity.this,AboutActivity.class);
                FifthActivity.this.startActivity(activity7);
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

    private class ZebpayRetrieve extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private Integer res = 0;

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {


            try {

                URL url = new URL("https://www.zebapi.com/api/v1/market/ticker/btc/inr");
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
                        System.out.println(stringBuilder);
                        Log.d("Zebpay ", stringBuilder.toString());

                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } else {

                    return null;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();

                return null;


            }
        }

        protected void onPostExecute(String response) {
            String zebpaybuy = null;

            if (response == null) {
                response = "THERE WAS AN ERROR";
                Toast.makeText(getApplicationContext(), "Can't fetch Market Price , Try Later", Toast.LENGTH_SHORT).show();

            } else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    zebpaybuy = object.getString("buy");

                    price = Double.parseDouble(zebpaybuy);
                    System.out.println(" Current Price is " + price);
                    finalprice = totalcoin * price;
                    profit = finalprice - totalcost;
                    System.out.println(" Current Price main is " + price);
                    System.out.println(" Total Price is " + finalprice);


                } catch (JSONException e) {
                    // Appropriate error handling code
                }
                Log.i("INFO", response);
                // textView1.setText(zebpaybuy);
                // textView2.setText(zebpaysell);

                text3.setText(finalprice.toString());
                if (profit > 0) {
                    text4.setTextColor(Color.GREEN);
                    text4.setText(profit.toString());

                } else

                {
                    text4.setTextColor(Color.RED);
                    text4.setText(profit.toString());
                }
                // setnotify();
            }

        }
    }


//    }
//    void setnotify()
//    {
//        final int NOTIFICATION_ID = 1;
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent mIntent = new Intent(this, FifthActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//
//        builder.setContentTitle("CoinPryc Bitcoin Portoflio");
//        // builder.setContentText("Don't need to worry any more.You will receive daily alerts on your portfolio.");
//        builder.setSmallIcon(R.drawable.logo_menu_color);
//        builder.setContentText("Your portfolio is at Rs."+finalprice);
//        //  builder.setSmallIcon(R.drawable.ic_launcher);
//        builder.setContentIntent(pendingIntent);
//
//        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID, builder.build());
//    }
//
//
//    private int getInterval(){
//
//        System.out.println("Inside Get Interval");
//
//        int days = 1;
//        int hours = 24;
//        int minutes = 60;
//        int seconds = 60;
//        int milliseconds = 1000;
//        int repeatMS = days * hours * minutes * seconds * milliseconds;
//        return repeatMS;
//    }
//
//
//
//    public static class AlarmReceiver extends BroadcastReceiver {
//
//        NotificationManager notificationManager;
//        Context context1;
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            System.out.println("Inside AlarmRec");
//
//            Intent service1 = new Intent(context, AlarmService.class);
//            context.startService(service1);
//        }
//    }
//    public class AlarmService extends IntentService{
//
//        private static final int NOTIFICATION_ID = 1;
//        private NotificationManager notificationManager;
//        private PendingIntent pendingIntent;
//        private String price = null;
//
//        public AlarmService()
//        {
//            super("AlarmService") ;
//        }
//
//
//
//        @Override
////       // public IBinder onBind(Intent arg0)
////        {
////            return null;
////        }
//
//        @SuppressWarnings("static-access")
////
////        NotificationCompat.Builder mBuilder =
////                new NotificationCompat.Builder(this
////                        .setContentTitle("My notification")
////                        .setContentText("Hello World!");
////
//     //   @Override
//        protected void onHandleIntent(Intent intent) {
//
//            System.out.println("Inside Set Alarm Service");
//
//
//            //super.onStart(intent, startId);
//            Context context = this.getApplicationContext();
//            notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
//            Intent mIntent = new Intent(this, FifthActivity.class);
//            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//
//            builder.setContentTitle("CoinPryc Bitcoin Portoflio");
//            builder.setContentText("Don't need to worry any more.You will receive daily alerts on your portfolio.");
//            builder.setSmallIcon(R.drawable.logo_menu_color);
//           // builder.setContentText("Your portfolio is at Rs."+finalprice);
//          //  builder.setSmallIcon(R.drawable.ic_launcher);
//            builder.setContentIntent(pendingIntent);
//
//            notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.notify(NOTIFICATION_ID, builder.build());
//        }
//    }


}



