package com.suryajeet945.cc.gaugematchernew;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by cc on 24-06-2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] wireno;
    private final String[] sizeinmm;



    public CustomListAdapter(Activity context, String[] wireno,String[] sizeinmm) {
        super(context, R.layout.mylist, wireno);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.wireno = wireno;
        this.sizeinmm = sizeinmm;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView wireNo = (TextView) rowView.findViewById(R.id.wireno);
        TextView sizeInMM = (TextView) rowView.findViewById(R.id.sizeinmm);


        wireNo.setText(wireno[position]);
        sizeInMM.setText(sizeinmm[position]);

        return rowView;

    }
}