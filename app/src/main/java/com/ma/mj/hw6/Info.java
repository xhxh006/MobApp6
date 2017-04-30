package com.ma.mj.hw6;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KimMinJung on 2017-04-11.
 */

public class Info implements Parcelable {
    String en, et, em1,em2,em3, eh, date, checked;
    int rb;

    public Info (String en, int rb, String et, String em1, String em2, String em3, String eh, String date){
        this.en = en;
        this.rb = rb;
        this.et = et;
        this.em1 = em1;
        this.em2 = em2;
        this.em3 = em3;
        this.eh = eh;
        this.date = date;
        this.checked = "false";
    }

    protected Info(Parcel in) {
        en = in.readString();
        rb = in.readInt();
        et = in.readString();
        em1 = in.readString();
        em2 = in.readString();
        em3 = in.readString();
        eh = in.readString();
        date = in.readString();
        checked = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public String getName(){
        return en;
    }
    public int getRadio(){
        return rb;
    }
    public String getTel(){
        return et;
    }
    public String getMenu1(){
        return em1;
    }
    public String getMenu2(){
        return em2;
    }
    public String getMenu3(){
        return em3;
    }
    public String getWeb(){
        return eh;
    }
    public String getDate(){ return date; }
    public String getChecked(){return checked;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(en);
        dest.writeInt(rb);
        dest.writeString(et);
        dest.writeString(em1);
        dest.writeString(em2);
        dest.writeString(em3);
        dest.writeString(eh);
        dest.writeString(date);
        dest.writeString(checked);
    }

}
