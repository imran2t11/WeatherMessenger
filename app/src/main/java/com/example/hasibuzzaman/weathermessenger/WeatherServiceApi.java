package com.example.hasibuzzaman.weathermessenger;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Hasibuzzaman on 8/5/2016.
 */
public interface WeatherServiceApi {
    @GET
    Call<WeatherResponse> getWeather(@Url String url);
}
