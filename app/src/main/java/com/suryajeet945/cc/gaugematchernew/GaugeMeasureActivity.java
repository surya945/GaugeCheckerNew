package com.suryajeet945.cc.gaugematchernew;

import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class GaugeMeasureActivity extends AppCompatActivity {
    public static double[] differenceInSizeArray = new double[WireNoAndSize.SizeInMm.length];
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauge_measure);


        toolbar =(Toolbar)findViewById(R.id.myToolBar);
        toolbar.setTitle("Wire Matcher");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNev();
    }

    public void onbtGaugeCheckClick(View view) {

        EditText ETsizeInMm;
        ListView listView;
        final String[] tempWireNo = new String[3];
        String[] tempSizeInMm = new String[3];
        for (int x = 0; x < 3; x++) {
            tempWireNo[x] = "";
            tempSizeInMm[x] = "";
        }
        double sizeInMm = 0;
        double checkNo = 0;
        double checkNoNext = 0;
        int maxmMachedIndex = 0;
        ETsizeInMm = (EditText) findViewById(R.id.ETenterTheGaugeValueInMm);

        if (!ETsizeInMm.getText().toString().trim().isEmpty() ) {
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.relativeHeader);
            linearLayout.setVisibility(View.VISIBLE);

            try {

                sizeInMm = Double.parseDouble(ETsizeInMm.getText().toString());
            }catch (Exception e){
                Toast.makeText(this,"Please enter a valid size like 1.02",Toast.LENGTH_LONG).show();
                return;
            }

            for (int j = 0; j < WireNoAndSize.WireNos.length; j++) {
                double temp = Double.parseDouble(WireNoAndSize.SizeInMm[j]);
                temp = temp - sizeInMm;
                if (temp < 0) {
                    temp = (-1) * temp;
                    differenceInSizeArray[j] = temp;
                } else {
                    differenceInSizeArray[j] = temp;
                }
            }

            maxmMachedIndex = FindSmallestValueIndex(differenceInSizeArray);

            if (maxmMachedIndex != 0) {
                tempWireNo[0] = WireNoAndSize.WireNo[maxmMachedIndex - 1];
                tempSizeInMm[0] = WireNoAndSize.SizeInMm[maxmMachedIndex - 1];
            }

            tempWireNo[1] = WireNoAndSize.WireNo[maxmMachedIndex];
            tempSizeInMm[1] = WireNoAndSize.SizeInMm[maxmMachedIndex];

            if (WireNoAndSize.SizeInMm.length - maxmMachedIndex > 1) {
                tempWireNo[2] = WireNoAndSize.WireNo[maxmMachedIndex + 1];
                tempSizeInMm[2] = WireNoAndSize.SizeInMm[maxmMachedIndex + 1];
            }


            listView = (ListView) findViewById(R.id.resultListView);

            CustomListAdapter adapter = new CustomListAdapter(this, tempWireNo, tempSizeInMm);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String Slecteditem = tempWireNo[+position];
                    Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
    public static int FindSmallestValueIndex(double[] arry) {
        int index = 0;
        double min = arry[index];

        for (int i = 0; i < arry.length; i++) {
            if (arry[i] < min) {
                min = arry[i];
                index = i;
            }

        }
        return index;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        if (id == R.id.settings) {
            /*Intent intent=new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);*/
        }

        if(id==R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
