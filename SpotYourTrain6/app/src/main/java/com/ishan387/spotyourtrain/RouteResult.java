package com.ishan387.spotyourtrain;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.ishan387.models.trnbtw.Train;
import com.ishan387.models.trnbtw.TrainBetween;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.ishan387.models.route.*;

import org.w3c.dom.Text;

public class RouteResult extends AppCompatActivity {

    String t;
    final String u = "http://api.railwayapi.com/route/train/";
    String ur = null;
    String [] tnum;
    boolean flag;
    AdView mAdView6;
    AdView mAdView7;
    Toolbar toolbar;
    String content=null;
    String tn,tnu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_result);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        //TextView test222 = (TextView) findViewById(R.id.test222);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                t = null;
            } else {
                t= extras.getString("TRNROUTE");

            }
        } else {
           t= (String) savedInstanceState.getSerializable("TRNROUTE");

        }

        if(t.charAt(0)>='A' && t.charAt(0)<='Z') {
            flag = false;
            tnum = t.split("\\(");
            //  tnum[1] = tnum[1].replace("(","");
            tnum[1] = tnum[1].replace(")","");


        }
        else {
            tnum = t.split(" ");
            flag = true;
        }

      if(flag)
      {
          ur = u+tnum[0]+"/apikey/thnxp9240/";

      }
        else
      {
          ur = u+tnum[1]+"/apikey/thnxp9240/";

      }

       // test222.setText(tnum[0]);
        mAdView6 = (AdView) findViewById(R.id.adView6);
        // AdRequest a = new AdRequest.
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView6.loadAd(adRequest);

        mAdView7 = (AdView) findViewById(R.id.adView7);
        // AdRequest a = new AdRequest.
        AdRequest adReq = new AdRequest.Builder().build();
        mAdView7.loadAd(adReq);



        new Downloadtrnroute().execute(ur);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.s) {

            File path=new File(getFilesDir(),"routes");
            if(!path.exists())
            {
                path.mkdir();
            }


             File fileWithinMyDir = new File(path, tn+"-"+tnu); //Getting a file within the dir.

            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(fileWithinMyDir);
                outputStream.write(content.getBytes());
                outputStream.close();
                Toast.makeText(getApplicationContext(), "Saved..", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyRAdapter extends RecyclerView
            .Adapter<MyRAdapter.ViewHolder> {

        private List<Route> rtt = new ArrayList<Route>();

        public MyRAdapter(List<Route> p) {

            rtt.addAll(p);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.singlkeroutelayout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            /*String [] st = ps.get(i).getBookingStatus().split(",");

            String book = st[0]+"-"+st[1]+" - "+st[2];*/
            String ss = rtt.get(i).getFullname();
            viewHolder.stnname.setText(ss);

            viewHolder.deptime.setText(rtt.get(i).getSchdep());
            viewHolder.arrivtime.setText(rtt.get(i).getScharr());

            viewHolder.day.setText("Day "+Long.toString(rtt.get(i).getDay()));

                viewHolder.halt.setText(Long.toString(rtt.get(i).getHalt())+" Mins");



                viewHolder.distance.setText(Long.toString(rtt.get(i).getDistance())+" Kms");



        }
        // Implement OnClick listener. The clicked item text is displayed in a Toast message.


        @Override
        public int getItemCount() {
            return rtt.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView stnname;

            public TextView deptime;
            public TextView arrivtime;
            public TextView day;
            public TextView halt;

            public TextView distance;


            //public ImageView rg;
            public ViewHolder(View itemView) {
                super(itemView);
                this.stnname = (TextView) itemView.findViewById(R.id.routestationame);
                this.deptime = (TextView) itemView.findViewById(R.id.routescdeptime);

                this.arrivtime = (TextView) itemView.findViewById(R.id.routeschartime);
                this.day = (TextView) itemView.findViewById(R.id.routeday);
                this.halt = (TextView) itemView.findViewById(R.id.routehaltvalue);
                this.distance = (TextView) itemView.findViewById(R.id.routedistancevalue);

            }
        }


    }

    private class Downloadtrnroute extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(RouteResult.this);

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
            content = result;
            Broutes brt = gson.fromJson(result, Broutes.class);
            //List<Train> t = tb.getTrain();

            if (brt.getResponseCode() != 200) {

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
                finish();
            } else {

                    dialog.hide();
                    RecyclerView routerecycler = (RecyclerView) findViewById(R.id.routerecycler);
                    routerecycler.setVisibility(View.VISIBLE);

                    CardView cv = (CardView) findViewById(R.id.cardrouteresult);
                    cv.setVisibility(View.VISIBLE);
                    mAdView6.setVisibility(View.GONE);
                mAdView7.setVisibility(View.GONE);

                routerecycler.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(RouteResult.this);
                    routerecycler.setLayoutManager(mLayoutManager);
                    MyRAdapter mAdapter = new MyRAdapter(brt.getRoute());
                    routerecycler.setAdapter(mAdapter);
                    TextView trn = (TextView) findViewById(R.id.routetrainum);
                    TextView trnm = (TextView) findViewById(R.id.routetrainname);
                    TextView rd1 = (TextView) findViewById(R.id.rd1);
                TextView rd2 = (TextView) findViewById(R.id.rd2);
                TextView rd3 = (TextView) findViewById(R.id.rd3);
                TextView rd4 = (TextView) findViewById(R.id.rd4);
                TextView rd5 = (TextView) findViewById(R.id.rd5);
                TextView rd6 = (TextView) findViewById(R.id.rd6);
                TextView rd7 = (TextView) findViewById(R.id.rd7);

                List<com.ishan387.models.route.Day> dy = brt.getTrain().getDays();

                rd1.setText(dy.get(0).getRuns());
                if (dy.get(0).getRuns().contains("N")) {
                   rd1.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd1.setTextColor(getResources().getColor(R.color.green));
                }
                rd2.setText(dy.get(1).getRuns());
                if (dy.get(1).getRuns().contains("N")) {
                    rd2.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd2.setTextColor(getResources().getColor(R.color.green));
                }
                rd3.setText(dy.get(2).getRuns());
                if (dy.get(2).getRuns().contains("N")) {
                    rd3.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd3.setTextColor(getResources().getColor(R.color.green));
                }

                rd4.setText(dy.get(3).getRuns());

                if (dy.get(3).getRuns().contains("N")) {
                    rd4.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd4.setTextColor(getResources().getColor(R.color.green));
                }
                rd5.setText(dy.get(4).getRuns());
                if (dy.get(4).getRuns().contains("N")) {
                    rd5.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd5.setTextColor(getResources().getColor(R.color.green));
                }

                rd6.setText(dy.get(5).getRuns());
                if (dy.get(5).getRuns().contains("N")) {
                    rd6.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd6.setTextColor(getResources().getColor(R.color.green));
                }
                rd7.setText(dy.get(6).getRuns());
                if (dy.get(6).getRuns().contains("N")) {
                    rd7.setTextColor(getResources().getColor(R.color.red));
                } else {
                    rd7.setTextColor(getResources().getColor(R.color.green));
                }

               /* TextView y2s = (TextView) findViewById(R.id.y2s);
                TextView y1a = (TextView) findViewById(R.id.y1a);
                TextView y2a = (TextView) findViewById(R.id.y2a);
                TextView y3a = (TextView) findViewById(R.id.y3a);
                TextView yfc = (TextView) findViewById(R.id.yfc);
                TextView ysl = (TextView) findViewById(R.id.ysl);
                TextView y3e = (TextView) findViewById(R.id.y3e);
                TextView ycc = (TextView) findViewById(R.id.ycc);*/

                //TextView  = (TextView) findViewById(R.id.to);
               trn.setText(brt.getTrain().getNumber());
                trnm.setText(brt.getTrain().getName());
                tn= brt.getTrain().getNumber();
                tnu = brt.getTrain().getName();
               // List<com.ishan387.models.route.Class> cl = brt.getTrain().getClasses();








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

