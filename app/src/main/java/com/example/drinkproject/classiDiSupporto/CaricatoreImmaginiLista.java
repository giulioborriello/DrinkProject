package com.example.drinkproject.classiDiSupporto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.drinkproject.R;
import com.example.drinkproject.views.DrinksHolder;

import controller.Controller;

public class CaricatoreImmaginiLista extends AsyncTask<Void, Void, Drawable> {
    private Controller controller;
    private Context context;
    private DrinksHolder holder;


    public CaricatoreImmaginiLista(Controller controller, Context context, DrinksHolder holder) {
        this.controller = controller;
        this.context = context;
        this.holder = holder;
    }


    @Override
    protected Drawable doInBackground(Void... params) {
        Drawable drawable = null;
        try {
            byte[] immagineBytes = controller.getImmagineByID(holder.id);
            if (immagineBytes != null && immagineBytes.length != 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(immagineBytes, 0, immagineBytes.length);
                drawable = new BitmapDrawable(context.getResources(), bitmap);
            } else {
                drawable = context.getResources().getDrawable(R.drawable.beer_not_found);
            }
        } catch (Exception e) {
            drawable = context.getResources().getDrawable(R.drawable.beer_not_found);
        }
        return drawable;
    }


    @Override
    protected void onPostExecute(Drawable drawable) {
        holder.immagine.setImageDrawable(drawable);
    }
}

