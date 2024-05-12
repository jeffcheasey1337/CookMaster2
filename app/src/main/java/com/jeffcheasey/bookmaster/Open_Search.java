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

import com.jeffcheasey.bookmaster.RV_Adapter.Info;
import com.jeffcheasey.bookmaster.RV_Adapter.Listener;
import com.jeffcheasey.bookmaster.RV_Adapter.Rec_Adap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Open_Search extends AppCompatActivity implements Listener {
    private List<Info> data;
    private RecyclerView rv;
    int []a;
    Rec_Adap adapter;
    Db databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BookMaster");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Opaque));
        Intent intent=getIntent();
        a=intent.getIntArrayExtra("array");
        rv= (RecyclerView) findViewById(R.id.rv);
        databaseHelper = new Db(getApplicationContext());
        databaseHelper = new Db(this);
        data=new ArrayList<>();
        data=fillItems();
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
        for(int i=0;i<a.length;i++) {
            Cursor query = db.rawQuery("SELECT * FROM book where id=" + a[i]
                    + ";", null);
            while (query.moveToNext()) {
                String a = query.getString(5);
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(getAssets().open(a));
                    bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data.add(i, new Info(query.getInt(0), query.getString(1), query.getString(2), query.getString(6), bmp));
                bmp = null;
            }
            query.close();
        }
        db.close();
        return data;
    }


    @Override
    public void onRecyclerViewItemClicked(int position) {
        Intent intent=new Intent(this,More.class);
        intent.putExtra("id",data.get(position).getId());
        startActivity(intent);
    }
}
