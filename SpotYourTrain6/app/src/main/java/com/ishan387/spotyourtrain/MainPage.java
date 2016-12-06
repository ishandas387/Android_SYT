package com.ishan387.spotyourtrain;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.ishan387.models.Running;
import com.ishan387.models.auto.Autocomplete;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainPage extends AppCompatActivity {


    String tno = null, d;

    SharedPreferences settings;
    EditText etno;
    Spinner spinfav;
    ListView fl;
    DatabaseHandler db;
    AutoCompleteTextView atv;
    Toolbar toolbar;
    ProgressBar barProgress;
    Button r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        barProgress = (ProgressBar) findViewById(R.id.progressLmain);
        barProgress.setVisibility(View.GONE);

        r = (Button) findViewById(R.id.route);



        Button npB = (Button) findViewById(R.id.npButton);
        //spinfav = (Spinner) findViewById(R.id.spinnerfav);
        fl = (ListView) findViewById(R.id.favlist);
        fl.setEmptyView(findViewById(R.id.empty));


        //fl.setBackgroundColor(Color.DKGRAY);

        atv = (AutoCompleteTextView) findViewById(R.id.autotv);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (atv.getText().toString().equals(""))

                {
                    Toast.makeText(getApplicationContext(), "Please enter a Train name or number", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(isNetworkAvailable())
                    {
                        Intent it = new Intent(MainPage.this, RouteResult.class);
                        it.putExtra("TRNROUTE", atv.getText().toString());
                        String label = atv.getText().toString();


                        if (label.trim().length() > 0) {
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            boolean t = db.insertLabel(label);
                            if (t) {

                                loadSpinnerData();
                            } else {

                            }

                        }
                        startActivity(it);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Seems like you are not connected to internet!", Toast.LENGTH_SHORT).show();

                    }






                }




            }
        });


        //spinfav.setOnItemSelectedListener(this);

        // Loading spinner data from database

        loadSpinnerData();


        fl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String tn = ((TextView) view).getText().toString();
                atv.setText(tn);
            }
        });

        // ListView recentview = (ListView) findViewById(R.id.listView2);

       /* if (checkDataBase())
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            recentview.setAdapter(adapter);

            recentview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String item = ((TextView) view).getText().toString();
                    etno.setText(item);

                    //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                }
            });
        }*/
        // else
        // {
          /* DatabaseHandler d = new DatabaseHandler(this);
        ArrayAdapter<Favorites> mArrayAdapter = new ArrayAdapter<Favorites>(this, android.R.layout.simple_list_item_1, android.R.id.text1, d.getAllContacts());
        recentview.setAdapter(mArrayAdapter);*/


        //s}


       /* ActionBar ab = getActionBar();
        ab.setIcon(R.drawable.train);*/

        //etno = (EditText) findViewById(R.id.trainNumber);


       /* settings = getSharedPreferences("MyPref", MODE_PRIVATE);
        if (settings.contains("TrainNum")) {
            // etno.setText(settings.getString("TrainNum", ""));
            atv.setText(settings.getString("TrainNum", ""));
        }*/
        atv.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                String tname = "http://api.railwayapi.com/suggest_train/trains/" + newText + "/apikey/thnxp9240/";
                if(isNetworkAvailable() ){
                    if(!newText.equals("")) {
                        barProgress.setVisibility(View.VISIBLE);
                        new gettrainname().execute(tname);
                    }
                }

            }

        });


        AdView mAdView = (AdView) findViewById(R.id.adView2);
        // AdRequest a = new AdRequest.
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /*AdView mAdView1 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);*/


        npB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                npClicked(v);
            }
        });
        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();

        int day1, day2, day3, month1, month2, year;

        year = c.get(Calendar.YEAR);
        month2 = c.get(Calendar.MONTH) + 1;
        if (month2 == 1) {
            month1 = 12;
        } else {
            month1 = month2 - 1;
        }

        day3 = c.get(Calendar.DAY_OF_MONTH);
        //day3 =1;
        switch (day3) {
            case 1:
                c1.set(year, month1 - 1, 1);
                day2 = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                day1 = day2 - 1;
                break;
            case 2:
                day2 = day3 - 1;
                c1.set(year, month1 - 1, 1);
                day1 = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                break;

            default:
                day2 = day3 - 1;
                day1 = day2 - 1;

        }


        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add(Integer.toString(day3));
        list.add(Integer.toString(day2));
        list.add(Integer.toString(day1));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner2.setAdapter(dataAdapter);


        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        List<String> list1 = new ArrayList<String>();
        list1.add(Integer.toString(month2));
        list1.add(Integer.toString(month1));


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner1.setAdapter(dataAdapter1);


        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list2 = new ArrayList<String>();
        list2.add(Integer.toString(year));
        list2.add(Integer.toString(year-1));

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner3.setAdapter(dataAdapter2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
               /* if (id == R.id.Fav) {

           /* Intent it = new Intent(MainPage.this, FavAct.class);

            /*i.putExtra("TRAIN_NUMBER", tno);
            i.putExtra("DATE", d);

                    //startActivity(it)
                    Toast.makeText(getApplicationContext(), "Will be added in next update!", Toast.LENGTH_SHORT).show();
                    return true;


                } else*/
        if (id == R.id.pnr) {
            Intent ipnr = new Intent(MainPage.this, PnrActivity.class);
            startActivity(ipnr);
        } else if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }
        else if (id == R.id.tbs) {
            Intent tbs = new Intent(MainPage.this, TbsMainPage.class);
            startActivity(tbs);
        }
        else if (id == R.id.clear) {
                atv.setText("");
        }
        else if (id == R.id.seatav) {
            Intent stav = new Intent(MainPage.this, SeatAvailMainPage.class);
            startActivity(stav);
        }
       /* else if (id == R.id.twithintime) {
            Intent stav = new Intent(MainPage.this, WithinHourResult.class);
            startActivity(stav);
        }*/
        else if (id == R.id.retrieve) {
            Intent stav = new Intent(MainPage.this, OfflineRouteList.class);
            startActivity(stav);
        }
        return super.onOptionsItemSelected(item);
    }

    public void npClicked(View v) {

        //etno = (EditText) findViewById(R.id.trainNumber);
        atv = (AutoCompleteTextView) findViewById(R.id.autotv);
        Spinner s1 = (Spinner) findViewById(R.id.spinner2);
        Spinner s2 = (Spinner) findViewById(R.id.spinner);
        Spinner s3 = (Spinner) findViewById(R.id.spinner3);

        d = s1.getSelectedItem().toString() + "-" + s2.getSelectedItem().toString() + "-" + s3.getSelectedItem().toString();
        if (atv.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a Train Number", Toast.LENGTH_SHORT).show();
        } else {


            if (isNetworkAvailable()) {


               /* tno = atv.getText().toString();
                settings = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("TrainNum", tno);

                // Commit the edits!
                editor.commit();*/


                // Toast.makeText(getApplicationContext(), "Getting data", Toast.LENGTH_SHORT).show();

                //Intent i = new Intent(MainPage.this, Result.class);
                Intent i = new Intent(MainPage.this, HttpAct.class);
                tno = atv.getText().toString();
                i.putExtra("TRAIN_NUMBER", tno);
                i.putExtra("DATE", d);
                String label= atv.getText().toString();

                if (label.trim().length() > 0) {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    boolean t = db.insertLabel(label);
                    if (t) {

                        loadSpinnerData();
                    } else {

                    }

                }

                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Seems like you are not connected to internet!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

   /* private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getBaseContext().getDatabasePath(DatabaseHandler.dbName).toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            return false;
        }
        return checkDB != null;
    }*/

    private void loadSpinnerData() {
        db = new DatabaseHandler(getApplicationContext());

        List<String> labels = db.getAllLabels();

        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        // ArrayAdapter<String> da = new ArrayAdapter<String>(this,R.layout.listfavlayout, R.id.value, labels);
        ArrayAdapter<String> da = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, labels);
        //da.setDropDownViewResource(android.R.layout.);
        //String[] s = {"t","ta","tat"};
        //ArrayAdapter<String> tva = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, labels);

        //atv.setAdapter(tva);


        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinfav.setAdapter(dataAdapter);
        fl.setAdapter(da);
        registerForContextMenu(fl);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.favlist) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle("Options");

            menu.add(0, 1, Menu.NONE, "Delete");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemId = item.getItemId();
        if (itemId == 1) {
            AdapterView.AdapterContextMenuInfo menuinfo;
            menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index_id =
                    (int) menuinfo.position;
            deleteUserData(fl.getItemAtPosition(index_id).toString());
        }

        return super.onContextItemSelected(item);
    }

    private void deleteUserData(String indexid) {
        // TODO Auto-generated method stub

        //String user_id = items_id.get(indexid);
        //Toast.makeText(getApplicationContext(), "id " + user_id, 1).show();
        //db = db.getWritableDatabase()
        db = new DatabaseHandler(getApplicationContext());
        Contact c = new Contact(indexid);
        db.delete(c);
        //finish();
        loadSpinnerData();
        Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
    }


    class gettrainname extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... key) {
            String newText = key[0];
            newText = newText.trim();
            //newText = newText.replace(" ", "+");

            try {
                return downloadUrl(key[0]);
            } catch (IOException e) {


                return "Unable to retrieve data. We are working on it..";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //String testresult= "{ \"route\": [ { \"no\": 1, \"station\": \"Howrah Jn (HWH)\", \"status\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"actdep\": \"03:50 PM\", \"schdep\": \"03:50 PM\" }, { \"no\": 2, \"station\": \"Barddhaman Jn (BWN)\", \"status\": \"0 hour 6 min before\", \"actarr\": \"04:58 PM\", \"scharr\": \"05:04 PM\", \"actdep\": \"05:06 PM\", \"schdep\": \"05:06 PM\" }, { \"no\": 3, \"station\": \"Bolpur S Niktn (BHP)\", \"status\": \"0 hour 2 min late\", \"actarr\": \"05:55 PM\", \"scharr\": \"05:53 PM\", \"actdep\": \"06:00 PM\", \"schdep\": \"05:58 PM\" }, { \"no\": 4, \"station\": \"Rampur Hat (RPH)\", \"status\": \"0 hour 1 min late\", \"actarr\": \"06:58 PM\", \"scharr\": \"06:57 PM\", \"actdep\": \"06:59 PM\", \"schdep\": \"06:59 PM\" }, { \"no\": 5, \"station\": \"New Farakka Jn (NFK)\", \"status\": \"0 hour 14 min before\", \"actarr\": \"08:36 PM\", \"scharr\": \"08:50 PM\", \"actdep\": \"08:52 PM\", \"schdep\": \"08:52 PM\" }, { \"no\": 6, \"station\": \"Malda Town (MLDT)\", \"status\": \"No Delay\", \"actarr\": \"09:30 PM\", \"scharr\": \"09:30 PM\", \"actdep\": \"09:45 PM\", \"schdep\": \"09:45 PM\" }, { \"no\": 7, \"station\": \"Kishanganj (KNE)\", \"status\": \"23 hour 10 min before\", \"actarr\": \"12:28 AM\", \"scharr\": \"11:38 PM\", \"actdep\": \"12:30 AM\", \"schdep\": \"11:40 PM\" }, { \"no\": 8, \"station\": \"New Jalpaiguri (NJP)\", \"status\": \"0 hour 3 min late\", \"actarr\": \"01:48 AM\", \"scharr\": \"01:45 AM\", \"actdep\": \"02:05 AM\", \"schdep\": \"02:05 AM\" }, { \"no\": 9, \"station\": \"New Cooch Behar (NCB)\", \"status\": \"No Delay\", \"actarr\": \"04:15 AM\", \"scharr\": \"04:15 AM\", \"actdep\": \"04:17 AM\", \"schdep\": \"04:17 AM\" }, { \"no\": 10, \"station\": \"New Alipurduar (NOQ)\", \"status\": \"No Delay\", \"actarr\": \"04:35 AM\", \"scharr\": \"04:35 AM\", \"actdep\": \"04:37 AM\", \"schdep\": \"04:37 AM\" }, { \"no\": 11, \"station\": \"New Bongaigaon (NBQ)\", \"status\": \"0 hour 2 min before\", \"actarr\": \"06:23 AM\", \"scharr\": \"06:25 AM\", \"actdep\": \"06:27 AM\", \"schdep\": \"06:27 AM\" }, { \"no\": 12, \"station\": \"Kamakhya (KYQ)\", \"status\": \"0 hour 23 min before\", \"actarr\": \"08:48 AM\", \"scharr\": \"09:11 AM\", \"actdep\": \"09:13 AM\", \"schdep\": \"09:13 AM\" }, { \"no\": 13, \"station\": \"Guwahati (GHY)\", \"status\": \"No Delay\", \"actarr\": \"09:30 AM\", \"scharr\": \"09:30 AM\", \"actdep\": \"Destination\", \"schdep\": \"Destination\" } ], \"total\": 13, \"response_code\": 200, \"train_number\": \"12345\", \"position\": \" Saraighat Express is Destination Reached and there is No Delay when it arrived at Guwahati (GHY) at 09:30 AM (On Time)\" }";
            Gson gson = new Gson();
            Autocomplete a = gson.fromJson(result, Autocomplete.class);

            // if (rs != null) {
            //System.out.println("City : " + currentWeather.getName());


            // }
            //t.setText(rs.getPosition().toString());
            //dialog.hide();
            //t= (TextView) findViewById(R.id.statustext);
            //t.setText(rs.getPosition().toString());

                if(a!=null) {
                    if (a.getTotal() == 0) {
                        //Toast.makeText(getApplicationContext(), "Seems like we don't have info on this train..", Toast.LENGTH_SHORT).show();

                    } else {
                        final List<String> l = a.getTrain();

                        runOnUiThread(new Runnable() {
                            public void run() {
                                ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, l);
                                atv.setAdapter(adaptr);

                                barProgress.setVisibility(View.GONE);
                                adaptr.notifyDataSetChanged();
                            }

                        });

                    }
                }
        }



    }

           /* runOnUiThread(new Runnable(){
                public void run(){
                    aAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.item,suggest);
                    atv.setAdapter(aAdapter);
                    aAdapter.notifyDataSetChanged();
                }
            });

            return null;*/


    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 2326;


        StringBuilder response = new StringBuilder();

        URL url = new URL(myurl);
        HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
            String strLine = null;
            while ((strLine = input.readLine()) != null) {
                response.append(strLine);
            }
            input.close();
        }
        return response.toString();

    }

}