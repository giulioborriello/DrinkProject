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
        private final LayoutInflater inflater;
        private Filter filter;
        Controller controller = Controller.getInstance();
        private List<Drink> drinks = controller.getDrinks();
        private final List<Drink> filteredDrinks = new ArrayList<>(drinks);


        public DrinksAdapter(Context context) {
            this.context = context;
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

            double aDouble = filteredDrinks.get(position).getPrezzo();
            String price = Double.toString(aDouble);

            //Bitmap bitmap = BitmapFactory.decodeByteArray(drinks.get(position).getImmagine(), 0, drinks.get(position).getImmagine().length);

            holder.nome.setText(name);
            holder.descrizione.setText(description);
            holder.prezzo.setText(price);
            holder.immagine.setImageResource(R.drawable.spritz);
            holder.id = filteredDrinks.get(position).getId();
            holder.quantita.setText(controller.getQuantitaOrdinata(filteredDrinks.get(position).getId()));

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

                    filtraInBaseAllaCategoria(filterString, filteredList);

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

        private void filtraInBaseAllaCategoria(String filterString, List<Drink> filteredList) {
            if (filterString.equalsIgnoreCase("Tutti")) {
                filteredList.addAll(drinks);
            }

            else if (filterString.equalsIgnoreCase("Consigliati in base ai tuoi gusti")) {
                filteredList.addAll(controller.getSuggeritiInbaseAiTuoiGusti());
            }

            else if (filterString.equalsIgnoreCase("Consigliati in base alle tue tendenze")) {
                filteredList.addAll(controller.getSuggeritiInBaseAlleTendenze());
            }

            else {
                filteredList.addAll(controller.FiltraDrinkPerCategoria(filterString));
            }
        }


        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String filterString = constraint.toString().toLowerCase().trim();
                    List<Drink> filteredList = new ArrayList<>();

                    filtraInBaseAllaRicerca(filterString.isEmpty(), filteredList, drinks, controller.cercaDrink(filterString));

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


        private void filtraInBaseAllaRicerca(boolean filterString, List<Drink> filteredList, List<Drink> drinks, ArrayList<Drink> controller) {
            if (filterString) {
                filteredList.addAll(drinks);
            } else {
                filteredList.addAll(controller);
            }
        }
    }
