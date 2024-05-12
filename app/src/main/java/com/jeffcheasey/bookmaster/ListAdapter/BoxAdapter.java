package com.jeffcheasey.bookmaster.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.jeffcheasey.bookmaster.R;

import java.util.ArrayList;



public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Items> objects;

    public BoxAdapter(Context context, ArrayList<Items> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_list_view, parent, false);
        }
        Items p = getItems(position);
        ((TextView)view.findViewById(R.id.label)).setText(p.name);
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка;
        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.checkbox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.box);
        return view;
    }
    Items getItems(int position) {
        return ((Items) getItem(position));
    }

    public ArrayList<Items> getBox() {
        ArrayList<Items> box = new ArrayList<Items>();
        for (Items p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getItems((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}

