package com.example.helloworld;

import com.example.helloworld.Model.Country;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/v1/countries")
    Call<List<Country>> getAllCountries();

}
