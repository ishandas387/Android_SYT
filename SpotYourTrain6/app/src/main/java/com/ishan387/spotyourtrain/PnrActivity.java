package com.ishan387.spotyourtrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PnrActivity extends AppCompatActivity {

    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr);
        final EditText e = (EditText) findViewById(R.id.pnrtext);

        setting = getSharedPreferences("MyPref", MODE_PRIVATE);
        if (setting.contains("pnr")) {
            // etno.setText(settings.getString("TrainNum", ""));
            e.setText(setting.getString("pnr", ""));
        }


        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button  check = (Button) findViewById(R.id.checkpnrb);



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter PNR number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setting = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putString("pnr", e.getText().toString());

                    // Commit the edits!
                    editor.commit();
                    Intent a = new Intent(PnrActivity.this, PnrResult.class);
                    a.putExtra("pnrnumber", e.getText().toString());
                    startActivity(a);

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pnr_menu, menu);
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
