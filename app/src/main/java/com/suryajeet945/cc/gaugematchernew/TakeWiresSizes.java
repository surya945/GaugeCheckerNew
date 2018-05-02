package com.suryajeet945.cc.gaugematchernew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cc on 26-06-2016.
 */
public class TakeWiresSizes extends AppCompatActivity {
    TextView textView1st;
    EditText editText1st;
    TextView textView2st;
    EditText editText2st;
    TextView textView3st;
    EditText editText3st;
    TextView textView4st;
    EditText editText4st;
    TextView textView5st;
    EditText editText5st;
    TextView textView6st;
    EditText editText6st;
    Toolbar toolbar;
    Button buttonGetFieldsToEnterSize,buttonGetCombinationOfWires;
    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.take_wire_sizes);

        toolbar =(Toolbar)findViewById(R.id.myToolBar);
        toolbar.setTitle("Wire Matcher");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView1st=(TextView)findViewById(R.id.textView1st);
        textView2st=(TextView)findViewById(R.id.textView2nd);
        textView3st=(TextView)findViewById(R.id.textView3rd);
        textView4st=(TextView)findViewById(R.id.textView4th);
        textView5st=(TextView)findViewById(R.id.textView5th);
        textView6st=(TextView)findViewById(R.id.textView6th);

        editText1st=(EditText)findViewById(R.id.editText1stWireSize);
        editText2st=(EditText)findViewById(R.id.editText2stWireSize);
        editText3st=(EditText)findViewById(R.id.editText3stWireSize);
        editText4st=(EditText)findViewById(R.id.editText4stWireSize);
        editText5st=(EditText)findViewById(R.id.editText5stWireSize);
        editText6st=(EditText)findViewById(R.id.editText6stWireSize);

        cardView1=(CardView)findViewById(R.id.cardView1);
        cardView2=(CardView)findViewById(R.id.cardView2);
        cardView3=(CardView)findViewById(R.id.cardView3);
        cardView4=(CardView)findViewById(R.id.cardView4);
        cardView5=(CardView)findViewById(R.id.cardView5);
        cardView6=(CardView)findViewById(R.id.cardView6);

        cardView1.setVisibility(View.INVISIBLE);
        cardView2.setVisibility(View.INVISIBLE);
        cardView3.setVisibility(View.INVISIBLE);
        cardView4.setVisibility(View.INVISIBLE);
        cardView5.setVisibility(View.INVISIBLE);
        cardView6.setVisibility(View.INVISIBLE);

        Button btnGetEditText=(Button)findViewById(R.id.buttonGetCombinationOfWires);
        btnGetEditText.setVisibility(View.INVISIBLE);

       // buttonGetCombinationOfWires=(Button)findViewById(R.id.buttonGetCombinationOfWires);


    }


    public void onBtnGetEditTextFieldsClick(View view)
    {
        EditText ETnoOfWires=(EditText)findViewById(R.id.editTextNoOfWires);
        String noOfWires=ETnoOfWires.getText().toString().trim();
        if(!noOfWires.isEmpty())
        {
            int noOfWiresInt;
            try {
                noOfWiresInt = Integer.parseInt(noOfWires);
            }catch (Exception e){
                Toast.makeText(TakeWiresSizes.this,"Please enter the no between 1 to 6",Toast.LENGTH_LONG).show();
                return;
            }
            if(noOfWiresInt>6)
            {
                Toast.makeText(getApplicationContext(),"Please enter no less than 7",Toast.LENGTH_SHORT).show();
            }
            else {
                MakeViewsVisible(noOfWiresInt);

            }
        }
    }

    public void onGetCombinationOfWiresClick(View view)
    {
        EditText etNoOfWires=(EditText)findViewById(R.id.editTextNoOfWires);
        EditText et1stWireSize=(EditText)findViewById(R.id.editText1stWireSize);
        EditText et2ndWireSize=(EditText)findViewById(R.id.editText2stWireSize);
        EditText et3rdWireSize=(EditText)findViewById(R.id.editText3stWireSize);
        EditText et4thWireSiwe=(EditText)findViewById(R.id.editText4stWireSize);
        EditText et5thWireSiwe=(EditText)findViewById(R.id.editText5stWireSize);
        EditText et6thWireSiwe=(EditText)findViewById(R.id.editText6stWireSize);

        String[] wireSizes;

        int noOfWires=Integer.parseInt(etNoOfWires.getText().toString().trim());

        if(noOfWires==1)
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
        }
        else if(noOfWires==2)
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
            wireSizes[1]=et2ndWireSize.getText().toString().trim();
        }
        else if(noOfWires==3)
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
            wireSizes[1]=et2ndWireSize.getText().toString().trim();
            wireSizes[2]=et3rdWireSize.getText().toString().trim();
        }
        else if(noOfWires==4)
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
            wireSizes[1]=et2ndWireSize.getText().toString().trim();
            wireSizes[2]=et3rdWireSize.getText().toString().trim();
            wireSizes[3]=et4thWireSiwe.getText().toString().trim();
        }
        else if(noOfWires==5)
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
            wireSizes[1]=et2ndWireSize.getText().toString().trim();
            wireSizes[2]=et3rdWireSize.getText().toString().trim();
            wireSizes[3]=et4thWireSiwe.getText().toString().trim();
            wireSizes[4]=et5thWireSiwe.getText().toString().trim();
        }
        else
        {
            wireSizes=new String[noOfWires];
            wireSizes[0]=et1stWireSize.getText().toString().trim();
            wireSizes[1]=et2ndWireSize.getText().toString().trim();
            wireSizes[2]=et3rdWireSize.getText().toString().trim();
            wireSizes[3]=et4thWireSiwe.getText().toString().trim();
            wireSizes[4]=et5thWireSiwe.getText().toString().trim();
            wireSizes[5]=et6thWireSiwe.getText().toString().trim();
        }
        for(int i=0;i<wireSizes.length;i++){
            if(wireSizes[i].equals("")){
                Toast.makeText(this,"Please enter some values in fields",Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent=new Intent(TakeWiresSizes.this,WireMatcher.class);
        intent.putExtra("wireSizes",wireSizes);
        startActivity(intent);

    }

    public void MakeViewsVisible(int noOfWires)
    {
        if(noOfWires==1)
        {
            cardView1.setVisibility(View.VISIBLE);


        }
        else if(noOfWires==2)
        {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);

        }
        else if(noOfWires==3)
        {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);

        }
        else if(noOfWires==4)
        {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);

        }
        else if(noOfWires==5)
        {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);

        }
        else if(noOfWires==6)
        {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
        }

        Button btnGetEditText=(Button)findViewById(R.id.buttonGetCombinationOfWires);
        btnGetEditText.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
