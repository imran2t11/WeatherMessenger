package com.example.hasibuzzaman.weathermessenger;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    WeatherResponse weatherResponse ;  //Contains the Whole object that comes from the API
    List<WeatherResponse.Weather> weathers;  // Arraylist for Weater class object
    WeatherResponse.Main mainClass  ;  // class Main
    int temparature ;


    WeatherServiceApi weatherServiceApi;
    private AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> list;
    ImageView weatherIcon;
    TextView temperatureText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkLibraryPopulate();
        loadDatas();

        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.autocompletetextView);
        list= new ArrayList<>();

        list.add("Barguna"); list.add("Barisal"); list.add("Bhola"); list.add("Jhalokati"); list.add("Patuakhali");
        list.add("Pirojpur");list.add("Bandarban"); list.add("Brahmanbaria"); list.add("Chandpur"); list.add("Chittagong");
        list.add("Comilla");list.add("Cox's Bazar");list.add("Feni");list.add("Khagrachhari");list.add("Lakshmipur");
        list.add("Noakhal");list.add("Rangamati");list.add("Dhaka");list.add("Faridpur");
        list.add("Gazipur");list.add("Gopalganj");list.add("Jamalpur ");list.add("Kishoreganj");list.add("Madaripur");
        list.add("Manikganj");list.add("Munshiganj");list.add("Mymensingh");list.add("Narayanganj");list.add("Narsingdi");
        list.add("Netrakona");list.add("Rajbari");list.add("Shariatpur");list.add("Sherpur");list.add("Tangail");
        list.add("Bagerhat");list.add("Chuadanga");list.add("Jessore");list.add("Jhenaidah");list.add("Khulna");
        list.add("Kushtia");list.add("Magura");list.add("Meherpur");list.add("Narail");list.add("Satkhira");
        list.add("Bogra");list.add("Joypurhat");list.add("Naogaon");list.add("Natore");
        list.add("Nawabganj");list.add("Pabna");list.add("Rajshahi");list.add("Sirajganj");
        list.add("Dinajpur");list.add("Gaibandha");list.add("Kurigram");list.add("Lalmonirhat");
        list.add("Nilphamari");list.add("Panchagarh");list.add("Rangpur");list.add("Thakurgaon");
        list.add("Habiganj");list.add("Moulvibazar");list.add("Sunamganj");list.add("Sylhet");


        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,list);
        autoCompleteTextView.setAdapter(adapter);



    }

    private void loadDatas() {
        Call<WeatherResponse> weatherResponseCall=weatherServiceApi.getWeather();

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherResponse = response.body();  // getting the Whole object from the API
                 weathers = weatherResponse.getWeather();  // Arraylist of Weather class object
                // some test printing
                Log.e("PD DEbug", weathers.get(0).getId()+" , "+ weathers.get(0).getMain() + ", "+ weathers.get(0).getDescription() );
                mainClass= weatherResponse.getMain();  // acheiving the Main class
                temparature = (int)(mainClass.getTemp()-273.15);  // Acheving the temparature in Celcious
                Log.e("Temparature : "," "+ temparature);

                weatherIcon=(ImageView) findViewById(R.id.weatherIcon);  // icon imageview reference
                temperatureText= (TextView) findViewById(R.id.temperatureText);
                Log.e("mine", "onResponse: "+weathers.get(0).getIcon() );

                String iconString=weathers.get(0).getIcon().toString();
                // shwoing the icon
                String url="http://openweathermap.org/img/w/" + iconString + ".png";
                Picasso.with(MainActivity.this)
                        .load(url)
                        .into(weatherIcon);


                // getting Refernce of All necessary classes Datas




            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Data can't retreive", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkLibraryPopulate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherServiceApi = retrofit.create(WeatherServiceApi.class);


    }
}
