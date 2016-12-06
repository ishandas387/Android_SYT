package com.ishan387.spotyourtrain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.ishan387.models.Route;
import com.ishan387.models.Running;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpAct extends Activity {

    TextView t ;
    ScrollView s;
    boolean flag;
    String[] tnum;
    //String u = "http://api.railwayapi.com/live/train/";
    String u ="http://api.railwayapi.com/live/train/";
    static final String apikey ="thnxp9240";
    static  final String zero ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

       // t = (TextView) findViewById(R.id.statustext);
        //s = (ScrollView) findViewById(R.id.scrollView);
        AdView mAdView1 = (AdView) findViewById(R.id.adView);
        // AdRequest a = new AdRequest.
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest);

        String newString,d;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                d = null;
            } else {
                newString= extras.getString("TRAIN_NUMBER");
                d = extras.getString("DATE");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("TRAIN_NUMBER");
            d= (String) savedInstanceState.getSerializable("DATE");
        }

        if(newString.charAt(0)>='A' && newString.charAt(0)<='Z') {
            flag = false;
             tnum = newString.split("\\(");
          //  tnum[1] = tnum[1].replace("(","");
            tnum[1] = tnum[1].replace(")", "");


        }
        else {
            tnum = newString.split(" ");
            flag = true;
        }

        String [] date = d.split("-");
        if(date[0].length()==1){
            date[0] = zero+date[0];
        }
        if (date[1].length()==1){
            date[1] = zero+date[1];
        }

        if (flag)
        {
            u = u + tnum[0]+"/doj/"+date[2]+date[1]+date[0]+"/apikey/"+apikey+"/";

        }
        else
        {
            u = u + tnum[1]+"/doj/"+date[2]+date[1]+date[0]+"/apikey/"+apikey+"/";

        }




        new DownloadWebpageTask().execute(u);
        /*String testresult= "{ \"route\": [ { \"no\": 1, \"station\": \"Howrah Jn (HWH)\", \"status\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"actdep\": \"03:50 PM\", \"schdep\": \"03:50 PM\" }, { \"no\": 2, \"station\": \"Barddhaman Jn (BWN)\", \"status\": \"0 hour 6 min before\", \"actarr\": \"04:58 PM\", \"scharr\": \"05:04 PM\", \"actdep\": \"05:06 PM\", \"schdep\": \"05:06 PM\" }, { \"no\": 3, \"station\": \"Bolpur S Niktn (BHP)\", \"status\": \"0 hour 2 min late\", \"actarr\": \"05:55 PM\", \"scharr\": \"05:53 PM\", \"actdep\": \"06:00 PM\", \"schdep\": \"05:58 PM\" }, { \"no\": 4, \"station\": \"Rampur Hat (RPH)\", \"status\": \"0 hour 1 min late\", \"actarr\": \"06:58 PM\", \"scharr\": \"06:57 PM\", \"actdep\": \"06:59 PM\", \"schdep\": \"06:59 PM\" }, { \"no\": 5, \"station\": \"New Farakka Jn (NFK)\", \"status\": \"0 hour 14 min before\", \"actarr\": \"08:36 PM\", \"scharr\": \"08:50 PM\", \"actdep\": \"08:52 PM\", \"schdep\": \"08:52 PM\" }, { \"no\": 6, \"station\": \"Malda Town (MLDT)\", \"status\": \"No Delay\", \"actarr\": \"09:30 PM\", \"scharr\": \"09:30 PM\", \"actdep\": \"09:45 PM\", \"schdep\": \"09:45 PM\" }, { \"no\": 7, \"station\": \"Kishanganj (KNE)\", \"status\": \"23 hour 10 min before\", \"actarr\": \"12:28 AM\", \"scharr\": \"11:38 PM\", \"actdep\": \"12:30 AM\", \"schdep\": \"11:40 PM\" }, { \"no\": 8, \"station\": \"New Jalpaiguri (NJP)\", \"status\": \"0 hour 3 min late\", \"actarr\": \"01:48 AM\", \"scharr\": \"01:45 AM\", \"actdep\": \"02:05 AM\", \"schdep\": \"02:05 AM\" }, { \"no\": 9, \"station\": \"New Cooch Behar (NCB)\", \"status\": \"No Delay\", \"actarr\": \"04:15 AM\", \"scharr\": \"04:15 AM\", \"actdep\": \"04:17 AM\", \"schdep\": \"04:17 AM\" }, { \"no\": 10, \"station\": \"New Alipurduar (NOQ)\", \"status\": \"No Delay\", \"actarr\": \"04:35 AM\", \"scharr\": \"04:35 AM\", \"actdep\": \"04:37 AM\", \"schdep\": \"04:37 AM\" }, { \"no\": 11, \"station\": \"New Bongaigaon (NBQ)\", \"status\": \"0 hour 2 min before\", \"actarr\": \"06:23 AM\", \"scharr\": \"06:25 AM\", \"actdep\": \"06:27 AM\", \"schdep\": \"06:27 AM\" }, { \"no\": 12, \"station\": \"Kamakhya (KYQ)\", \"status\": \"0 hour 23 min before\", \"actarr\": \"08:48 AM\", \"scharr\": \"09:11 AM\", \"actdep\": \"09:13 AM\", \"schdep\": \"09:13 AM\" }, { \"no\": 13, \"station\": \"Guwahati (GHY)\", \"status\": \"No Delay\", \"actarr\": \"09:30 AM\", \"scharr\": \"09:30 AM\", \"actdep\": \"Destination\", \"schdep\": \"Destination\" } ], \"total\": 13, \"response_code\": 200, \"train_number\": \"12345\", \"position\": \" Saraighat Express is Destination Reached and there is No Delay when it arrived at Guwahati (GHY) at 09:30 AM (On Time)\" }";
        Gson gson = new Gson();
        Running rs = gson.fromJson(testresult,
                Running.class);*/

        // if (rs != null) {
        //System.out.println("City : " + currentWeather.getName());


        // }
        //t.setText(rs.getPosition().toString());
        //new DownloadWebpageTask().execute(u);





    }

    private void setDisplayList(Running rs) {


        ArrayList<Route> r = new ArrayList<Route>();
        List<Route> rl = new ArrayList<>();
        rl = rs.getRoute();

        for(int i = 0; i<rl.size();i++){
            r.add(rl.get(i));
        }



        MyCustomAdapter dataAdapter = new MyCustomAdapter(this,
                R.layout.run, r);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_http, menu);
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

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(HttpAct.this);

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {


                return "Unable to retrieve data. We are working on it..";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPreExecute() {

            dialog.setCancelable(true);
            dialog.setMessage("Fetching...");

            dialog.show();

        }
        @Override
        protected void onPostExecute(String result) {

            //String testresult= "{ \"route\": [ { \"no\": 1, \"station\": \"Howrah Jn (HWH)\", \"status\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"actdep\": \"03:50 PM\", \"schdep\": \"03:50 PM\" }, { \"no\": 2, \"station\": \"Barddhaman Jn (BWN)\", \"status\": \"0 hour 6 min before\", \"actarr\": \"04:58 PM\", \"scharr\": \"05:04 PM\", \"actdep\": \"05:06 PM\", \"schdep\": \"05:06 PM\" }, { \"no\": 3, \"station\": \"Bolpur S Niktn (BHP)\", \"status\": \"0 hour 2 min late\", \"actarr\": \"05:55 PM\", \"scharr\": \"05:53 PM\", \"actdep\": \"06:00 PM\", \"schdep\": \"05:58 PM\" }, { \"no\": 4, \"station\": \"Rampur Hat (RPH)\", \"status\": \"0 hour 1 min late\", \"actarr\": \"06:58 PM\", \"scharr\": \"06:57 PM\", \"actdep\": \"06:59 PM\", \"schdep\": \"06:59 PM\" }, { \"no\": 5, \"station\": \"New Farakka Jn (NFK)\", \"status\": \"0 hour 14 min before\", \"actarr\": \"08:36 PM\", \"scharr\": \"08:50 PM\", \"actdep\": \"08:52 PM\", \"schdep\": \"08:52 PM\" }, { \"no\": 6, \"station\": \"Malda Town (MLDT)\", \"status\": \"No Delay\", \"actarr\": \"09:30 PM\", \"scharr\": \"09:30 PM\", \"actdep\": \"09:45 PM\", \"schdep\": \"09:45 PM\" }, { \"no\": 7, \"station\": \"Kishanganj (KNE)\", \"status\": \"23 hour 10 min before\", \"actarr\": \"12:28 AM\", \"scharr\": \"11:38 PM\", \"actdep\": \"12:30 AM\", \"schdep\": \"11:40 PM\" }, { \"no\": 8, \"station\": \"New Jalpaiguri (NJP)\", \"status\": \"0 hour 3 min late\", \"actarr\": \"01:48 AM\", \"scharr\": \"01:45 AM\", \"actdep\": \"02:05 AM\", \"schdep\": \"02:05 AM\" }, { \"no\": 9, \"station\": \"New Cooch Behar (NCB)\", \"status\": \"No Delay\", \"actarr\": \"04:15 AM\", \"scharr\": \"04:15 AM\", \"actdep\": \"04:17 AM\", \"schdep\": \"04:17 AM\" }, { \"no\": 10, \"station\": \"New Alipurduar (NOQ)\", \"status\": \"No Delay\", \"actarr\": \"04:35 AM\", \"scharr\": \"04:35 AM\", \"actdep\": \"04:37 AM\", \"schdep\": \"04:37 AM\" }, { \"no\": 11, \"station\": \"New Bongaigaon (NBQ)\", \"status\": \"0 hour 2 min before\", \"actarr\": \"06:23 AM\", \"scharr\": \"06:25 AM\", \"actdep\": \"06:27 AM\", \"schdep\": \"06:27 AM\" }, { \"no\": 12, \"station\": \"Kamakhya (KYQ)\", \"status\": \"0 hour 23 min before\", \"actarr\": \"08:48 AM\", \"scharr\": \"09:11 AM\", \"actdep\": \"09:13 AM\", \"schdep\": \"09:13 AM\" }, { \"no\": 13, \"station\": \"Guwahati (GHY)\", \"status\": \"No Delay\", \"actarr\": \"09:30 AM\", \"scharr\": \"09:30 AM\", \"actdep\": \"Destination\", \"schdep\": \"Destination\" } ], \"total\": 13, \"response_code\": 200, \"train_number\": \"12345\", \"position\": \" Saraighat Express is Destination Reached and there is No Delay when it arrived at Guwahati (GHY) at 09:30 AM (On Time)\" }";
                    Gson gson = new Gson();
            Running rs = gson.fromJson(result,Running.class);

           // if (rs != null) {
                //System.out.println("City : " + currentWeather.getName());


           // }
            //t.setText(rs.getPosition().toString());
            dialog.hide();
            //t= (TextView) findViewById(R.id.statustext);
            //t.setText(rs.getPosition().toString());

            if (rs.getResponse_code()!=200){

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(rs.getRoute().size()==0)
            {
                Toast.makeText(getApplicationContext(), "Invalid train number..", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                /*if(rs.getTotal()==0){
                    Toast.makeText(getApplicationContext(), "Seems like we don't have info on this train..", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {*/
                    setDisplayList(rs);

            }



        }
    }
    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 2326 ;


        StringBuilder response  = new StringBuilder();

        URL url = new URL(myurl);
        HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
            String strLine = null;
            while ((strLine = input.readLine()) != null)
            {
                response.append(strLine);
            }
            input.close();
        }
        return response.toString();
        /*try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(10000 /* milliseconds );
            //conn.setConnectTimeout(15000 /* milliseconds );
            conn.setAllowUserInteraction(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            //conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string

            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }*/
    }



    private class MyCustomAdapter extends ArrayAdapter<Route> {

        private ArrayList<Route> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Route> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Route>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView station;
            TextView Accarival;
            TextView contAccDeparture;
            TextView status;
            TextView SchArrival;
            TextView SchDeparture;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
           // Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.run, null);

                holder = new ViewHolder();
                holder.station = (TextView) convertView.findViewById(R.id.station);
                holder.Accarival = (TextView) convertView.findViewById(R.id.name);
                holder.contAccDeparture = (TextView) convertView.findViewById(R.id.continent);
                holder.status = (TextView) convertView.findViewById(R.id.region);
                holder.SchArrival = (TextView) convertView.findViewById(R.id.textview5);
                holder.SchDeparture = (TextView) convertView.findViewById(R.id.textview6);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Route country = countryList.get(position);
            if(country.getStatus().contains(" Late"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.red));
            }
            else if (country.getStatus().contains(" before")){
                holder.status.setTextColor(getResources().getColor(R.color.try1));

            }
            else {
                holder.status.setTextColor(getResources().getColor(R.color.orange));

            }
            holder.station.setText(country.getStation_().getName()+" - "+country.getStation_().getCode());
            holder.Accarival.setText(country.getActarr());
            holder.contAccDeparture.setText(country.getActdep());
            holder.status.setText(country.getStatus());
            holder.SchArrival.setText(country.getScharr());
            holder.SchDeparture.setText(country.getSchdep());

            return convertView;

        }
    }

}



