package com.ishan387.spotyourtrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class OfflineRouteList extends AppCompatActivity {
    TextView tvempty;
    TextView tvempty1;
     ListView lv;
    ListView lvtbs;
    boolean b ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_route_list);
        lv= (ListView) findViewById(R.id.lvofflineroute);
       lvtbs = (ListView) findViewById(R.id.stbslist);
       tvempty = (TextView)findViewById(R.id.oempty);
         tvempty1= (TextView)findViewById(R.id.ooempty);

        setListView1();
        setListView2();
        registerForContextMenu(lv);

        registerForContextMenu(lvtbs);
        AdView mAdView = (AdView) findViewById(R.id.adViewoffline);
        // AdRequest a = new AdRequest.
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        lvtbs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fname1 = (String) (lvtbs.getItemAtPosition(i));
                Toast.makeText(getApplicationContext(), fname1, Toast.LENGTH_SHORT).show();
                Intent off = new Intent(OfflineRouteList.this, Offlinetbs.class);

                off.putExtra("FNAME", fname1);
                startActivity(off);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fname = (String) (lv.getItemAtPosition(i));
                Toast.makeText(getApplicationContext(), fname, Toast.LENGTH_SHORT).show();
                Intent off = new Intent(OfflineRouteList.this, OfflineRouteResult.class);

                off.putExtra("FNAME", fname);
                startActivity(off);

            }
        });


    }

    private void setListView2() {
        File dir1 = new File(getFilesDir(),"tbs");
        if (!dir1.exists())
        {
            //lvtbs.setEmptyView(findViewById(R.id.oempty));
            tvempty1.setVisibility(View.VISIBLE);


        }
        else {
            File[] filelist1 = dir1.listFiles();
            String[] theNamesOfFiles1 = new String[filelist1.length];
            for (int i = 0; i < theNamesOfFiles1.length; i++) {
                theNamesOfFiles1[i] = filelist1[i].getName();
            }
            lvtbs.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, theNamesOfFiles1 ));

        }

    }

    private void setListView1() {
        File dir = new File(getFilesDir(),"routes");
        if (!dir.exists())
        {
            tvempty.setVisibility(View.VISIBLE);

        }
        else {
            File[] filelist = dir.listFiles();
            String[] theNamesOfFiles = new String[filelist.length];
            for (int i = 0; i < theNamesOfFiles.length; i++) {
                theNamesOfFiles[i] = filelist[i].getName();
            }
            lv.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, theNamesOfFiles ));

        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvofflineroute) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle("Options");

            menu.add(0, 1, Menu.NONE, "Delete");
            b=true;

        }
        else if(v.getId() == R.id.stbslist) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle("Options");

            menu.add(0, 1, Menu.NONE, "Delete");
            b=false;

        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemId = item.getItemId();
        if (itemId == 1) {
            AdapterView.AdapterContextMenuInfo menuinfo;
            menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index_id =(int) menuinfo.position;
            if(b)
            {
                deleteUserData(lv.getItemAtPosition(index_id).toString());




            }
            else {
               // Toast.makeText(getApplicationContext(), "Second list view", Toast.LENGTH_SHORT).show();
                deleteUserData(lvtbs.getItemAtPosition(index_id).toString());


                // deleteUserData(fl.getItemAtPosition(index_id).toString());

            }

        }

        return super.onContextItemSelected(item);
    }

    private void deleteUserData(String s) {
        if(b){
            File dir = new File(getFilesDir(),"routes");
            if (!dir.exists())
            {
                //lvtbs.setEmptyView(findViewById(R.id.oempty));
               // tvempty1.setVisibility(View.VISIBLE);


            }
            else {

                File file = new File(dir, s);
                    file.delete();
                setListView1();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();



            }
        }
        else
        {
            File dir1 = new File(getFilesDir(), "tbs");
            if (!dir1.exists()) {
            //lvtbs.setEmptyView(findViewById(R.id.oempty));

            }
            else
            {
                File file = new File(dir1, s);
                file.delete();
                setListView2();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();


            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offline_route_list, menu);
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
