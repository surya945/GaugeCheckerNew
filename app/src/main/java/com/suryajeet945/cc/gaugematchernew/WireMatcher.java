package com.suryajeet945.cc.gaugematchernew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Surya jeet Singh on 26-06-2016.
 */

public class WireMatcher extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private class WiresAreaClass{
        String wires;
        double area;
        public WiresAreaClass(String wires,double area){
            this.wires=wires;
            this.area=area;
        }

    }
    private  class WiresAreaClassComparator implements Comparator<WiresAreaClass>{

        @Override
        public int compare(WiresAreaClass lhs, WiresAreaClass rhs) {
            if(lhs.area>rhs.area){
                return 1;
            }else if(lhs.area<rhs.area){
                return -1;
            }else{
                return 0;
            }
        }
    }

    ListView listView;

    public static double[] tempWireNo;
    public static double[] tempSizeInMm;
    public static double[] tempWireArea;
    public static List<WiresAreaClass> singleWireSizer;
    public static List<WiresAreaClass> doubleWireSizer;
    public static List<WiresAreaClass> tripleWireSizer;
    public static List<WiresAreaClass> fourWireSizer;
    public static List<WiresAreaClass> fiveWireSizer;
    public static List<WiresAreaClass> sixWireSizer;

    public static String[] finalNoofWiresArray;
    public static String[] finalSizeOfWiresArray;

    public static String[][] singleWireSizerArray;
    public static String[][] doubleWireSizerArray;
    public static String[][] tripleWireSizerArray;
    public static String[][] fourWireSizerArray;
    public static String[][] fiveWireSizerArray;
    public static String[][] sixWireSizerArray;

    public static double totalArea = 0;

    ProgressDialog progressDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wire_matcher);

        toolbar =(Toolbar)findViewById(R.id.myToolBar);
        toolbar.setTitle("Wire Matcher");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalArea = 0;


        String[] wireSizes = getIntent().getStringArrayExtra("wireSizes");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.spinner_entries,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addListnerOnSpinnerItemSelection();

        tempWireNo = WireNoAndSize.WireNos;
        tempSizeInMm = WireNoAndSize.SizeInMms;
        tempWireArea=new double[tempWireNo.length];
        DecimalUtils decimalUtils = new DecimalUtils();
        int temp = 0;
        for (int i = 0; i < wireSizes.length; i++) {
            totalArea = totalArea + (Double.parseDouble(wireSizes[i])) * (Double.parseDouble(wireSizes[i]));
        }

        TextView textViewTotalArea = (TextView) findViewById(R.id.textViewTotalCrossArea);
        textViewTotalArea.setText(decimalUtils.round(Double.toString(totalArea), 4));


        for (int i = 0; i < tempWireNo.length; i++) {
            tempWireArea[i] =tempSizeInMm[i]*tempSizeInMm[i];
        }

        SingleCkeckerAsyncTask singleCkeckerAsyncTask = new SingleCkeckerAsyncTask();
        DoubleCkeckerAsyncTask doubleCkeckerAsyncTask = new DoubleCkeckerAsyncTask();
        TripleCkeckerAsyncTask tripleCkeckerAsyncTask = new TripleCkeckerAsyncTask();
        FourCkeckerAsyncTask fourCkeckerAsyncTask = new FourCkeckerAsyncTask();
        FiveCkeckerAsyncTask fiveCkeckerAsyncTask = new FiveCkeckerAsyncTask();
        SixCkeckerAsyncTask sixCkeckerAsyncTask = new SixCkeckerAsyncTask();

        singleCkeckerAsyncTask.execute();
        doubleCkeckerAsyncTask.execute();
        tripleCkeckerAsyncTask.execute();
        fourCkeckerAsyncTask.execute();
        fiveCkeckerAsyncTask.execute();
        sixCkeckerAsyncTask.execute();

        try {
            singleCkeckerAsyncTask.get();
            doubleCkeckerAsyncTask.get();
            tripleCkeckerAsyncTask.get();
            fourCkeckerAsyncTask.get();
            fiveCkeckerAsyncTask.get();
            sixCkeckerAsyncTask.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        singleWireSizerArray = new String[singleWireSizer.size()][2];
        doubleWireSizerArray = new String[doubleWireSizer.size()][2];
        tripleWireSizerArray = new String[tripleWireSizer.size()][2];
        fourWireSizerArray = new String[fourWireSizer.size()][2];
        fiveWireSizerArray = new String[fiveWireSizer.size()][2];
        sixWireSizerArray = new String[sixWireSizer.size()][2];

        for (int singleInt = 0; singleInt < singleWireSizer.size(); singleInt++) {
            singleWireSizerArray[singleInt][0] = singleWireSizer.get(singleInt).wires;
            singleWireSizerArray[singleInt][1] = singleWireSizer.get(singleInt).area+"";
        }
        for (int i = 0; i < doubleWireSizer.size(); i++) {
            doubleWireSizerArray[i][0] = doubleWireSizer.get(i).wires;
            doubleWireSizerArray[i][1] = doubleWireSizer.get(i).area+"";
        }
        for (int i = 0; i < tripleWireSizer.size(); i++) {
            tripleWireSizerArray[i][0] = tripleWireSizer.get(i).wires;
            tripleWireSizerArray[i][1] = tripleWireSizer.get(i).area+"";
        }
        for (int i = 0; i < fourWireSizer.size(); i++) {
            fourWireSizerArray[i][0] = fourWireSizer.get(i).wires;
            fourWireSizerArray[i][1] = fourWireSizer.get(i).area+"";
        }
        for (int i = 0; i < fiveWireSizer.size(); i++) {
            fiveWireSizerArray[i][0] = fiveWireSizer.get(i).wires;
            fiveWireSizerArray[i][1] = fiveWireSizer.get(i).area+"";
        }
        for (int i = 0; i < sixWireSizer.size(); i++) {
            sixWireSizerArray[i][0] = sixWireSizer.get(i).wires;
            sixWireSizerArray[i][1] = sixWireSizer.get(i).area+"";
        }

        int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
        singlgeWireSizerLength = singleWireSizer.size();
        doubleWireSizerLength = doubleWireSizer.size();
        tripleWireSizerLength = tripleWireSizer.size();
        fourWireSizerLength = fourWireSizer.size();
        fiveWireSizerLength = fiveWireSizer.size();
        singlgeWireSizerLength = sixWireSizer.size();

        int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
        finalNoofWiresArray = new String[finalArrayLength];
        finalSizeOfWiresArray = new String[finalArrayLength];

        if (singlgeWireSizerLength != 0) {
            for (int i = 0; i < singlgeWireSizerLength; i++) {
                temp = i;
                finalNoofWiresArray[temp] = singleWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(singleWireSizerArray[i][1], 4);
            }
        }
        if (doubleWireSizerLength != 0) {
            for (int i = 0; i < doubleWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength;
                finalNoofWiresArray[temp] = doubleWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(doubleWireSizerArray[i][1], 4);
            }
        }
        if (tripleWireSizerLength != 0) {
            for (int i = 0; i < tripleWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength;
                finalNoofWiresArray[temp] = tripleWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(tripleWireSizerArray[i][1], 4);
            }
        }
        if (fourWireSizerLength != 0) {
            for (int i = 0; i < fourWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength;
                finalNoofWiresArray[temp] = fourWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(fourWireSizerArray[i][1], 4);
            }
        }
        if (finalArrayLength != 0) {
            for (int i = 0; i < fiveWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength;
                finalNoofWiresArray[temp] = fiveWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(fiveWireSizerArray[i][1], 4);
            }
        }
        if (sixWireSizerLength != 0) {
            for (int i = 0; i < sixWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength + fiveWireSizerLength;
                finalNoofWiresArray[temp] = sixWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(sixWireSizerArray[i][1], 4);
            }
        }

        listView = (ListView) findViewById(R.id.listView2);

        CustomListAdapter adapter1 = new CustomListAdapter(this, finalNoofWiresArray, finalSizeOfWiresArray);
        listView.setAdapter(adapter1);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        TextView textView = (TextView) view;
        DecimalUtils decimalUtils = new DecimalUtils();

        int temp = 0;

        if (textView.getText().toString() == "To Single") {
            SingleCkeckerAsyncTask singleCkeckerAsyncTask = new SingleCkeckerAsyncTask();
            singleCkeckerAsyncTask.execute();

            try {
                singleCkeckerAsyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            singleWireSizerArray = new String[singleWireSizer.size()][2];
            for (int singleInt = 0; singleInt < singleWireSizer.size(); singleInt++) {
                singleWireSizerArray[singleInt][0] = singleWireSizer.get(singleInt).wires;
                singleWireSizerArray[singleInt][1] = singleWireSizer.get(singleInt).area+"";

            }
            int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
            singlgeWireSizerLength = singleWireSizer.size();

            int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
            finalNoofWiresArray = new String[finalArrayLength];
            finalSizeOfWiresArray = new String[finalArrayLength];

            if (singlgeWireSizerLength != 0) {
                for (int i = 0; i < singlgeWireSizerLength; i++) {
                    temp = i;
                    finalNoofWiresArray[temp] = singleWireSizerArray[i][0];
                    finalSizeOfWiresArray[temp] = decimalUtils.round(singleWireSizerArray[i][1], 4);
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void addListnerOnSpinnerItemSelection() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedlistener());
    }

    public class CustomOnItemSelectedlistener extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedItemValue = parent.getItemAtPosition(pos).toString().trim();
            int position = parent.getSelectedItemPosition();
            DecimalUtils decimalUtils = new DecimalUtils();


            int temp = 0;

            if (position == 0) {

                SingleCkeckerAsyncTask singleCkeckerAsyncTask = new SingleCkeckerAsyncTask();
                singleCkeckerAsyncTask.execute();
                try {
                    singleCkeckerAsyncTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                singleWireSizerArray = new String[singleWireSizer.size()][2];
                for (int singleInt = 0; singleInt < singleWireSizer.size(); singleInt++) {
                    singleWireSizerArray[singleInt][0] = singleWireSizer.get(singleInt).wires;
                    singleWireSizerArray[singleInt][1] = singleWireSizer.get(singleInt).area+"";

                }
                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                singlgeWireSizerLength = singleWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];

                if (singlgeWireSizerLength != 0) {
                    for (int i = 0; i < singlgeWireSizerLength; i++) {
                        temp = i;
                        finalNoofWiresArray[temp] = singleWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(singleWireSizerArray[i][1], 4);
                    }
                }

                try
                {
                    CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                    listView.setAdapter(adapter1);
                }catch (Exception e)
                {

                }


            }
            if (position == 1) {
                DoubleCkeckerAsyncTask doubleCkeckerAsyncTask = new DoubleCkeckerAsyncTask();
                doubleCkeckerAsyncTask.execute();
                try {
                    doubleCkeckerAsyncTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                doubleWireSizerArray = new String[doubleWireSizer.size()][2];
                for (int i = 0; i < doubleWireSizer.size(); i++) {
                    doubleWireSizerArray[i][0] = doubleWireSizer.get(i).wires;
                    doubleWireSizerArray[i][1] = doubleWireSizer.get(i).area+"";
                }
                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                doubleWireSizerLength = doubleWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];

                if (doubleWireSizerLength != 0) {
                    for (int i = 0; i < doubleWireSizerLength; i++) {
                        temp = i + singlgeWireSizerLength;
                        finalNoofWiresArray[temp] = doubleWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(doubleWireSizerArray[i][1], 4);
                    }
                }

                CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                listView.setAdapter(adapter1);
            }
            if (position == 2) {
                TripleCkeckerAsyncTask tripleCkeckerAsyncTask = new TripleCkeckerAsyncTask();
                tripleCkeckerAsyncTask.execute();
                try {

                    tripleCkeckerAsyncTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                tripleWireSizerArray = new String[tripleWireSizer.size()][2];

                for (int i = 0; i < tripleWireSizer.size(); i++) {
                    tripleWireSizerArray[i][0] = tripleWireSizer.get(i).wires;
                    tripleWireSizerArray[i][1] = tripleWireSizer.get(i).area+"";
                }
                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                tripleWireSizerLength = tripleWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];

                if (tripleWireSizerLength != 0) {
                    for (int i = 0; i < tripleWireSizerLength; i++) {
                        temp = i + singlgeWireSizerLength + doubleWireSizerLength;
                        finalNoofWiresArray[temp] = tripleWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(tripleWireSizerArray[i][1], 4);
                    }
                }

                CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                listView.setAdapter(adapter1);
            }
            if (position == 3) {
                FourCkeckerAsyncTask fourCkeckerAsyncTask = new FourCkeckerAsyncTask();
                fourCkeckerAsyncTask.execute();
                try {

                    fourCkeckerAsyncTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                fourWireSizerArray = new String[fourWireSizer.size()][2];

                for (int i = 0; i < fourWireSizer.size(); i++) {
                    fourWireSizerArray[i][0] = fourWireSizer.get(i).wires;
                    fourWireSizerArray[i][1] = fourWireSizer.get(i).area+"";
                }
                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                fourWireSizerLength = fourWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];

                if (fourWireSizerLength != 0) {
                    for (int i = 0; i < fourWireSizerLength; i++) {
                        temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength;
                        finalNoofWiresArray[temp] = fourWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(fourWireSizerArray[i][1], 4);
                    }
                }

                CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                listView.setAdapter(adapter1);

            }
            if (position == 4) {
                FiveCkeckerAsyncTask fiveCkeckerAsyncTask = new FiveCkeckerAsyncTask();
                fiveCkeckerAsyncTask.execute();
                try {

                    fiveCkeckerAsyncTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                fiveWireSizerArray = new String[fiveWireSizer.size()][2];

                for (int i = 0; i < fiveWireSizer.size(); i++) {
                    fiveWireSizerArray[i][0] = fiveWireSizer.get(i).wires;
                    fiveWireSizerArray[i][1] = fiveWireSizer.get(i).area+"";
                }

                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                fiveWireSizerLength = fiveWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];

                if (finalArrayLength != 0) {
                    for (int i = 0; i < fiveWireSizerLength; i++) {
                        temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength;
                        finalNoofWiresArray[temp] = fiveWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(fiveWireSizerArray[i][1], 4);
                    }
                }


                CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                listView.setAdapter(adapter1);
            }
            if (position == 5) {
                SixCkeckerAsyncTask sixCkeckerAsyncTask = new SixCkeckerAsyncTask();
                sixCkeckerAsyncTask.execute();
                try {

                    sixCkeckerAsyncTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                sixWireSizerArray = new String[sixWireSizer.size()][2];


                for (int i = 0; i < sixWireSizer.size(); i++) {
                    sixWireSizerArray[i][0] = sixWireSizer.get(i).wires;
                    sixWireSizerArray[i][1] = sixWireSizer.get(i).area+"";
                }

                int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
                sixWireSizerLength = sixWireSizer.size();

                int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
                finalNoofWiresArray = new String[finalArrayLength];
                finalSizeOfWiresArray = new String[finalArrayLength];


                if (sixWireSizerLength != 0) {
                    for (int i = 0; i < sixWireSizerLength; i++) {
                        temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength + fiveWireSizerLength;
                        finalNoofWiresArray[temp] = sixWireSizerArray[i][0];
                        finalSizeOfWiresArray[temp] = decimalUtils.round(sixWireSizerArray[i][1], 4);
                    }
                }


                CustomListAdapter adapter1 = new CustomListAdapter(WireMatcher.this, finalNoofWiresArray, finalSizeOfWiresArray);
                listView.setAdapter(adapter1);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class Compare implements Comparator<String> {

        @Override
        public int compare(String lhs, String rhs) {

            String string1 = lhs.substring(lhs.indexOf("#") + 1);
            String string2 = rhs.substring(rhs.indexOf("#") + 1);

            Log.e("comp", string1);

            return string1.compareTo(string2);

        }
    }

    public class DecimalUtils {

        public String round(String value, int numberOfDigitsAfterDecimalPoint) {
            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                    BigDecimal.ROUND_HALF_UP);
            return bigDecimal.toString();
        }
    }

    public void btnSeeApproximationValueClick(View view) {


        DecimalUtils decimalUtils = new DecimalUtils();
        int temp = 0;

        SingleCkeckerAsyncTask singleCkeckerAsyncTask = new SingleCkeckerAsyncTask();
        DoubleCkeckerAsyncTask doubleCkeckerAsyncTask = new DoubleCkeckerAsyncTask();
        TripleCkeckerAsyncTask tripleCkeckerAsyncTask = new TripleCkeckerAsyncTask();
        FourCkeckerAsyncTask fourCkeckerAsyncTask = new FourCkeckerAsyncTask();
        FiveCkeckerAsyncTask fiveCkeckerAsyncTask = new FiveCkeckerAsyncTask();
        SixCkeckerAsyncTask sixCkeckerAsyncTask = new SixCkeckerAsyncTask();

        singleCkeckerAsyncTask.execute();
        doubleCkeckerAsyncTask.execute();
        tripleCkeckerAsyncTask.execute();
        fourCkeckerAsyncTask.execute();
        fiveCkeckerAsyncTask.execute();
        sixCkeckerAsyncTask.execute();

        try {
            singleCkeckerAsyncTask.get();
            doubleCkeckerAsyncTask.get();
            tripleCkeckerAsyncTask.get();
            fourCkeckerAsyncTask.get();
            fiveCkeckerAsyncTask.get();
            sixCkeckerAsyncTask.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        singleWireSizerArray = new String[singleWireSizer.size()][2];
        doubleWireSizerArray = new String[doubleWireSizer.size()][2];
        tripleWireSizerArray = new String[tripleWireSizer.size()][2];
        fourWireSizerArray = new String[fourWireSizer.size()][2];
        fiveWireSizerArray = new String[fiveWireSizer.size()][2];
        sixWireSizerArray = new String[sixWireSizer.size()][2];

        for (int singleInt = 0; singleInt < singleWireSizer.size(); singleInt++) {
            singleWireSizerArray[singleInt][0] = singleWireSizer.get(singleInt).wires;
            singleWireSizerArray[singleInt][1] = singleWireSizer.get(singleInt).area+"";
        }
        for (int i = 0; i < doubleWireSizer.size(); i++) {
            doubleWireSizerArray[i][0] = doubleWireSizer.get(i).wires;
            doubleWireSizerArray[i][1] = doubleWireSizer.get(i).area+"";
        }
        for (int i = 0; i < tripleWireSizer.size(); i++) {
            tripleWireSizerArray[i][0] = tripleWireSizer.get(i).wires;
            tripleWireSizerArray[i][1] = tripleWireSizer.get(i).area+"";
        }
        for (int i = 0; i < fourWireSizer.size(); i++) {
            fourWireSizerArray[i][0] = fourWireSizer.get(i).wires;
            fourWireSizerArray[i][1] = fourWireSizer.get(i).area+"";
        }
        for (int i = 0; i < fiveWireSizer.size(); i++) {
            fiveWireSizerArray[i][0] = fiveWireSizer.get(i).wires;
            fiveWireSizerArray[i][1] = fiveWireSizer.get(i).area+"";
        }
        for (int i = 0; i < sixWireSizer.size(); i++) {
            sixWireSizerArray[i][0] = sixWireSizer.get(i).wires;
            sixWireSizerArray[i][1] = sixWireSizer.get(i).area+"";
        }

        int singlgeWireSizerLength = 0, doubleWireSizerLength = 0, tripleWireSizerLength = 0, fourWireSizerLength = 0, fiveWireSizerLength = 0, sixWireSizerLength = 0;
        singlgeWireSizerLength = singleWireSizer.size();
        doubleWireSizerLength = doubleWireSizer.size();
        tripleWireSizerLength = tripleWireSizer.size();
        fourWireSizerLength = fourWireSizer.size();
        fiveWireSizerLength = fiveWireSizer.size();
        sixWireSizerLength = sixWireSizer.size();

        int finalArrayLength = singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fiveWireSizerLength + fourWireSizerLength + sixWireSizerLength;
        finalNoofWiresArray = new String[finalArrayLength];
        finalSizeOfWiresArray = new String[finalArrayLength];

        try {
            if (singleWireSizerArray != null) {
                for (int i = 0; i < singlgeWireSizerLength; i++) {
                    temp = i;
                    finalNoofWiresArray[temp] = singleWireSizerArray[i][0];
                    finalSizeOfWiresArray[temp] = decimalUtils.round(singleWireSizerArray[i][1], 4);
                }
            }
        } catch (Exception e) {

        }
        if (doubleWireSizerLength != 0) {
            for (int i = 0; i < doubleWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength;
                finalNoofWiresArray[temp] = doubleWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(doubleWireSizerArray[i][1], 4);
            }
        }
        if (tripleWireSizerLength != 0) {
            for (int i = 0; i < tripleWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength;
                finalNoofWiresArray[temp] = tripleWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(tripleWireSizerArray[i][1], 4);
            }
        }
        if (fourWireSizerLength != 0) {
            for (int i = 0; i < fourWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength;
                finalNoofWiresArray[temp] = fourWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(fourWireSizerArray[i][1], 4);
            }
        }
        if (finalArrayLength != 0) {
            for (int i = 0; i < fiveWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength;
                finalNoofWiresArray[temp] = fiveWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(fiveWireSizerArray[i][1], 4);
            }
        }
        if (sixWireSizerLength != 0) {
            for (int i = 0; i < sixWireSizerLength; i++) {
                temp = i + singlgeWireSizerLength + doubleWireSizerLength + tripleWireSizerLength + fourWireSizerLength + fiveWireSizerLength;
                finalNoofWiresArray[temp] = sixWireSizerArray[i][0];
                finalSizeOfWiresArray[temp] = decimalUtils.round(sixWireSizerArray[i][1], 4);
            }
        }


        CustomListAdapter adapter1 = new CustomListAdapter(this, finalNoofWiresArray, finalSizeOfWiresArray);
        listView.setAdapter(adapter1);


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

    public static int FindSmallestValueIndex(Double[] arry) {
        int index = 0;
        Double min = arry[index];

        for (int i = 0; i < arry.length; i++) {
            if (arry[i] < min) {
                min = arry[i];
                index = i;
            }

        }
        return index;

    }

    public class SingleCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> singleWireSizerTemp = SingleCkecker(totalArea);
            singleWireSizer = singleWireSizerTemp;

            return null;
        }
    }

    private class DoubleCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> doubleWireSizerTemp = DoubleCkecker(totalArea);
            doubleWireSizer = doubleWireSizerTemp;

            return null;
        }
    }

    private class TripleCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> tripleWireSizerTemp = TripleChecker(totalArea);
            tripleWireSizer = tripleWireSizerTemp;

            return null;
        }
    }

    private class FourCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> fourWireSizerTemp = FourChecker(totalArea);
            fourWireSizer = fourWireSizerTemp;

            return null;
        }
    }

    private class FiveCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> fiveWireSizerTemp = FiveChecker(totalArea);
            fiveWireSizer = fiveWireSizerTemp;

            return null;
        }
    }

    private class SixCkeckerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            List<WiresAreaClass> sixWireSizerTemp = SixChecker(totalArea);
            sixWireSizer = sixWireSizerTemp;

            return null;
        }
    }




    //method 1
    public List<WiresAreaClass> SingleCkecker(double totalArea) {

        ArrayList<Double> singleWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {

            double tempValue = totalArea - tempWireArea[i];
            if (Math.abs(tempValue)<2) {
                arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]),totalArea - tempValue));
            }


        }
        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {

            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;
    }



    //method 2
    public List<WiresAreaClass> DoubleCkecker(double totalArea) {

        ArrayList<Double> doubleWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {
            for (int j = i; j < tempWireArea.length && j<i+WireNoAndSize.WireRange; j++) {
                double tempValue = totalArea - (tempWireArea[i] + tempWireArea[j]);
                if (Math.abs(tempValue)<2) {
                    arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]) + "+" + Double.toString(tempWireNo[j]),totalArea - tempValue));
                }
            }

        }

        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {

            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;

    }



    //method 3
    public List<WiresAreaClass> TripleChecker(double totalArea) {
        ArrayList<Double> tripleWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {
            for (int j = i; j < tempWireArea.length&& j<i+WireNoAndSize.WireRange; j++) {
                for (int k = j; k < tempWireArea.length&& k<i+WireNoAndSize.WireRange; k++) {
                    double tempValue = totalArea - (tempWireArea[i]+tempWireArea[j]+tempWireArea[k]);
                    if (Math.abs(tempValue)<2) {
                        arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]) + "+" + Double.toString(tempWireNo[j]) + "+" + Double.toString(tempWireNo[k]),totalArea - tempValue));
                    }
                }
            }

        }
        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {

            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;
    }



    //method 4
    public List<WiresAreaClass> FourChecker(double totalArea) {
        ArrayList<Double> FourWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {
            for (int j = i; j < tempWireArea.length&& j<i+WireNoAndSize.WireRange; j++) {
                for (int k = j; k < tempWireArea.length&& k<i+WireNoAndSize.WireRange; k++) {
                    for (int l = k; l < tempWireArea.length&& l<i+WireNoAndSize.WireRange; l++) {
                        double tempValue = totalArea - (tempWireArea[i]+tempWireArea[j]+tempWireArea[k]+tempWireArea[l]);
                        if (Math.abs(tempValue)<2) {
                            arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]) + "+" + Double.toString(tempWireNo[j]) + "+" + Double.toString(tempWireNo[k])+ "+" + Double.toString(tempWireNo[l]),totalArea - tempValue));
                        }
                    }
                }
            }

        }
        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {

            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;
    }



    //method 5
    public List<WiresAreaClass> FiveChecker(double totalArea) {
        ArrayList<Double> FiveWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {
            for (int j = i; j < tempWireArea.length&& j<i+WireNoAndSize.WireRange; j++) {
                for (int k = j; k < tempWireArea.length&& k<i+WireNoAndSize.WireRange; k++) {
                    for (int l = k; l < tempWireArea.length&& l<i+WireNoAndSize.WireRange; l++) {
                        for (int m = l; m < tempWireArea.length&& m<i+WireNoAndSize.WireRange; m++) {
                            double tempValue = totalArea - (tempWireArea[i]+tempWireArea[j]+tempWireArea[k]+tempWireArea[l]+tempWireArea[m]);
                            if (Math.abs(tempValue)<2) {
                                arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]) + "+" + Double.toString(tempWireNo[j]) + "+" + Double.toString(tempWireNo[k]) + "+" + Double.toString(tempWireNo[l]) + "+" + Double.toString(tempWireNo[m]),totalArea - tempValue));
                            }
                        }
                    }
                }
            }

        }
        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {

            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;
    }


    //method 6
    public List<WiresAreaClass> SixChecker(double totalArea) {
        ArrayList<Double> SixWireSizer = new ArrayList<Double>();
        EditText ETsetApproxValue = (EditText) findViewById(R.id.editTextSetApproximationValue);
        ArrayList<WiresAreaClass> arrayListWireData = new ArrayList<>();
        ArrayList<WiresAreaClass> arrayListPositions = new ArrayList<>();
        for (int i = 0; i < tempWireArea.length; i++) {
            for (int j = i; j < tempWireArea.length&& j<i+WireNoAndSize.WireRange; j++) {
                for (int k = j; k < tempWireArea.length&& k<i+WireNoAndSize.WireRange; k++) {
                    for (int l = k; l < tempWireArea.length&& l<i+WireNoAndSize.WireRange; l++) {
                        for (int m = l; m < tempWireArea.length&& m<i+WireNoAndSize.WireRange; m++) {
                            for (int n = m; n < tempWireArea.length&& n<i+WireNoAndSize.WireRange; n++) {
                                double tempValue = totalArea - (tempWireArea[i]+tempWireArea[j]+tempWireArea[k]+tempWireArea[l]+tempWireArea[m]+tempWireArea[n]);
                                if (Math.abs(tempValue)<2) {
                                    arrayListPositions.add(new WiresAreaClass(Double.toString(tempWireNo[i]) + "+" + Double.toString(tempWireNo[j]) + "+" + Double.toString(tempWireNo[k]) + "+" + Double.toString(tempWireNo[l]) + "+" + Double.toString(tempWireNo[m]) + "+" + Double.toString(tempWireNo[n]),totalArea - tempValue));
                                }
                            }
                        }
                    }
                }
            }

        }
        Collections.sort(arrayListPositions,new WiresAreaClassComparator());

        if (!ETsetApproxValue.getText().toString().trim().isEmpty()) {
            double setApproxValue = Double.parseDouble(ETsetApproxValue.getText().toString().trim());
            for (WiresAreaClass w:arrayListPositions) {
                if (Math.abs(totalArea-w.area) < setApproxValue) {
                    arrayListWireData.add(w);
                }
            }
        } else {
            arrayListWireData.add(findMin(arrayListPositions));
        }

        return arrayListWireData;
    }


    public WiresAreaClass findMin(List<WiresAreaClass>wireAreaList){
        double min=Double.MAX_VALUE;
        WiresAreaClass minWire=new WiresAreaClass("",0);
        for(WiresAreaClass w:wireAreaList){
            if(min>Math.abs(totalArea-w.area)){
                min=Math.abs(totalArea-w.area);
                minWire=w;
            }
        }
        return minWire;
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
