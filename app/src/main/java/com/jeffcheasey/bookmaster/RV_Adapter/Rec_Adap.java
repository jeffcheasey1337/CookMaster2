package com.jeffcheasey.bookmaster.RV_Adapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffcheasey.bookmaster.Book;
import com.jeffcheasey.bookmaster.Favourite;
import com.jeffcheasey.bookmaster.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class Rec_Adap  extends RecyclerView.Adapter<Rec_Adap.PersonViewHolder> {
   private Listener listener;
    List<Info> book;

        public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv;
            TextView name_of_book;
            TextView author;
            TextView ingr;
            ImageView photo;
            Button btn;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.card_view);
                name_of_book = (TextView) itemView.findViewById(R.id.Name);
                author = (TextView) itemView.findViewById(R.id.Comand);
                photo=(ImageView)itemView.findViewById(R.id.image);
                ingr= (TextView) itemView.findViewById(R.id.ingr);
                btn= (Button) itemView.findViewById(R.id.btn);
                btn.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (listener != null) listener.onRecyclerViewItemClicked(getAdapterPosition());
            }
        }


        public Rec_Adap(List<Info> book){
            this.book = book;
        }


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

    public  void setclick(Listener listener){
        this.listener=listener;
    }


    @Override
        public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
            personViewHolder.name_of_book.setText(book.get(i).getName());
            personViewHolder.author.setText(Html.fromHtml("<b>"+book.get(i).getDesc()+"</b> минут"));
            personViewHolder.photo.setImageBitmap(book.get(i).getPhoto_id());
            personViewHolder.ingr.setText(Html.fromHtml("<b>Ингредиенты:</b><br>"+book.get(i).getDur()));

    }

        @Override
        public int getItemCount() {
            if (book == null) {
                return 0;
            }
                return book.size();
    }
}


