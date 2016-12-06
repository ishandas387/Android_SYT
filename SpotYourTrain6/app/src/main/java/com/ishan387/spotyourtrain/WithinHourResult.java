package com.ishan387.spotyourtrain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.seat.Availability;
import com.ishan387.models.seat.Seatavail;
import com.ishan387.models.within.Train;
import com.ishan387.models.within.WithinTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WithinHourResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_hour_result);

        String u = "http://api.railwayapi.com/arrivals/station/sbc/hours/5/apikey/thnxp9240/";
        new Downloadtbs().execute(u);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_within_hour_result, menu);
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
    public class MyRAdapter extends RecyclerView
            .Adapter<MyViewHolder> {

        private List<Train> ps = new ArrayList<>();


        //OnItemClickListener mItemClickListener;
        public MyRAdapter(List<Train> p) {

            ps.addAll(p);

        }
        //private final onClick

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.withinstationresultrow, viewGroup, false);
            MyViewHolder viewHolder = new MyViewHolder(v);

            return viewHolder;
        }



        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {

            /*String [] st = ps.get(i).getBookingStatus().split(",");

            String book = st[0]+"-"+st[1]+" - "+st[2];*/
            viewHolder.name.setText(ps.get(i).getName());
            viewHolder.schar.setText(ps.get(i).getScharr());
            viewHolder.schdep.setText(ps.get(i).getSchdep());
            viewHolder.actar.setText(ps.get(i).getActarr());
            viewHolder.actdep.setText(ps.get(i).getActdep());
            viewHolder.tnum.setText(ps.get(i).getNumber());



        }





        @Override
        public int getItemCount() {
            return ps.size();
        }






    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView schar;
        public TextView schdep;public TextView actar;public TextView actdep;
        TextView tnum;

        //public ImageView rg;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.withinrowname);
            this.schar = (TextView) itemView.findViewById(R.id.withinrowscharr);

            this.schdep = (TextView) itemView.findViewById(R.id.withinrowschdep);
            this.actar = (TextView) itemView.findViewById(R.id.withinrowaccaarr);
            this.actdep = (TextView) itemView.findViewById(R.id.withinrowaccdep);
            this.tnum = (TextView) itemView.findViewById(R.id.withinrownumber);



            //this.rg =(ImageView) itemView.findViewById(R.id.confirmcolor);
        }
    }

    private class Downloadtbs extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(WithinHourResult.this);

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

            //String s = "{ \"total\": 14, \"response_code\": 200, \"train\": [ { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16524\", \"no\": 1, \"dest_arrival_time\": \"08:20\", \"name\": \"BANGALORE EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"05:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16518\", \"no\": 2, \"dest_arrival_time\": \"08:20\", \"name\": \"BANGALORE EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"05:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16215\", \"no\": 3, \"dest_arrival_time\": \"09:30\", \"name\": \"CHAMUNDI EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"06:45\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12613\", \"no\": 4, \"dest_arrival_time\": \"13:45\", \"name\": \"TIPPU EXPRESS\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"11:15\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"17307\", \"no\": 5, \"dest_arrival_time\": \"16:40\", \"name\": \"BASAVA EXPRESS\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"13:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"N\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12008\", \"no\": 6, \"dest_arrival_time\": \"16:15\", \"name\": \"SHATABDI EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"14:15\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16557\", \"no\": 7, \"dest_arrival_time\": \"17:10\", \"name\": \"RAJYARANI EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"14:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16535\", \"no\": 8, \"dest_arrival_time\": \"18:25\", \"name\": \"GOLGUMBAZ EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"15:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16232\", \"no\": 9, \"dest_arrival_time\": \"18:50\", \"name\": \"MAILADUTURAI EX\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"16:00\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"56213\", \"no\": 10, \"dest_arrival_time\": \"20:15\", \"name\": \"TIRUPATI PASSR\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"17:00\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16236\", \"no\": 11, \"dest_arrival_time\": \"21:00\", \"name\": \"TUTICORIN EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"17:45\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16592\", \"no\": 12, \"dest_arrival_time\": \"21:50\", \"name\": \"HAMPI EXP\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"18:40\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"16022\", \"no\": 13, \"dest_arrival_time\": \"23:25\", \"name\": \"KAVERI EXPRESS\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"20:30\" }, { \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"56263\", \"no\": 14, \"dest_arrival_time\": \"04:00\", \"name\": \"BANGALORE PASSR\", \"to\": { \"name\": \"BANGALORECYJN\", \"code\": \"SBC\" }, \"from\": { \"name\": \"MYSORE JN\", \"code\": \"MYS\" }, \"src_departure_time\": \"23:55\" } ] }";


            Gson gson = new Gson();
            WithinTime wt = gson.fromJson(result, WithinTime.class);
            //List<Train> t = tb.getTrain();

            if (wt.getResponseCode() != 200) {

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
            } else {
                if (wt.getTotal()==0) {
                    Toast.makeText(getApplicationContext(), "Please check the station names..", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    dialog.hide();
                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.twrecycle);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    // CardView cv = (CardView) findViewById(R.id.cardtbs);
                    //cv.setVisibility(View.VISIBLE);
                    //mAdView5.setVisibility(View.GONE);

                    mRecyclerView.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(WithinHourResult.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    MyRAdapter mAdapter = new MyRAdapter(wt.getTrain());

                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                   /* TextView from = (TextView) findViewById(R.id.from);
                    TextView to = (TextView) findViewById(R.id.to);

                    from.setText(tb.getTrain().get(0).getFrom().getName() + " (" + tb.getTrain().get(0).getFrom().getCode() + ")");

                    to.setText(tb.getTrain().get(0).getTo().getName() + " (" + tb.getTrain().get(0).getTo().getCode() + ")");

                    d= (TextView) findViewById(R.id.d);
                    d.setText(month);*/
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


}
