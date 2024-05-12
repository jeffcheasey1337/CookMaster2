package com.jeffcheasey.bookmaster;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffcheasey.bookmaster.resourcedb.resourcedb;

import java.io.IOException;

public class More extends AppCompatActivity {
    TextView name;

    TextView ingr;
    TextView dur;
    TextView inst;
    ImageView img;
    ImageButton imgbtn;
    int id;
    resourcedb db1;
    Db databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        db1 = new resourcedb(this);
        Cursor cursor=db1.check(id);
        imgbtn= (ImageButton) findViewById(R.id.imgbtn);
        if(cursor!=null){
            imgbtn.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            imgbtn.setImageResource(android.R.drawable.btn_star);
        }
        name= (TextView) findViewById(R.id.name);

        ingr= (TextView) findViewById(R.id.ingredient);
        dur= (TextView) findViewById(R.id.dur);
        inst= (TextView) findViewById(R.id.instr);
        img= (ImageView) findViewById(R.id.img);
        databaseHelper = new Db(getApplicationContext());
        databaseHelper = new Db(this);
        try {
            fillactivity();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillactivity() throws IOException {
        db = databaseHelper.open();
        Cursor query = db.rawQuery("SELECT * FROM book where id="+id+";", null);
        while (query.moveToNext()){
            name.setTypeface(null,Typeface.BOLD);
            name.setText(query.getString(1));

            dur.setText(Html.fromHtml("<b>Время:</b> " +query.getString(2)+" дней"));


            ingr.setText(Html.fromHtml("<b>Жанр:</b>\n"+query.getString(4)));

            inst.setText(Html.fromHtml("<b>Описаниеы:</b>\n"+query.getString(6)));

            String fileName = query.getString(5);

            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(fileName));
            img.setImageBitmap(bmp);
        }
    }

    public void Clicked(View view) {

       Cursor cursor=db1.check(id);
    if(cursor!=null){
        db1.delet(Integer.toString(id));
        imgbtn.setImageResource(android.R.drawable.btn_star);
    }
        else{
        db1.insertdata(id);
        imgbtn.setImageResource(android.R.drawable.btn_star_big_on);
    }
    }
}
