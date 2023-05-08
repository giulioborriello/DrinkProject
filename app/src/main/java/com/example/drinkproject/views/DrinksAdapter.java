    package com.example.drinkproject.views;

    import android.content.Context;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Filter;
    import android.widget.Filterable;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.drinkproject.R;

    import java.util.ArrayList;
    import java.util.List;

    import controller.Controller;
    import model.Drink;

    public class DrinksAdapter extends RecyclerView.Adapter<DrinksHolder> implements Filterable {
        Context context;
        private List<Drink> drinks;
        private final List<Drink> filteredDrinks;
        private final LayoutInflater inflater;
        private Filter filter;
        Controller controller = Controller.getInstance();


        public DrinksAdapter(Context context, List<Drink> drinks) {
            this.context = context;
            this.drinks = drinks;
            this.filteredDrinks = new ArrayList<>(drinks);
            inflater = LayoutInflater.from(context);
            this.filter = getFilter();
        }


        @NonNull
        @Override
        public DrinksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.drink_view,parent,false);
            return new DrinksHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull DrinksHolder holder, int position) {
            String name = filteredDrinks.get(position).getNome();
            String description = filteredDrinks.get(position).getDescrizione();

            Double aDouble = filteredDrinks.get(position).getPrezzo();
            String price = aDouble.toString();

            //Bitmap bitmap = BitmapFactory.decodeByteArray(drinks.get(position).getImmagine(), 0, drinks.get(position).getImmagine().length);

            holder.nome.setText(name);
            holder.descrizione.setText(description);
            holder.prezzo.setText(price);
            holder.immagine.setImageResource(R.drawable.spritz);
            holder.id = filteredDrinks.get(position).getId();
            //holder.drinkQuantity.setText(controller.getQuantitàOrdinata(filteredDrinks.get(position).getId()));

            holder.aggiungiUnDrink.setOnClickListener(v -> {
                if(holder.quantita.getText().toString().equals("")) {
                    holder.quantita.setText("1");
                } else {
                    int quantity = Integer.parseInt(holder.quantita.getText().toString());
                    quantity++;
                    holder.quantita.setText(String.valueOf(quantity));
                }
            });

            holder.rimuoviUnDrink.setOnClickListener(v -> {
                if(!holder.quantita.getText().toString().equals("")) {
                    int quantity = Integer.parseInt(holder.quantita.getText().toString());
                    if (quantity > 0) {
                        quantity--;
                        holder.quantita.setText(String.valueOf(quantity));
                    }
                }
            });

            holder.quantita.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if(s!=null && !s.toString().equals(""))
                        controller.updateDrink(filteredDrinks.get(position).getId(), Integer.parseInt(s.toString()));
                }
            });
        }


        public interface OnClickListener {
            void onClick(int position, Drink drink);
        }


        @Override
        public int getItemCount() {
            return filteredDrinks.size();
        }


        public Filter getCategoriesFilter(){
            return (new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String filterString = constraint.toString().toLowerCase().trim();
                    List<Drink> filteredList = new ArrayList<>();

                    if (filterString.equals("all")) {
                        filteredList.addAll(drinks);
                    } else {
                        filteredList.addAll(controller.FiltraDrinkPerCategoria(filterString));
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredDrinks.clear();
                    filteredDrinks.addAll((List<Drink>) results.values);

                    notifyDataSetChanged();
                }
            });
        }


        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String filterString = constraint.toString().toLowerCase().trim();
                    List<Drink> filteredList = new ArrayList<>();

                    if (filterString.isEmpty()) {
                        filteredList.addAll(drinks);
                    } else {
                        filteredList.addAll(controller.cercaDrink(filterString));
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredDrinks.clear();
                    filteredDrinks.addAll((List<Drink>) results.values);

                    notifyDataSetChanged();
                }
            };
        }

    }
