package com.example.helloworld.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.ApiService;
import com.example.helloworld.Model.Country;
import com.example.helloworld.CountryAdapter;
import com.example.helloworld.R;
import com.example.helloworld.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<Country> countryList;
    private List<Country> countryListFull;
    private ApiService apiService;
    private SearchView searchView;
    private ProgressBar progressBar;
    private Handler handler;
    private Spinner spinnerFilterLocked;
    private String selectedFilter = "All";

    public DetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        apiService = RetrofitClient.getClient().create(ApiService.class);

        progressBar = view.findViewById(R.id.progressBar);

        spinnerFilterLocked = view.findViewById(R.id.spinner_filter_locked);
        spinnerFilterLocked.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFilter = parent.getItemAtPosition(position).toString();
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchCountryData();
            }
        }, 3000);

        return view;
    }

    private void applyFilters() {
        if (countryListFull == null) {
            return;
        }

        List<Country> filteredList = new ArrayList<>();

        switch (selectedFilter) {
            case "All":
                filteredList.addAll(countryListFull);
                break;
            case "Locked":
                for (Country country : countryListFull) {
                    if (country.isLocked()) {
                        filteredList.add(country);
                    }
                }
                break;
            case "Unlocked":
                for (Country country : countryListFull) {
                    if (!country.isLocked()) {
                        filteredList.add(country);
                    }
                }
                break;
        }

        if (countryAdapter != null) {
            countryAdapter.setFilter(filteredList);
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void fetchCountryData() {
        showProgressBar();
        Call<List<Country>> call = apiService.getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    countryList = response.body();
                    countryListFull = new ArrayList<>(countryList);
                    countryAdapter = new CountryAdapter(getActivity(), countryList);
                    recyclerView.setAdapter(countryAdapter);
                    hideProgressBar();
                } else {
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                t.printStackTrace();
                hideProgressBar();

            }
        });
    }

    private void filter(String query) {
        if (countryAdapter != null) {
            countryAdapter.getFilter().filter(query);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

