package com.example.hasibuzzaman.weathermessenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {
    //


    ImageView windIcon,humidityIcon,visibilityIcon,pressureIcon,uvIcon,sunIcon;
    TextView windDetails,windInfo,humidityInfo,humidityDetails,visibilityDetails,visibilityInfo,pressureInfo,pressureDetails;
    TextView sunInfo,sunDetails,uvInfo,uvDetails;
    WeatherServiceApi weatherServiceApi;

    WeatherResponse weatherResponse ;  //Contains the Whole object that comes from the API
    List<WeatherResponse.Weather> weathers;  // Arraylist for Weater class object
    WeatherResponse.Main mainClass  ;  // class Main
    WeatherResponse.Wind windClass  ;  // class Main
    WeatherResponse.Sys SunsetSunriseCountry  ;  // class Main

    int temparature ;
    Integer humidity;
    Double pressure, tempMax, tempMin,windSpeed;
    Integer sunset,sunrise;
    String country=null;
    //


    private AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> list;
    ImageView weatherIcon,minTempIcon,maxTempIcon;
    TextView temperatureText,minTempText,maxTempText;
    String selectedCity;
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
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCity=autoCompleteTextView.getText().toString();
                temperatureText.setText(selectedCity);
                loadDatas();
            }
        });



    }

    private void loadDatas() {
        String sy= "data/2.5/weather?q="+selectedCity+"&appid=e72eab0d4bf4ebaa03b4ed3f02680aa4";
        Call<WeatherResponse> weatherResponseCall=weatherServiceApi.getWeather(sy);

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                //  All References
                weatherResponse = response.body();  // getting the Whole object from the API
                weathers = weatherResponse.getWeather();  // Arraylist of Weather class object
                // some test printing
                mainClass= weatherResponse.getMain();  // acheiving the Main class
                temparature = (int)(mainClass.getTemp()-273.15);  // Acheving the temparature in Celcious
                String Cityname = weatherResponse.getName();
                windClass = weatherResponse.getWind();  // getting the wind class reference
                windSpeed = windClass.getSpeed();
                pressure=  mainClass.getPressure();
                humidity = mainClass.getHumidity();
                tempMax = mainClass.getTempMax();
                tempMin = mainClass.getTempMin();
                SunsetSunriseCountry = weatherResponse.getSys();
                sunrise = SunsetSunriseCountry.getSunrise();
                 sunset = SunsetSunriseCountry.getSunset();

                long timestamp = (Long.parseLong(String.valueOf(sunset)) * 1000);

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a",Locale.getDefault());
               // SimpleDateFormat sdf = new SimpleDateFormat("E, hh:mm:ss");
              // sdf.setTimeZone(TimeZone.getTimeZone(""));
                String date = sdf.format(timestamp);
                Log.e("Sunrise : ", ""+date);











                //



                Log.e("PD DEbug", weathers.get(0).getId()+" , "+ weathers.get(0).getMain() + ", "+ weathers.get(0).getDescription() );
                Log.e("Temparature : "," "+ temparature);
                Log.e("CitynAME : "," "+ Cityname);

                initilizereference();
                minTempIcon.setImageResource(R.drawable.downward);
                maxTempIcon.setImageResource(R.drawable.upward);
                minTempText.setText("70°");
                maxTempText.setText("90°");

                String iconString=weathers.get(0).getIcon().toString();
                String url="http://openweathermap.org/img/w/" + iconString + ".png";
                Picasso.with(MainActivity.this)
                        .load(url)
                        .into(weatherIcon);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });
    }

    private void initilizereference() {
        weatherIcon=(ImageView) findViewById(R.id.weatherIcon);
        temperatureText= (TextView) findViewById(R.id.temperatureText);
        maxTempIcon= (ImageView) findViewById(R.id.maxTempIcon);
        minTempIcon= (ImageView) findViewById(R.id.minTempIcon);
        maxTempText= (TextView) findViewById(R.id.maxTempText);
        minTempText= (TextView) findViewById(R.id.minTempText);
        windDetails= (TextView) findViewById(R.id.windDetails);
        humidityInfo= (TextView) findViewById(R.id.humidityInfo);
        windInfo= (TextView) findViewById(R.id.windInfo);
        humidityDetails= (TextView) findViewById(R.id.humidityDetails);
        visibilityDetails= (TextView) findViewById(R.id.visibilityDetails);
        visibilityInfo= (TextView) findViewById(R.id.visibilityInfo);
        pressureInfo= (TextView) findViewById(R.id.pressureInfo);
        pressureDetails= (TextView) findViewById(R.id.pressureDetails);
        sunInfo= (TextView) findViewById(R.id.sunInfo);
        sunDetails= (TextView) findViewById(R.id.sunDetails);
        uvInfo= (TextView) findViewById(R.id.uvInfo);
        uvDetails= (TextView) findViewById(R.id.uvDetails);

        windIcon= (ImageView) findViewById(R.id.windIcon);
        humidityIcon= (ImageView) findViewById(R.id.humidityIcon);
        visibilityIcon= (ImageView) findViewById(R.id.visibilityIcon);
        pressureIcon= (ImageView) findViewById(R.id.pressureIcon);
        uvIcon= (ImageView) findViewById(R.id.uvIcon);
        sunIcon= (ImageView) findViewById(R.id.sunIcon);








    }

    private void networkLibraryPopulate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherServiceApi = retrofit.create(WeatherServiceApi.class);


    }
}
