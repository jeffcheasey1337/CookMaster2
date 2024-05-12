package com.jeffcheasey.bookmaster.ListAdapter;



public class Items {
   public boolean box;
    public String name;


    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Items(String name, boolean check){
        this.name=name;
        box=check;
    }
}
