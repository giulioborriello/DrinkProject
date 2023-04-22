package com.example.drinkproject.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

import java.util.List;

import controller.Controller;
import model.Drink;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksHolder> {
    Context context;
    private final List<Drink> drinks;
    private final LayoutInflater inflater;
    Controller controller = Controller.getInstance();


    public DrinksAdapter(Context context, List<Drink> drinks) {
        this.context = context;
        this.drinks = drinks;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public DrinksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view,parent,false);
        return new DrinksHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DrinksHolder holder, int position) {
        String name = drinks.get(position).getNome();
        String description = drinks.get(position).getDescrizione();

        Double aDouble = drinks.get(position).getPrezzo();
        String price = aDouble.toString();

        //Bitmap bitmap = BitmapFactory.decodeByteArray(drinks.get(position).getImmagine(), 0, drinks.get(position).getImmagine().length);

        holder.nameText.setText(name);
        holder.descriptionText.setText(description);
        holder.drinkPrice.setText(price);
        holder.drinkImage.setImageResource(R.drawable.spritz);
        holder.id = drinks.get(position).getId();

        holder.itemView.setOnClickListener(view -> {
            controller.addDrink(holder.id, 1);
        });
         
    }


    public interface OnClickListener {
        void onClick(int position, Drink drink);
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }


}
