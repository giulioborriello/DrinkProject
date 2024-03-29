    package com.example.drinkproject.views;

    import android.content.Context;
    import android.preference.PreferenceManager;
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
    import com.example.drinkproject.activities.ImpostazioniActivity;
    import com.example.drinkproject.classiDiSupporto.CaricatoreImmaginiLista;
    import com.example.drinkproject.classiDiSupporto.ImpostazioniAttributi;

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

            if(PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)){
                holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                holder.nome.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.descrizione.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.prezzo.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.quantita.setTextColor(context.getResources().getColor(android.R.color.black));
            }
            else {
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.backgroud_recyclerview_item));
                holder.nome.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.descrizione.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.prezzo.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.quantita.setTextColor(context.getResources().getColor(android.R.color.white));
            }



            holder.id = filteredDrinks.get(position).getId();
            String id = filteredDrinks.get(position).getId();

            settaNomeDescrizionePrezzo(holder, name, description, price);
            caricaImmagini(holder);

            String quantitaOrdinata = controller.getQuantitaOrdinata(id);
            holder.quantita.setText(quantitaOrdinata);

            if(ImpostazioniAttributi.modalitaColoriAltoContrastoAttivata){
                holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                holder.nome.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.descrizione.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.prezzo.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.quantita.setTextColor(context.getResources().getColor(android.R.color.black));
            }

            holder.aggiungiUnDrink.setOnClickListener(v -> {
                String quantitaInput = holder.quantita.getText().toString();
                if(quantitaInput.equals("")) {
                    if (position >= 0 && position < filteredDrinks.size()) {
                        controller.updateDrink(filteredDrinks.get(position).getId(), 1);
                        holder.quantita.setText(String.valueOf(1));
                    }
                }
                else if (quantitaInput.equals("30")) {
                    if (position >= 0 && position < filteredDrinks.size()) {
                        controller.updateDrink(filteredDrinks.get(position).getId(), 0);
                        holder.quantita.setText(String.valueOf(0));
                    }
                }
                else {
                    int quantity = Integer.parseInt(quantitaInput);
                    quantity++;
                    if (position >= 0 && position < filteredDrinks.size()) {
                        controller.updateDrink(filteredDrinks.get(position).getId(), quantity);
                        holder.quantita.setText(String.valueOf(quantity));
                    }
                }
            });

            holder.rimuoviUnDrink.setOnClickListener(v -> {
                String quantitaInput = holder.quantita.getText().toString();
                if(!quantitaInput.equals("")) {
                    int quantity = Integer.parseInt(quantitaInput);
                    if (quantity > 0) {
                        quantity--;
                        if (position >= 0 && position < filteredDrinks.size()) {
                            controller.updateDrink(filteredDrinks.get(position).getId(), quantity);
                            holder.quantita.setText(String.valueOf(quantity));
                        }
                    }
                    else {
                        if (position >= 0 && position < filteredDrinks.size()) {
                            controller.updateDrink(filteredDrinks.get(position).getId(), 30);
                            holder.quantita.setText(String.valueOf(30));
                        }
                    }
                }
            });

            holder.quantita.setFocusable(false);
            holder.quantita.setEnabled(false);
        }

        private static void settaNomeDescrizionePrezzo(@NonNull DrinksHolder holder, String name, String description, String price) {
            holder.nome.setText(name);
            holder.descrizione.setText(description);
            holder.prezzo.setText(price);
        }


        private void caricaImmagini(@NonNull DrinksHolder holder) {
            try {
                CaricatoreImmaginiLista caricatoreImmagini = new CaricatoreImmaginiLista(controller, context, holder);
                caricatoreImmagini.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                filteredList.addAll(controller.getSuggerimentiInbaseAiTuoiGusti());
            }

            else if (filterString.equalsIgnoreCase("Consigliati in base alle tue tendenze")) {
                filteredList.addAll(controller.getSuggerimentiInBaseAlleTendenze());
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
