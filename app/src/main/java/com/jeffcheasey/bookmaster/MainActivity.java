package com.jeffcheasey.bookmaster;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabWidget;




public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Устанавливаем обработчик необработанных исключений
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                // Здесь можно добавить код для логирования информации о сбое
                Log.e("MainActivity", "Приложение завершилось из-за необработанного исключения:", throwable);

                // Выход из приложения
                finish();
                System.exit(1);
            }
        });

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Favourite.class);
        spec = tabHost.newTabSpec("")
                .setIndicator("",res.getDrawable(R.drawable.open_book))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        intent = new Intent().setClass(this, Search.class);
        spec = tabHost.newTabSpec("Search")
                .setIndicator("", res.getDrawable(android.R.drawable.ic_menu_search))
                .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, Book.class);
        spec = tabHost
                .newTabSpec("")
                .setIndicator("",
                        res.getDrawable(R.drawable.favorite_heart_button))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
