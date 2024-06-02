package com.example.helloworld;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Activity.DetailActivity;
import com.example.helloworld.Model.Country;
import com.example.helloworld.Model.User;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {

    private Context context;
    private List<Country> countryList;
    private List<Country> countryListFull;
    private UserPreferences userPreferences;

    public CountryAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
        this.countryListFull = new ArrayList<>();
        this.userPreferences = new UserPreferences(context);

        for (Country country : this.countryList) {
            country.setLocked(!userPreferences.isCountryUnlocked(country.getId()));
        }

        this.countryListFull.addAll(this.countryList);
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.tvCountryName.setText(country.getName());

        if (country.isLocked()) {
            holder.lockedImage.setVisibility(View.VISIBLE);
        } else {
            holder.lockedImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (country.isLocked()) {
                    showUnlockDialog(country);
                } else {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", country.getId());
                    intent.putExtra("name", country.getName());
                    intent.putExtra("population", country.getPopulation());
                    intent.putExtra("land_area", country.getLandArea());
                    intent.putExtra("density", country.getDensity());
                    intent.putExtra("capital", country.getCapital());
                    intent.putExtra("currency", country.getCurrency());
                    intent.putExtra("flag", country.getFlag());
                    context.startActivity(intent);
                }
            }
        });
    }

    private void showUnlockDialog(Country country) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.open_detail_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvDialog = dialog.findViewById(R.id.tv_dialog);
        TextView noBtn = dialog.findViewById(R.id.no_btn);
        TextView yesBtn = dialog.findViewById(R.id.yes_btn);

        tvDialog.setText("This country detail is Locked.\n Open it with 1 Coin");

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userCoins = userPreferences.getUser().getCoin();
                if (userCoins >= 1) {
                    int updatedCoins = userCoins - 1;
                    User user = userPreferences.getUser();
                    user.setCoin(updatedCoins);
                    userPreferences.saveUser(user);

                    userPreferences.addUnlockedCountryId(country.getId());
                    country.setLocked(false);
                    notifyDataSetChanged();

                    dialog.dismiss();

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", country.getId());
                    intent.putExtra("name", country.getName());
                    intent.putExtra("population", country.getPopulation());
                    intent.putExtra("land_area", country.getLandArea());
                    intent.putExtra("density", country.getDensity());
                    intent.putExtra("capital", country.getCapital());
                    intent.putExtra("currency", country.getCurrency());
//                    intent.putExtra("flag", country.getFlag());
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, "Not enough coins to unlock", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void setFilter(List<Country> filteredList) {
        countryList.clear();
        countryList.addAll(filteredList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Country> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(countryListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Country item : countryListFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryList.clear();
                countryList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName;
        ImageView lockedImage;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tv_negara);
            lockedImage = itemView.findViewById(R.id.locked);
        }
    }
}


