package com.jeffcheasey.bookmaster;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jeffcheasey.bookmaster.ListAdapter.BoxAdapter;
import com.jeffcheasey.bookmaster.ListAdapter.Items;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements  SearchView.OnQueryTextListener {
    int place=0;
    Spinner spinner;
    Db databaseHelper;
    ArrayAdapter<String> adapter;
    SQLiteDatabase db;
    ArrayList<Items> products = new ArrayList<>();
    BoxAdapter boxAdapter;
    String[] data = {"10 минут", "20 минут", "30 минут", "60 минут", "180 минут"};
    SearchView searchView;
    ListView lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        databaseHelper = new Db(getApplicationContext());
        databaseHelper = new Db(this);
        //txt= (EditText) findViewById(R.id.edit);
        searchView= (SearchView) findViewById(R.id.seacrhview);
        searchView.setOnQueryTextListener(this);
        fillData();
        boxAdapter = new BoxAdapter(this, products);
        // настраиваем список
        lvMain = (ListView) findViewById(R.id.listview);
        lvMain.setAdapter(boxAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Выбор ингредиентов");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Opaque));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Book");
        // выделяем элемент
        spinner.setSelection(3);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                place=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                System.out.println("ABU ZAEBAL");
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=0;
                String a[]=new String[300];
                Intent intent=new Intent(Search.this,Float.class);
                for (Items p : boxAdapter.getBox()) {
                    {
                        if (p.isBox()) {
                            a[i] = p.getName();
                            i++;
                            System.out.println(a[i]);
                        }
                    }
                }
                //System.out.println(adapter.getItem(place).replace("минут",""));
                intent.putExtra("time",adapter.getItem(place).replace("минут",""));
                intent.putExtra("array",a);
                startActivity(intent);
            }}
        );
    //showResult();
    }

    private void fillData() {
        db = databaseHelper.open();
        int i=0;
        Cursor query = db.rawQuery("SELECT * FROM ing;", null);
         while(query.moveToNext()) {
        products.add(i,new Items(query.getString(1),false));
        i++;
         }   query.close();
             db.close();
    }

        @Override
    public boolean onQueryTextSubmit(String query) {
            int i=0;
          //  System.out.println("a");
            ArrayList<Items> prod = new ArrayList<>();
            // Click action
            //db = databaseHelper.open();
            //System.out.println(searchView.getQuery());
            //Cursor cursor = db.rawQuery("SELECT * FROM ing where name=" + "'" + searchView.getQuery() + "';", null);
            for(int ii=0;ii<products.size();ii++){
                if(products.get(ii).getName().contains(searchView.getQuery()))
                    prod.add(i,new Items(products.get(ii).getName(),false));
                i++;
            }
            boxAdapter=new BoxAdapter(this,prod);
            lvMain.setAdapter(boxAdapter);
            return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}


