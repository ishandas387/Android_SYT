package com.ishan387.spotyourtrain;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.Running;
import com.ishan387.models.pnr.BoardingPoint;
import com.ishan387.models.pnr.FromStation;
import com.ishan387.models.pnr.Passenger;
import com.ishan387.models.pnr.ReservationUpto;
import com.ishan387.models.pnr.ToStation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PnrResult extends AppCompatActivity {


    String u ="http://api.railwayapi.com/pnr_status/pnr/";
    final String apikey = "thnxp9240";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_result);

        String p;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                p = null;
            } else {

                p = extras.getString("pnrnumber");
            }
        } else {

            p= (String) savedInstanceState.getSerializable("pnrnumber");
        }


        //String pnrresulttest = "{ \"response_code\": 200, \"total_passengers\": 2, \"boarding_point\": { \"code\": \"CGL\", \"name\": \"CHENGALPATTU\" }, \"train_name\": \"PDY BBS EXPRESS\", \"passengers\": [ { \"no\": 1, \"booking_status\": \"B2 , 33,GN\", \"current_status\": \"CNF\" }, { \"no\": 2, \"booking_status\": \"B2 , 34,GN\", \"current_status\": \"CNF\" } ], \"chart_prepared\": \"N\", \"train_num\": \"12897\", \"doj\": \"16-9-2015\", \"class\": \"3A\", \"reservation_upto\": { \"code\": \"BBS\", \"name\": \"BHUBANESWAR\" }, \"to_station\": { \"code\": \"BBS\", \"name\": \"BHUBANESWAR\" }, \"from_station\": { \"code\": \"CGL\", \"name\": \"CHENGALPATTU\" }, \"pnr\": \"4208274317\", \"error\": false }";


        u = u+p+"/apikey/"+apikey;

        new DownloadWebpageTask().execute(u);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pnr_result, menu);
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
            .Adapter<MyRAdapter.ViewHolder> {

        private List<Passenger>  ps = new ArrayList<Passenger>();
        public MyRAdapter(List<Passenger> p){

            ps.addAll(p);

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.passenger, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            String [] st = ps.get(i).getBookingStatus().split(",");

            String book = st[0]+"-"+st[1]+" - "+st[2];
            viewHolder.bookingstatus.setText(book);
            if (ps.get(i).getCurrentStatus().equals("CNF"))
            {
                viewHolder.currentstatus.setText("CONFIRMED");
                viewHolder.rg.setImageResource(R.drawable.greensq);

            }
            else{
                viewHolder.currentstatus.setText(ps.get(i).getCurrentStatus());
                viewHolder.rg.setImageResource(R.drawable.redsq);

            }

        }

        @Override
        public int getItemCount() {
            return ps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public TextView bookingstatus;
            public TextView currentstatus;
            public ImageView rg;
            public ViewHolder(View itemView) {
                super(itemView);
                this.bookingstatus = (TextView)itemView.findViewById(R.id.bookingstatus);
                this.currentstatus =(TextView) itemView.findViewById(R.id.confirm);
                this.rg =(ImageView) itemView.findViewById(R.id.confirmcolor);
            }
        }



    }



   /* private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(PnrResult.this);

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {


                return "Unable to retrieve data. We are working on it..";
            }
        }*/



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

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(PnrResult.this);

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

            Gson gson = new Gson();
            com.ishan387.models.pnr.PnrResult pr = gson.fromJson(result,
                    com.ishan387.models.pnr.PnrResult.class);


            dialog.hide();
            //t= (TextView) findViewById(R.id.statustext);
            //t.setText(rs.getPosition().toString());

            if (pr.getResponseCode()!=200){

                Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
            }
            else {
                if(pr.getTotalPassengers()==0){
                    Toast.makeText(getApplicationContext(), "Please check the PNR..", Toast.LENGTH_SHORT).show();
                    finish();

                }
                else
                {
                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.passengers);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(PnrResult.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    CardView cv = (CardView) findViewById(R.id.card);
                    cv.setVisibility(View.VISIBLE);
                    TextView board =(TextView) findViewById(R.id.board);
                    TextView trainname =(TextView) findViewById(R.id.trainname);
                    TextView date =(TextView) findViewById(R.id.date);
                    //TextView cls =(TextView) findViewById(R.id.cls);
                    TextView fromstation =(TextView) findViewById(R.id.fromstation);
                    TextView tostation =(TextView) findViewById(R.id.tostation);
                    TextView upto =(TextView) findViewById(R.id.chrt);
                    BoardingPoint bp = pr.getBoardingPoint();
                    FromStation fs = pr.getFromStation();
                    ToStation ts = pr.getToStation();
                    ReservationUpto ru = pr.getReservationUpto();

                    String frmstation = fs.getName()+"\t("+fs.getCode()+")";
                    String tstn = ts.getName()+"\t("+ts.getCode()+")";
                    String b = bp.getName()+"\t("+bp.getCode()+")";
                    String r = ru.getName()+"\t("+ru.getCode()+")";

                    fromstation.setText(frmstation);
                    tostation.setText(tstn);
                    board.setText(b);
                    upto.setText(r);
                    date.setText(pr.getDoj());
                    trainname.setText(pr.getTrainName());
                    //cls.setText(pr.getClass_());



                    List<Passenger> ps = pr.getPassengers();
                    MyRAdapter mAdapter = new MyRAdapter(ps);
                    mRecyclerView.setAdapter(mAdapter);
                   // RecyclerView.ItemDecoration itemDecoration =
                     //       new DividerItemDecoration(Context.this, LinearLayoutManager.VERTICAL);
                    //mRecyclerView.addItemDecoration(itemDecoration);


                }
            }



        }
    }

}
