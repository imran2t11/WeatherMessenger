// imran Robiul
package com.example.hasibuzzaman.weathermessenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //

    ImageView windIcon,humidityIcon,visibilityIcon,pressureIcon,uvIcon,sunIcon;
    TextView descriptionTextView,windDetails,windInfo,humidityInfo,humidityDetails,
            dateTodayTextView,visibilityDetails,visibilityInfo,pressureInfo,pressureDetails,cityName;
    TextView sunInfo,sunDetails,uvInfo,uvDetails,lastUpdateText;
    WeatherServiceApi weatherServiceApi;

    Calendar ca ;

    boolean click=false;
    WeatherResponse weatherResponse ;  //Contains the Whole object that comes from the API
    List<WeatherResponse.Weather> weathers;  // Arraylist for Weater class object
    WeatherResponse.Main mainClass  ;  // class Main
    WeatherResponse.Wind windClass  ;  // class Wind
    WeatherResponse.Sys SunsetSunriseCountry  ;  // class Sys
    WeatherResponse.Clouds clouds  ;  // class Clouds
    WeatherResponse.Coord coord  ;  // class coord

    int temparature ;
    Integer humidity;
    Double pressure,windSpeed,lat,lon;
    int tempMax, tempMin;
    Integer sunset,sunrise,cloudPercentage;
    Integer lastUpdateTime;
    String country=null;
     Myclass myclass;
    //


    private AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> list;
    ImageView weatherIcon,minTempIcon,maxTempIcon;
    TextView temperatureText,minTempText,maxTempText,sunsettv,sunrisetv,cloudstv,latTv,lonTv;
    String selectedCity="dhaka";
    boolean celcius=false;
    Button celOrFerScale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);*/
        ca= Calendar.getInstance();
        initilizereference();




        networkLibraryPopulate();
        loadDatas();




        // End of Thread



        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.autocompletetextView);
        //autoCompleteTextView.setText("Dhaka");
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
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this);




        myclass=new Myclass();
        myclass.execute();


    }

    private void loadDatas() {


        String sy= "data/2.5/weather?q="+selectedCity+",bd&appid=e72eab0d4bf4ebaa03b4ed3f02680aa4";
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
                windSpeed =  windClass.getSpeed();
                pressure=  mainClass.getPressure();
                humidity = mainClass.getHumidity();
                tempMax = (int)(mainClass.getTempMax()-273.15);
                tempMin = (int)(mainClass.getTempMin()-273.15);
                SunsetSunriseCountry = weatherResponse.getSys();
                sunrise = SunsetSunriseCountry.getSunrise();
                sunset = SunsetSunriseCountry.getSunset();
                lastUpdateTime=weatherResponse.getDt();

                clouds =weatherResponse.getClouds();
                cloudPercentage = clouds.getAll();

                coord=weatherResponse.getCoord();
                 lat = coord.getLat();
                 lon = coord.getLon();


                long timestamp = (Long.parseLong(String.valueOf(sunrise)) * 1000);

                SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
                // SimpleDateFormat sdf = new SimpleDateFormat("E, hh:mm:ss");
                 sdf.setTimeZone(TimeZone.getTimeZone("GMT+06"));
                String date = sdf.format(timestamp);
                Log.e("Sunrise : ", ""+date);

                sunrisetv.setText(date+"/");

                long timestamp1 = (Long.parseLong(String.valueOf(sunset)) * 1000);
                SimpleDateFormat sdfy = new SimpleDateFormat("h:mm");
                // SimpleDateFormat sdf = new SimpleDateFormat("E, hh:mm:ss");
                sdfy.setTimeZone(TimeZone.getTimeZone("GMT+06"));
                String date1 = sdfy.format(timestamp1);

                sunsettv.setText(date1+"");





                //

                //Log.e("PD DEbug", weathers.get(0).getId()+" , "+ weathers.get(0).getMain() + ", "+ weathers.get(0).getDescription() );
                Log.e("Temparature : "," "+ temparature);
                Log.e("CitynAME : "," "+ Cityname+".................");

                dateTodayTextView.setText(ca.get(Calendar.DAY_OF_MONTH)+"/"+(ca.get(Calendar.MONTH)+1)+"/"+ca.get(Calendar.YEAR));
                temperatureText.setText(temparature+"\u2103");



                descriptionTextView.setText(weathers.get(0).getDescription());
                latTv.setText(lat+"°/");
                lonTv.setText(lon+"°");


                windInfo.setText(windSpeed+" mph");
                humidityInfo.setText(humidity+"%");
                pressureInfo.setText(pressure+"kpa");
                cloudstv.setText(cloudPercentage+"%");

                Log.e("WindSpeed : ", ""+windSpeed+" mph");
                Log.e("humidity : ", ""+humidity+"%");
                Log.e("pressure : ", ""+pressure+" kpa");



                String iconString=weathers.get(0).getIcon().toString();
                String url="http://openweathermap.org/img/w/" + iconString + ".png";
               /* Picasso.with(MainActivity.this)
                        .load(url)
                        .into(weatherIcon);*/



                final Handler handler =new Handler(getMainLooper());
                new Thread()
                {
                    @Override
                    public void run() {
                        while(true)
                        {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Log.e("Inside Time: ", "Time update");



                                    long timestamp = (Long.parseLong(String.valueOf(lastUpdateTime)) * 1000);
                                    SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm:ss");
                                    time_formatter.setTimeZone(TimeZone.getTimeZone("GMT+06"));
                                    String last_update_time_str = time_formatter.format(timestamp);
                                    String current_time_str = time_formatter.format(System.currentTimeMillis());

                                    Log.e("lastUpdate : ", ""+last_update_time_str);

                                    SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                                    Date date1 = null;
                                    Date date2= null;
                                    try {
                                        date1 = format.parse(last_update_time_str);
                                        date2= format.parse(current_time_str);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    long diff = date2.getTime() - date1.getTime();

                                    // long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    // long diffHours = diff / (60 * 60 * 1000) % 24;
                                    // long diffDays = diff / (24 * 60 * 60 * 1000);

                         /*  *//**//* Log.e("dayes : " , diffDays+"" );
                            //System.out.print(diffHours + " hours, ");
                            Log.e("minutes : ",diffMinutes +"");
                            Log.e(" seconds :", diffSeconds + "");*//**//*
                            // if()*/


                                    if(diffMinutes>1)
                                    {

                                        lastUpdateText.setText(""+ diffMinutes+" min ago");
                                    }
                                    else
                                    {
                                        lastUpdateText.setText(" "+ diffMinutes+" min ago");

                                    }
                                }
                            });

                            try {
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.start();

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check Youre Network Connection.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initilizereference() {
        //weatherIcon=(ImageView) findViewById(R.id.weatherIcon);
        temperatureText= (TextView) findViewById(R.id.temperatureText);

        cityName= (TextView) findViewById(R.id.cityNametv);
        sunrisetv = (TextView) findViewById(R.id.sunrisetv);
        sunsettv= (TextView) findViewById(R.id.sunsetTv);
        cloudstv= (TextView) findViewById(R.id.cloudstv);
        latTv= (TextView) findViewById(R.id.latTv);
        lonTv =(TextView) findViewById(R.id.lonTv);

        descriptionTextView= (TextView) findViewById(R.id.descriptionTextView);
        windInfo= (TextView) findViewById(R.id.windInfo);
        windDetails= (TextView) findViewById(R.id.windDetails);
        humidityInfo= (TextView) findViewById(R.id.humidityInfo);
        humidityDetails= (TextView) findViewById(R.id.humidityDetails);
        visibilityDetails= (TextView) findViewById(R.id.visibilityDetails);

        pressureInfo= (TextView) findViewById(R.id.pressureInfo);
        pressureDetails= (TextView) findViewById(R.id.pressureDetails);
        //sunInfo= (TextView) findViewById(R.id.sunInfo);
        sunDetails= (TextView) findViewById(R.id.sunDetails);
        uvDetails= (TextView) findViewById(R.id.uvDetails);
        dateTodayTextView= (TextView) findViewById(R.id.dateTodayTextView);


        windIcon= (ImageView) findViewById(R.id.windIcon);
        humidityIcon= (ImageView) findViewById(R.id.humidityIcon);
        visibilityIcon= (ImageView) findViewById(R.id.visibilityIcon);
        pressureIcon= (ImageView) findViewById(R.id.pressureIcon);
        uvIcon= (ImageView) findViewById(R.id.uvIcon);
        sunIcon= (ImageView) findViewById(R.id.sunIcon);

        celOrFerScale= (Button) findViewById(R.id.celOrFerScale);

        lastUpdateText= (TextView) findViewById(R.id.lastUpdateText);


    }

    private void networkLibraryPopulate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherServiceApi = retrofit.create(WeatherServiceApi.class);


    }

    public void search(View view) {

        cityName.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.VISIBLE);

    }

    public void CelcOrFerScale(View view) {
        if(celcius==false){
            int temInFer=(int)(temparature*9/5.0)+32;
            temperatureText.setText(String.valueOf(temInFer)+"°F");
            celOrFerScale.setText("C");
            celcius=true;
        }else{
            temperatureText.setText(Integer.toString(temparature)+"°C");
            celOrFerScale.setText("F");
            celcius=false;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCity=autoCompleteTextView.getText().toString();
        autoCompleteTextView.setVisibility(View.GONE);
        autoCompleteTextView.setText("");
        cityName.setVisibility(View.VISIBLE); cityName.setText(selectedCity);
        loadDatas();
        myclass.cancel(true);
        myclass=new Myclass();
        myclass.execute();

    }



    class Myclass extends AsyncTask
    {
        @Override
        protected void onProgressUpdate(Object[] values) {
            Log.e("Asynctask : "," intializing asynctask");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            while(!click)
            {



                if (isCancelled())
                {

                    break; // don't forget to terminate this method
                }
                else
                {
                    publishProgress();
                }

                try {
                    Thread.sleep(1000*60*10);
                } catch (InterruptedException e) {

                }

                loadDatas();


            }

            return null;
        }
    }

}
