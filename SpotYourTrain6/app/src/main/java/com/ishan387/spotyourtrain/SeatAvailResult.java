package com.ishan387.spotyourtrain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.seat.Availability;
import com.ishan387.models.seat.Seatavail;
import com.ishan387.models.trnbtw.Train;
import com.ishan387.models.trnbtw.TrainBetween;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SeatAvailResult extends AppCompatActivity {

    String frm,to,dtd,dtm,dty,tn,c,q;
    String u ="http://api.railwayapi.com/check_seat/train/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_avail_result);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

               tn = null;
                frm = null;
                to=null;
                dtd=null;
                dtm=null;
                dty=null;

                c=null;
                q=null;
            } else {
                tn = extras.getString("TRNNUM");
                frm = extras.getString("FRM");
                to = extras.getString("TT");
                dtd= extras.getString("DTDAY");
                dtm= extras.getString("DTMO");
                dty= extras.getString("DTY");

                c = extras.getString("CLS");
                q= extras.getString("QT");

            }
        } else {
            tn = (String) savedInstanceState.getSerializable("TRNNUM");

            frm= (String) savedInstanceState.getSerializable("FRM");

            to= (String) savedInstanceState.getSerializable("TT");
            dtd= (String) savedInstanceState.getSerializable("DTDM");
            dtm= (String) savedInstanceState.getSerializable("DTMO");
            dty= (String) savedInstanceState.getSerializable("DTY");

            c= (String) savedInstanceState.getSerializable("CLS");
            q= (String) savedInstanceState.getSerializable("QT");

           // frm = frm.toLowerCase();
           // to= to.toLowerCase();

        }
        if(dtd.length()==1){
            dtd= "0"+dtd;
        }
        if (dtm.length()==1)
        {
            dtm = "0"+dtm;
        }
        boolean flag;
        String [] tnum;
        if(tn.charAt(0)>='A' && tn.charAt(0)<='Z') {
            flag = false;
            tnum = tn.split("\\(");
            //  tnum[1] = tnum[1].replace("(","");
            tnum[1] = tnum[1].replace(")", "");


        }
        else {
            tnum = tn.split(" ");
            flag = true;
        }


        if (flag)
        {
            u = u + tnum[0]+"/source/"+frm.split("-")[1]+"/dest/"+to.split("-")[1]+"/date/"+dtd+"-"+dtm+"-"+dty+"/class/"+c+"/quota/"+q+"/apikey/thnxp9240/";

        }
        else
        {
            u = u + tnum[1]+"/source/"+frm.split("-")[1]+"/dest/"+to.split("-")[1]+"/date/"+dtd+"-"+dtm+"-"+dty+"/class/"+c+"/quota/"+q+"/apikey/thnxp9240/";
           // u="http://api.railwayapi.com/check_seat/train/12146/source/bbs/dest/Nk/date/20-10-2015/class/sl/quota/GN/apikey/thnxp9240/";
           // String ss= u ;
        }






















































































         new Downloadtbs ().execute(u);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seat_avail_result, menu);
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

        private List<Availability> ps = new ArrayList<>();


        //OnItemClickListener mItemClickListener;
        public MyRAdapter(List<Availability> p) {

            ps.addAll(p);

        }
        //private final onClick

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.seatavailrow, viewGroup, false);
            MyViewHolder viewHolder = new MyViewHolder(v);

            return viewHolder;
        }



        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {

            /*String [] st = ps.get(i).getBookingStatus().split(",");

            String book = st[0]+"-"+st[1]+" - "+st[2];*/
            viewHolder.date.setText(ps.get(i).getDate());
            viewHolder.status.setText(ps.get(i).getStatus());


        }





        @Override
        public int getItemCount() {
            return ps.size();
        }






    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView status;


        //public ImageView rg;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.rowdate);
            this.status = (TextView) itemView.findViewById(R.id.rowstatus);



            //this.rg =(ImageView) itemView.findViewById(R.id.confirmcolor);
        }
    }

    private class Downloadtbs extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(SeatAvailResult.this);

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
            Seatavail sa = gson.fromJson(result, Seatavail.class);
            //List<Train> t = tb.getTrain();

            if (sa.getResponseCode() != 200) {

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
            } else {
                if (sa.isError()) {
                    Toast.makeText(getApplicationContext(), "Please check the station names..", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    dialog.hide();
                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.sarrecycle);
                    mRecyclerView.setVisibility(View.VISIBLE);

                   // CardView cv = (CardView) findViewById(R.id.cardtbs);
                    //cv.setVisibility(View.VISIBLE);
                    //mAdView5.setVisibility(View.GONE);
                    TextView tnum = (TextView) findViewById(R.id.satnum);
                    TextView tname = (TextView) findViewById(R.id.satname);
                    TextView tfrom = (TextView) findViewById(R.id.safrom);
                    TextView tto = (TextView) findViewById(R.id.sato);
                    //TextView tclass = (TextView) findViewById(R.id.saclass);
                    TextView tquota = (TextView) findViewById(R.id.saquota);

                    tnum.setText(sa.getTrainNumber()+"-");
                    tname.setText(sa.getTrainName());
                    tfrom.setText(sa.getFrom().getName()+" ("+sa.getFrom().getCode()+")");
                    tto.setText(sa.getTo().getName()+" ("+sa.getTo().getCode()+")");
                   // tclass.setText(sa.getClass_().getClassName()+" "+sa.getClass_().getClassCode());
                    tquota.setText(sa.getQuota().getQuotaName()+"-"+sa.getQuota().getQuotaCode());


                    mRecyclerView.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(SeatAvailResult.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    MyRAdapter mAdapter = new MyRAdapter(sa.getAvailability());

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
