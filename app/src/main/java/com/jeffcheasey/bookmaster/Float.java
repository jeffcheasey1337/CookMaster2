package com.jeffcheasey.bookmaster;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.jeffcheasey.bookmaster.RV_Adapter.Info;
import com.jeffcheasey.bookmaster.RV_Adapter.Listener;
import com.jeffcheasey.bookmaster.RV_Adapter.Rec_Adap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Float extends AppCompatActivity implements Listener {
    String  []a;
    String time;
    private List<Info> data;
    private RecyclerView rv;
    Rec_Adap adapter;
    Db databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BookMaster");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Opaque));
        TextView text= (TextView) findViewById(R.id.text);
        Intent intent=getIntent();
        time=intent.getStringExtra("time");
        //System.out.println("fuck"+time);
        a=intent.getStringArrayExtra("array");
        //System.out.println(a.length);

        rv= (RecyclerView) findViewById(R.id.rv);
        databaseHelper = new Db(getApplicationContext());
        databaseHelper = new Db(this);
        data=new ArrayList<>();
        data=fillItems();
        if(data.size()==0){
            text.setTextSize(15);
            text.setText("Ничего не найдено");
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        adapter = new Rec_Adap(data);
        adapter.setclick(this);
        rv.setAdapter(adapter);

    }

    private List<Info> fillItems() {
        List<Info> data=new ArrayList<>();
        db = databaseHelper.open();
        int check=0;
        for(int i=0;i<a.length;i++) {
            Cursor query = db.rawQuery("SELECT  * from book where dur<=" + time + ";", null);
            while (query.moveToNext()) {
          //      System.out.println(query.getString(1));
                if (a[i] != null) {
                    if (query.getString(6).toLowerCase().contains(a[i].toLowerCase())) {
                        Bitmap bmp = null;
                        try {
                            bmp = BitmapFactory.decodeStream(getAssets().open(query.getString(5)));
                            bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        data.add(check, new Info(query.getInt(0), query.getString(1), query.getString(2), query.getString(6), bmp));
                        bmp = null;
                        check++;
                    }
                }
            }query.close();

        }db.close();

    return  data;}

    @Override
    public void onRecyclerViewItemClicked(int position) {
        Intent intent=new Intent(this,More.class);
        intent.putExtra("id",data.get(position).getId());
        System.out.println("aa");
        startActivity(intent);
    }
}
