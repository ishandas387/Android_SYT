package com.ishan387.spotyourtrain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.ishan387.models.pnr.Passenger;
import com.ishan387.models.trnbtw.Train;
import com.ishan387.models.trnbtw.TrainBetween;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrainBetweenStations extends AppCompatActivity {


    String u ="http://api.railwayapi.com/between/source/";
    String content = null;
    String fff,ttt;
    Toolbar toolbar;


    final String apikey = "thnxp9240";
    final String ZERO = "0";
    AdView mAdView5;
    TextView d;
    String month;
    int lastPosition =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_train_between_stations);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        mAdView5 = (AdView) findViewById(R.id.adView5);
        // AdRequest a = new AdRequest.
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView5.loadAd(adRequest);

        String frm,to,dt;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                frm = null;
                to=null;
                dt=null;
            } else {

                frm = extras.getString("FROM");
                to = extras.getString("TO");
                dt= extras.getString("DATE");
            }
        } else {

            frm= (String) savedInstanceState.getSerializable("FROM");

            to= (String) savedInstanceState.getSerializable("TO");
            dt= (String) savedInstanceState.getSerializable("DATE");
            frm = frm.toLowerCase();
            to= to.toLowerCase();
        }


        //String pnrresulttest = "{ \"response_code\": 200, \"total_passengers\": 2, \"boarding_point\": { \"code\": \"CGL\", \"name\": \"CHENGALPATTU\" }, \"train_name\": \"PDY BBS EXPRESS\", \"passengers\": [ { \"no\": 1, \"booking_status\": \"B2 , 33,GN\", \"current_status\": \"CNF\" }, { \"no\": 2, \"booking_status\": \"B2 , 34,GN\", \"current_status\": \"CNF\" } ], \"chart_prepared\": \"N\", \"train_num\": \"12897\", \"doj\": \"16-9-2015\", \"class\": \"3A\", \"reservation_upto\": { \"code\": \"BBS\", \"name\": \"BHUBANESWAR\" }, \"to_station\": { \"code\": \"BBS\", \"name\": \"BHUBANESWAR\" }, \"from_station\": { \"code\": \"CGL\", \"name\": \"CHENGALPATTU\" }, \"pnr\": \"4208274317\", \"error\": false }";


        //u = u+p+"/apikey/"+apikey;
        String fromarr[] = frm.split("-");
        String toarr[] = to.split("-");
        String datearr[] = dt.split("-");

        if (datearr[0].length()==1)
        {
            datearr[0] = ZERO+datearr[0];
        }
        if(datearr[1].length()==1)
        {
            datearr[1]=ZERO+datearr[1];
        }
        switch (datearr[1]){

            case "01": month = datearr[0]+"-JAN";
                break;
            case "02": month = datearr[0]+"-FEB";
                break;
            case "03": month = datearr[0]+"-MAR";
                break;
            case "04": month = datearr[0]+"-APR";
                break;
            case "05": month = datearr[0]+"-MAY";
                break;
            case "06": month = datearr[0]+"-JUN";
                break;
            case "07": month = datearr[0]+"-JUL";
                break;
            case "08": month = datearr[0]+"-AUG";
                break;
            case "09": month = datearr[0]+"-SEP";
                break;
            case "10": month = datearr[0]+"-OCT";
                break;
            case "11": month = datearr[0]+"-NOV";
                break;
            case "12": month = datearr[0]+"-DEC";
                break;


        }

        u= u+fromarr[1].toLowerCase()+"/dest/"+toarr[1].toLowerCase()+"/date/"+datearr[0]+"-"+datearr[1]+"/apikey/thnxp9240/";



        new Downloadtbs ().execute(u);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_between_stations, menu);
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

            File path=new File(getFilesDir(),"tbs");
            if(!path.exists())
            {
                path.mkdir();
            }


            File fileWithinMyDir = new File(path, fff+"-"+ttt); //Getting a file within the dir.

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
            .Adapter<MyViewHolder>  {

        private List<Train> ps = new ArrayList<Train>();


        //OnItemClickListener mItemClickListener;
        public MyRAdapter(List<Train> p) {

            ps.addAll(p);

        }
        //private final onClick

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.trainbetween, viewGroup, false);
            MyViewHolder viewHolder = new MyViewHolder(v);
          // v.setOnClickListener(this);
            return viewHolder;
        }



        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {

            /*String [] st = ps.get(i).getBookingStatus().split(",");

            String book = st[0]+"-"+st[1]+" - "+st[2];*/
            viewHolder.trainname.setText(ps.get(i).getName());
            viewHolder.trainnumber.setText(ps.get(i).getNumber());
            viewHolder.deptime.setText(ps.get(i).getSrcDepartureTime());
            viewHolder.arrivtime.setText(ps.get(i).getDestArrivalTime());

            viewHolder.d1.setText(ps.get(i).getDays().get(0).getRuns());
            if (ps.get(i).getDays().get(0).getRuns().contains("N")) {
                viewHolder.d1.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d1.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d2.setText(ps.get(i).getDays().get(1).getRuns());
            if (ps.get(i).getDays().get(1).getRuns().contains("N")) {
                viewHolder.d2.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d2.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d3.setText(ps.get(i).getDays().get(2).getRuns());
            if (ps.get(i).getDays().get(2).getRuns().contains("N")) {
                viewHolder.d3.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d3.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d4.setText(ps.get(i).getDays().get(3).getRuns());
            if (ps.get(i).getDays().get(3).getRuns().contains("N")) {
                viewHolder.d4.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d4.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d5.setText(ps.get(i).getDays().get(4).getRuns());
            if (ps.get(i).getDays().get(4).getRuns().contains("N")) {
                viewHolder.d5.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d5.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d6.setText(ps.get(i).getDays().get(5).getRuns());
            if (ps.get(i).getDays().get(5).getRuns().contains("N")) {
                viewHolder.d6.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d6.setTextColor(getResources().getColor(R.color.green));
            }
            viewHolder.d7.setText(ps.get(i).getDays().get(6).getRuns());
            if (ps.get(i).getDays().get(6).getRuns().contains("N")) {
                viewHolder.d7.setTextColor(getResources().getColor(R.color.red));
            } else {
                viewHolder.d7.setTextColor(getResources().getColor(R.color.green));
            }

           /* if (ps.get(i).getCurrentStatus().equals("CNF"))
            {
                viewHolder.currentstatus.setText("CONFIRMED");
                viewHolder.rg.setImageResource(R.drawable.greensq);

            }
            else{
                viewHolder.currentstatus.setText(ps.get(i).getCurrentStatus());
                viewHolder.rg.setImageResource(R.drawable.redsq);

            }*/
           // setAnimation(MyViewHolder.container, i);

        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(TrainBetweenStations.this, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

     /* @Override
        public void onClick(View view) {

            //mItemClickListener.onItemClick(v, getPosition()); //OnItemClickListener mItemClickListener;
            MyViewHolder holder = (MyViewHolder) view.getTag();
            if (view.getId() == holder.trainnumber.getId()) {
                Toast.makeText(TrainBetweenStations.this, holder.trainnumber.getText(), Toast.LENGTH_SHORT).show();
                Intent it = new Intent(TrainBetweenStations.this, RouteResult.class);
                it.putExtra("TRNROUTE", holder.trainnumber.getText());
                startActivity(it);
            }
        }*/



        @Override
        public int getItemCount() {
            return ps.size();
        }






    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trainnumber;
        public TextView trainname;
        public TextView deptime;
        public TextView arrivtime;
        public TextView d1;
        public TextView d2;

        public TextView d3;

        public TextView d4;

        public TextView d5;
        public TextView d6;
        public TextView d7;

        public  static  RelativeLayout container ;

        //public ImageView rg;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.trainname = (TextView) itemView.findViewById(R.id.trainname);
            this.container = (RelativeLayout) itemView.findViewById(R.id.container);
            this.trainnumber = (TextView) itemView.findViewById(R.id.trainnum);
            this.deptime = (TextView) itemView.findViewById(R.id.deptime);
            this.arrivtime = (TextView) itemView.findViewById(R.id.arrtime);
            this.d1 = (TextView) itemView.findViewById(R.id.d1);
            this.d2 = (TextView) itemView.findViewById(R.id.d2);
            this.d3 = (TextView) itemView.findViewById(R.id.d3);
            this.d4 = (TextView) itemView.findViewById(R.id.d4);
            this.d5 = (TextView) itemView.findViewById(R.id.d5);
            this.d6 = (TextView) itemView.findViewById(R.id.d6);
            this.d7 = (TextView) itemView.findViewById(R.id.d7);


            //this.rg =(ImageView) itemView.findViewById(R.id.confirmcolor);
        }
    }

    private class Downloadtbs extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(TrainBetweenStations.this);

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
            content =result;
            TrainBetween tb = gson.fromJson(result, TrainBetween.class);
            //List<Train> t = tb.getTrain();

            if (tb.getResponseCode() != 200) {

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
            } else {
                if (tb.getTotal() == 0) {
                    Toast.makeText(getApplicationContext(), "Please check the station names..", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    dialog.hide();
                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.tbst);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    CardView cv = (CardView) findViewById(R.id.cardtbs);
                    cv.setVisibility(View.VISIBLE);
                    mAdView5.setVisibility(View.GONE);

                    mRecyclerView.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(TrainBetweenStations.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    MyRAdapter mAdapter = new MyRAdapter(tb.getTrain());

                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    TextView from = (TextView) findViewById(R.id.from);
                    TextView to = (TextView) findViewById(R.id.to);

                    from.setText(tb.getTrain().get(0).getFrom().getName() + " (" + tb.getTrain().get(0).getFrom().getCode() + ")");

                    to.setText(tb.getTrain().get(0).getTo().getName() + " (" + tb.getTrain().get(0).getTo().getCode() + ")");

                    d= (TextView) findViewById(R.id.d);
                    d.setText(month);

                    fff = tb.getTrain().get(0).getFrom().getName();
                    ttt=  tb.getTrain().get(0).getTo().getName();

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









