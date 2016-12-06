package com.ishan387.spotyourtrain;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.route.Broutes;
import com.ishan387.models.route.Day;
import com.ishan387.models.route.Route;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfflineRouteResult extends AppCompatActivity {
    String f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_route_result);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                f= null;

                 }
            else {
                f= extras.getString("FNAME");
               // d = extras.getString("DATE");
            }
        } else {
            f= (String) savedInstanceState.getSerializable("FNAME");
           // d= (String) savedInstanceState.getSerializable("DATE");
        }

        StringBuilder text = new StringBuilder();

            File dir = new File(getFilesDir(), "routes");
            if (!dir.exists()) {

            } else {
                File file = new File(dir, f);


                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }


            }

        Gson gson = new Gson();
        //content = result;
        Broutes brt = gson.fromJson(text.toString(), Broutes.class);
        //List<Train> t = tb.getTrain();

        if (brt.getResponseCode() != 200) {

            Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
            finish();
        } else {

            //dialog.hide();
            RecyclerView routerecycler = (RecyclerView) findViewById(R.id.orouterecycler);
            routerecycler.setVisibility(View.VISIBLE);

            CardView cv = (CardView) findViewById(R.id.ocardrouteresult);
            cv.setVisibility(View.VISIBLE);
            //mAdView6.setVisibility(View.GONE);
            //mAdView7.setVisibility(View.GONE);

            routerecycler.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(OfflineRouteResult.this);
            routerecycler.setLayoutManager(mLayoutManager);
            MyRAdapter mAdapter = new MyRAdapter(brt.getRoute());
            routerecycler.setAdapter(mAdapter);
            TextView trn = (TextView) findViewById(R.id.oroutetrainum);
            TextView trnm = (TextView) findViewById(R.id.oroutetrainname);
            TextView rd1 = (TextView) findViewById(R.id.ord1);
            TextView rd2 = (TextView) findViewById(R.id.ord2);
            TextView rd3 = (TextView) findViewById(R.id.ord3);
            TextView rd4 = (TextView) findViewById(R.id.ord4);
            TextView rd5 = (TextView) findViewById(R.id.ord5);
            TextView rd6 = (TextView) findViewById(R.id.ord6);
            TextView rd7 = (TextView) findViewById(R.id.ord7);

            List<Day> dy = brt.getTrain().getDays();

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
           // tn= brt.getTrain().getNumber();
           // tnu = brt.getTrain().getName();
            // List<com.ishan387.models.route.Class> cl = brt.getTrain().getClasses();








        }



        // File file = new File(sdcard,"testFile.txt");





        //TextView tv = (TextView)findViewById(R.id.amount);

       // tv.setText(text.toString()); ////Set the text to text view.


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offline_route_result, menu);
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
}
