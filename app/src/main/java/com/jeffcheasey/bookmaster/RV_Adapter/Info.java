package com.jeffcheasey.bookmaster.RV_Adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


public class Info {
    private String Name;
    private String Desc;
    private String dur;
    private Bitmap photo_id;
    private int id;
    public Info(int id,String name,String desc,String dur,Bitmap photo_id){
        this.id=id;
        this.photo_id=photo_id;
        Name=name;
        Desc=desc;
        this.dur=dur;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Bitmap getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Bitmap photo_id) {
        this.photo_id = photo_id;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
