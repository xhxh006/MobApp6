package com.ma.mj.hw6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by KimMinJung on 2017-04-13.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    int num = 0;
    ArrayList<Info> arrayList = new ArrayList<>();
    ArrayList<Info> showList = new ArrayList<>();
    ArrayList<String> checking = new ArrayList<>();
    boolean checkVisible = false;
    public CustomAdapter(Context context, ArrayList<Info> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        this.showList.addAll(this.arrayList);
    }
    @Override
    public int getCount() {
        Log.i("show size", Integer.toString(showList.size()));
        return showList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("showitem",showList.get(position).toString());
        return showList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i("position",Integer.toString(position));
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_listview,null);
        }
        convertView.setTag(position);
        TextView t1 = (TextView)convertView.findViewById(R.id.tName);
        TextView t2 = (TextView)convertView.findViewById(R.id.tTel);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        CheckBox check = (CheckBox)convertView.findViewById(R.id.checkBox);
        check.setFocusable(false);
        check.setClickable(false);

        checking.add("false");

        Info one = showList.get(position);
        Log.i("show size",Integer.toString(showList.size()));
        Log.i("position",Integer.toString(position));

        t1.setText(one.getName());
        t2.setText(one.getTel());
        if(one.getRadio() == 1){
            img.setImageResource(R.drawable.chicken);
        }
        else if(one.getRadio() == 2){
            img.setImageResource(R.drawable.pizza);
        }
        else if(one.getRadio() == 3){
            img.setImageResource(R.drawable.hamburger);
        }

        if (checkVisible){
            check.setVisibility(View.VISIBLE);
        }
        else{
            check.setVisibility(View.INVISIBLE);
        }
        check.setChecked(false);

        return convertView;
    }
    Comparator<Info> nameAsc = new Comparator<Info>() {
        @Override
        public int compare(Info o1, Info o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    Comparator<Info> kindAsc = new Comparator<Info>() {
        @Override
        public int compare(Info o1, Info o2) {
            int value = 0;
            if(o1.getRadio() > o2.getRadio()){
                value = 1;
            }
            else if(o1.getRadio() < o2.getRadio()){
                value = -1;
            }
            return value;
        }
    };
    public void setAscSort(boolean value){
        showList.clear();
        if (value == true) {
            Collections.sort(arrayList, nameAsc);
            showList.addAll(arrayList);
            this.notifyDataSetChanged();
        }
        else {
            Collections.sort(arrayList,kindAsc);
            showList.addAll(arrayList);
            this.notifyDataSetChanged();
        }
    }
    public void showCheckBox(boolean value){
        checkVisible = value;
        this.notifyDataSetChanged();
    }
    public void filter(String text){
        showList.clear();
        if (text.length() == 0) {
            showList.addAll(arrayList);
            Log.i("show first", showList.toString());
        }
        else{
            for (Info searching : arrayList) {
                if (searching.getName().contains(text)) {
                    showList.add(searching);
                }
            }
            Log.i("not empty",showList.toString());
        }
        this.notifyDataSetChanged();
    }

//    public void setArrayList(ArrayList<Info> arrayList) {
//        this.arrayList = arrayList;
//    }
}
