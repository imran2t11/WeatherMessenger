package com.example.hasibuzzaman.weathermessenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    WeatherServiceApi weatherServiceApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkLibraryPopulate();
        loadDatas();
    }

    private void loadDatas() {
        Call<WeatherResponse> weatherResponseCall=weatherServiceApi.getWeather();

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                List<WeatherResponse.Weather> weathers = weatherResponse.getWeather();
                Log.e("PD DEbug", weathers.get(0).getId()+" , "+ weathers.get(0).getMain() + ", "+ weathers.get(0).getDescription() );
                WeatherResponse.Main ma= weatherResponse.getMain();
                Log.e("Temparature : "," "+ ma.getTemp());

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });
    }

    private void networkLibraryPopulate() {
     Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
             .addConverterFactory(GsonConverterFactory.create()).build();

        weatherServiceApi = retrofit.create(WeatherServiceApi.class);


    }
}
