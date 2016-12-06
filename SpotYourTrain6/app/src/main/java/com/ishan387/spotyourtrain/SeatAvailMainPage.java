package com.ishan387.spotyourtrain;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.auto.Autocomplete;
import com.ishan387.models.stationname.Station;
import com.ishan387.models.stationname.StationName;
import com.ishan387.models.trnbtw.Train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SeatAvailMainPage extends AppCompatActivity {
    AutoCompleteTextView sattn,saatvfrom,saatvto;
    ProgressBar barProgress,b1,b2;
    DatePicker  sadp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_avail_main_page);
        Button searchseat = (Button) findViewById(R.id.sasearch);
        sadp = (DatePicker) findViewById(R.id.sadatePicker);
         sattn= (AutoCompleteTextView) findViewById(R.id.satrainnumber);
        barProgress = (ProgressBar) findViewById(R.id.sapbl);
        b1 = (ProgressBar) findViewById(R.id.bp1);
        b2 = (ProgressBar) findViewById(R.id.bp2);

        saatvfrom = (AutoCompleteTextView) findViewById(R.id.sastationfrom);
        saatvto = (AutoCompleteTextView) findViewById(R.id.sastationto);
        sattn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = charSequence.toString();
                String tname = "http://api.railwayapi.com/suggest_train/trains/" + newText + "/apikey/thnxp9240/";
                if(isNetworkAvailable() ){
                    if(!newText.equals("")) {
                        barProgress.setVisibility(View.VISIBLE);
                        new gettrainname().execute(tname);
                    }
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saatvfrom.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                String tname = "http://api.railwayapi.com/suggest_station/name/" + newText + "/apikey/thnxp9240/";
                if (isNetworkAvailable()) {
                    if (!newText.equals("")) {
                        b1.setVisibility(View.VISIBLE);
                        new getstnname().execute(tname);
                    }

                }

            }

        });

        saatvto.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                String tname = "http://api.railwayapi.com/suggest_station/name/" + newText + "/apikey/thnxp9240/";
                if (isNetworkAvailable()) {
                    if (!newText.equals("")) {
                       b2.setVisibility(View.VISIBLE);
                        new getstnnameto().execute(tname);
                    }
                }

            }

        });



        final Spinner spinnerclass = (Spinner) findViewById(R.id.saclass);
        List<String> list = new ArrayList<String>();
        list.add("1A");
        list.add("2A"); list.add("3A"); list.add("SL"); list.add("FC"); list.add("CC");
        list.add("2S");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinnerclass.setAdapter(dataAdapter);


        final Spinner spinnerquota = (Spinner) findViewById(R.id.saquota);
        List<String> list1 = new ArrayList<String>();
        list1.add("GN");
        list1.add("LD"); list1.add("DF"); list1.add("HO"); list1.add("PH"); list1.add("FT");
        list1.add("DP");
        list1.add("CK");
        list1.add("PT");
        list1.add("SS");
        list1.add("HP");
        list1.add("RE");

        list1.add("OS");
        list1.add("PQ");
        list1.add("RC");
        list1.add("RS");
        list1.add("YU");
        list1.add("LB");
        list1.add("GNRS");



        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinnerquota.setAdapter(dataAdapter1);

        searchseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sattn.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter a train number", Toast.LENGTH_SHORT).show();

                }
                else if (saatvto.getText().toString().equals("") ||saatvfrom.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter Station details", Toast.LENGTH_SHORT).show();


                }

                else {


                    Intent it = new Intent(SeatAvailMainPage.this, SeatAvailResult.class);
                    it.putExtra("TRNNUM", sattn.getText().toString());
                    it.putExtra("FRM", saatvfrom.getText().toString());
                    it.putExtra("TT", saatvto.getText().toString());
                    it.putExtra("DTDAY", Integer.toString(sadp.getDayOfMonth()));
                    it.putExtra("DTMO", Integer.toString(sadp.getMonth()));
                    it.putExtra("DTY", Integer.toString(sadp.getYear()));
                    it.putExtra("CLS", spinnerclass.getSelectedItem().toString());
                    it.putExtra("QT", spinnerquota.getSelectedItem().toString());

                    startActivity(it);
                }
            }
        });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seat_avail_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                            sattn.setAdapter(adaptr);

                           // barProgress.setVisibility(View.GONE);
                            adaptr.notifyDataSetChanged();
                            barProgress.setVisibility(View.GONE);
                        }

                    });

                }
            }
        }



    }

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


    class getstnname extends AsyncTask<String, String, String> {

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
            StationName st = gson.fromJson(result, StationName.class);

            // if (rs != null) {
            //System.out.println("City : " + currentWeather.getName());


            // }
            //t.setText(rs.getPosition().toString());
            //dialog.hide();
            //t= (TextView) findViewById(R.id.statustext);
            //t.setText(rs.getPosition().toString());

            if(st!=null) {
                if (st.getTotal() == 0) {
                    //Toast.makeText(getApplicationContext(), "Seems like we don't have info on this train..", Toast.LENGTH_SHORT).show();

                } else {
                    final List<Station> l = st.getStation();
                    final List<String> lt = new ArrayList<>();
                    for(int i =0; i<st.getTotal() ; i++){
                        lt.add(l.get(i).getFullname()+" -"+l.get(i).getCode());
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lt);
                            saatvfrom.setAdapter(adaptr);
                            adaptr.notifyDataSetChanged();
                            b1.setVisibility(View.GONE);
                        }

                    });

                }
            }
        }



    }


    class getstnnameto extends AsyncTask<String, String, String> {

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
            StationName st = gson.fromJson(result, StationName.class);

            // if (rs != null) {
            //System.out.println("City : " + currentWeather.getName());


            // }
            //t.setText(rs.getPosition().toString());
            //dialog.hide();
            //t= (TextView) findViewById(R.id.statustext);
            //t.setText(rs.getPosition().toString());

            if(st!=null) {
                if (st.getTotal() == 0) {
                    //Toast.makeText(getApplicationContext(), "Seems like we don't have info on this train..", Toast.LENGTH_SHORT).show();

                } else {
                    final List<Station> l = st.getStation();
                    final List<String> lt = new ArrayList<>();
                    for(int i =0; i<st.getTotal() ; i++){
                        lt.add(l.get(i).getFullname()+" -"+l.get(i).getCode());
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lt);
                            saatvto.setAdapter(adaptr);
                            adaptr.notifyDataSetChanged();
                            b2.setVisibility(View.GONE);
                        }

                    });

                }
            }
        }



    }






}


