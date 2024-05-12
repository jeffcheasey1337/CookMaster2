package com.jeffcheasey.bookmaster.resourcedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewDebug;


public class resourcedb extends SQLiteOpenHelper {
    public resourcedb(Context context) {
        super(context, "mydb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table favourite( ID INTEGER);");
    }
    public boolean insertdata(int a){
        ContentValues values=new ContentValues();
        values.put("id",a);
        SQLiteDatabase db=getWritableDatabase();
        db.insert("favourite",null,values);
        return true;
    }
    public Cursor getAll(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from favourite",null);
    }
    public Cursor check(int id){
        SQLiteDatabase db=getReadableDatabase();
        try {
            Cursor a = db.rawQuery("select * from favourite WHERE id=" + Integer.toString(id) ,null);
            System.out.println(a);
            System.out.println(a.getCount());
            if (a.getCount()==0){
                return null;
            }
            else {
                return a;
            }
        }catch (Exception ex){
            return  null;
        }
    }
    public int delet(String a){
        SQLiteDatabase db=getReadableDatabase();
        return db.delete("favourite","id=?",new String[]{a});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}