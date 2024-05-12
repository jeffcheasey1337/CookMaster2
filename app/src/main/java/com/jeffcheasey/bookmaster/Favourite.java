package com.jeffcheasey.bookmaster;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;

import com.jeffcheasey.bookmaster.R;
import com.jeffcheasey.bookmaster.RV_Adapter.Info;
import com.jeffcheasey.bookmaster.RV_Adapter.Listener;
import com.jeffcheasey.bookmaster.RV_Adapter.Rec_Adap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Favourite extends AppCompatActivity implements Listener {
    TabHost tabHost;
    private List<Info> data;
    private RecyclerView rv;
    Rec_Adap adapter;
    Db databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BookMaster");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Opaque));
        setSupportActionBar(toolbar);

        tabHost = (TabHost) getParent().findViewById(android.R.id.tabhost);
        rv= (RecyclerView) findViewById(R.id.rv);
        databaseHelper = new Db(getApplicationContext());
        databaseHelper.create_db();
        (new main_parse() ).execute();
        // создаем базу данных

        data=new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        adapter = new Rec_Adap(data);
        adapter.setclick(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onRecyclerViewItemClicked(int position) {
        Intent intent=new Intent(this,More.class);
        intent.putExtra("id",data.get(position).getId());
        startActivity(intent);
    }


    private class main_parse extends AsyncTask<Void, String, List<Info>> {
        @Override
        protected List<Info> doInBackground(Void... params) {
            List<Info> data=new ArrayList<>();
            db = databaseHelper.open();
            Cursor query = db.rawQuery("SELECT * FROM book;", null);
            int i=0;

            while(query.moveToNext()){
                String a = query.getString(5);
                Bitmap bmp = null;
                try {
                    String fileName = query.getString(5);

                    bmp = BitmapFactory.decodeStream(getAssets().open(fileName));
                    bmp=Bitmap.createScaledBitmap(bmp,100,100,true);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Image Loading Error", "Error loading image for item at position " + i + ": " + e.getMessage());
                }
                data.add(i,new Info(query.getInt(0),query.getString(1),query.getString(2),query.getString(6), bmp));
                bmp=null;
            }
            query.close();
            db.close();
            return data;
        }

        protected void onPostExecute(List<Info> ts) {
            super.onPostExecute(ts);

            try {
                if (data == null)
                    data = new ArrayList<>();
                data.addAll(ts);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tabHost.getTabWidget().setVisibility(View.VISIBLE);
                return false;
            }
        });

        EditText txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);

        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchClose.setColorFilter(Color.WHITE);

        View searchplate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        searchplate.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                int index=0;
                tabHost.getTabWidget().setVisibility(View.VISIBLE);
                int [] array=new int[data.size()];
                for(int i=0;i<data.size();i++){
                    if(data.get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                        db = databaseHelper.open();
                        Cursor query1 = db.rawQuery("SELECT * FROM book where name=" + "'" + data.get(i).getName() + "';", null);
                        while(query1.moveToNext()){
                            array[index]=query1.getInt(0);
                            index+=1;
                        }
                        query1.close();
                        db.close();
                    }
                }
                System.out.println(array.length);
                Intent intent=new Intent(Favourite.this,Open_Search.class);
                intent.putExtra("array",array);
                startActivity(intent);
//                adapter = new Rec_Adap(data2);
//                rv.setAdapter(adapter);
//                System.out.println("fuck");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tabHost.getTabWidget().setVisibility(View.INVISIBLE);
                return false;
            }
        });
        return true;
    }

}

