package com.ishan387.spotyourtrain;

import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ishan387.models.trnbtw.Train;
import com.ishan387.models.trnbtw.TrainBetween;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Offlinetbs extends AppCompatActivity {
    String f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlinetbs);
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

        File dir = new File(getFilesDir(), "tbs");
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
        //content =result;
        TrainBetween tb = gson.fromJson(text.toString(), TrainBetween.class);
        //List<Train> t = tb.getTrain();

        if (tb.getResponseCode() != 200) {

            Toast.makeText(getApplicationContext(), "Something went wrong, we are working on it..", Toast.LENGTH_SHORT).show();
        } else {
            if (tb.getTotal() == 0) {
                Toast.makeText(getApplicationContext(), "Please check the station names..", Toast.LENGTH_SHORT).show();
                finish();

            } else {
               // dialog.hide();
                RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.otbst);
                mRecyclerView.setVisibility(View.VISIBLE);

                CardView cv = (CardView) findViewById(R.id.ocardtbs);
                cv.setVisibility(View.VISIBLE);
               // mAdView5.setVisibility(View.GONE);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(Offlinetbs.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                MyRAdapter mAdapter = new MyRAdapter(tb.getTrain());

                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                TextView from = (TextView) findViewById(R.id.ofrom);
                TextView to = (TextView) findViewById(R.id.oto);

                from.setText(tb.getTrain().get(0).getFrom().getName() + " (" + tb.getTrain().get(0).getFrom().getCode() + ")");

                to.setText(tb.getTrain().get(0).getTo().getName() + " (" + tb.getTrain().get(0).getTo().getCode() + ")");

                //d= (TextView) findViewById(R.id.od);
                //d.setText(month);

               // fff = tb.getTrain().get(0).getFrom().getName();
                //ttt=  tb.getTrain().get(0).getTo().getName();

            }
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offlinetbs, menu);
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
          //  setAnimation(MyViewHolder.container, i);

        }

       /* private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(TrainBetweenStations.this, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }*/

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

        public  static RelativeLayout container ;

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

}
