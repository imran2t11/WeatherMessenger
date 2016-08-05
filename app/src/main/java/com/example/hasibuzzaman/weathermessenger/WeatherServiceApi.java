package com.example.hasibuzzaman.weathermessenger;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hasibuzzaman on 8/5/2016.
 */
public interface WeatherServiceApi {
    @GET("data/2.5/weather?q=Dhaka,bd&appid=e72eab0d4bf4ebaa03b4ed3f02680aa4")
    Call<WeatherResponse> getWeather();
}
