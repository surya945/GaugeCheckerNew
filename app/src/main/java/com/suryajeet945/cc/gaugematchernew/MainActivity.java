package com.suryajeet945.cc.gaugematchernew;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static double[] differenceInSizeArray = new double[WireNoAndSize.SizeInMm.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ImageView imageView=(ImageView)findViewById(R.id.imageView);
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.screw_image_alfa);

        //imageView.setImageBitmap(bitmap);


//        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.relativeHeader);
//        linearLayout.setVisibility(View.INVISIBLE);
    }
public void OnSettingClick(View view){
    Intent intent=new Intent(MainActivity.this,SettingActivity.class);
    startActivity(intent);
}
    @Override
    public void onBackPressed() {

        TextView no=(TextView)findViewById(R.id.TVNo);
        no.setVisibility(View.INVISIBLE);
        TextView mm=(TextView)findViewById(R.id.TVMm);
        mm.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.how_to_use) {
            Intent intent=new Intent(MainActivity.this,HowToUse.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gaugelist) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            Intent intent = new Intent(MainActivity.this, ShowRecords.class);
            startActivity(intent);

            return true;

        } else if (id == R.id.nav_wireMatcher) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            Intent intent=new Intent(MainActivity.this,TakeWiresSizes.class);
            startActivity(intent);


        } else if (id == R.id.nav_share) {
            ApplicationInfo applicationInfo=getApplicationContext().getApplicationInfo();
            String apkPath=applicationInfo.sourceDir;

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");

            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(intent,"SHARE APP USING"));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void onbtGaugeMeasureClick(View view) {
        Intent intent=new Intent(this,GaugeMeasureActivity.class);
        startActivity(intent);
    }

    public void onbtGaugeMatcherClick(View view) {
        Intent intent=new Intent(this,TakeWiresSizes.class);
        startActivity(intent);
    }
}
