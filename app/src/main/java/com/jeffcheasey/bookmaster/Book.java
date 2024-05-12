package com.jeffcheasey.bookmaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import com.jeffcheasey.bookmaster.RV_Adapter.Info;
import com.jeffcheasey.bookmaster.RV_Adapter.Listener;
import com.jeffcheasey.bookmaster.RV_Adapter.Rec_Adap;
import com.jeffcheasey.bookmaster.resourcedb.resourcedb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Book extends AppCompatActivity implements Listener {
    private List<Info> data;
    TextView text;
    Rec_Adap adapter;
    Db databaseHelper;
    SQLiteDatabase db;
    private RecyclerView rv;
    resourcedb db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Любимые");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Opaque));
        rv= (RecyclerView) findViewById(R.id.rv);
        databaseHelper = new Db(getApplicationContext());
        databaseHelper.create_db();
        db1 = new resourcedb(this);
        text= (TextView) findViewById(R.id.textview);
        try {
            data=fillthedata(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        adapter = new Rec_Adap(data);
        adapter.setclick(this);
        rv.setAdapter(adapter);

    }

    private List<Info> fillthedata(List<Info> data) throws IOException {
        data=new ArrayList<>();
        db = databaseHelper.open();
        Cursor cursor= db1.getAll();
        int i=0;
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                Cursor query=db.rawQuery("SELECT * FROM book where id="+cursor.getInt(0)+";",null);
                if(query.getCount()!=0) {
                    while (query.moveToNext()) {
                        Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(query.getString(5)));
                        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                        data.add(i, new Info(query.getInt(0), query.getString(1), query.getString(2), query.getString(6), bmp));
                    }
                }
            }
        }

        else {
            data=null;
            text.setText("Ничего не найдено");
        }
        return data;
    }

    @Override
    public void onRecyclerViewItemClicked(int position) {
        Intent intent=new Intent(this,More.class);
        intent.putExtra("id",data.get(position).getId());
        startActivity(intent);
    }
}
