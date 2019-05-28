package android.kweather.com.kweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.kweather.com.kweather.gson.Forecast;
import android.kweather.com.kweather.gson.Weather;
import android.kweather.com.kweather.util.HttpUtil;
import android.kweather.com.kweather.util.Utility;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefreshLayout;
    private String mWeatherId;

    private Button navButton;
    public DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        setContentView(R.layout.activity_weather);
        //init控件

        init();
    }

    private void init() {
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.spot_text);
        bingPicImg = findViewById(R.id.bing_pic_img);

        swipeRefreshLayout = findViewById(R.id.swipe_retresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        navButton = findViewById(R.id.nav_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if (weatherString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);

        }else{
            //无缓存是去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");

            weatherLayout.setVisibility(View.VISIBLE);
            requertWeather(mWeatherId);
            
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requertWeather(mWeatherId);
            }
        });
        String bingPic = prefs.getString("bingPic",null);
        if (bingPic != null){
            Glide.with(this).load(bingPicImg);
        }else{
            loadingPic();
        }
    
    }

    private void loadingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });

            }
        });
    }

    public void requertWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        Log.v("responseresponssponse",weatherUrl);

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败...", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText =response.body().string();
                Log.v("responseresponssponse",responseText);
                final Weather weather = Utility.handleWeatherResponse(responseText);
                Log.v("weatherweathherweather",responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather!=null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);

                            Intent intent = new Intent(WeatherActivity.this,AutoUpdateService.class);
                            startService(intent);


                        }else{
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
loadingPic();
    }

    private void showWeatherInfo(Weather weather) {
        Log.v("weather.basic.update.",weather.basic.update.updateTime);


        String cityname = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.substring(11);
        String degree = weather.now.temperature+"℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityname);
        titleUpdateTime.setText(updateTime);
        Log.v("updateTime.updateTime",updateTime);
        Log.v("weather",""+weather.forecasts.toString());

        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecasts){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = view.findViewById(R.id.data_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            Log.v("forecast.dataforecast",forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max+"℃");
            minText.setText(forecast.temperature.min+"℃");
            forecastLayout.addView(view);
        }
        if (weather.aqi!= null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);

        }
        String comfort = "舒适度:"+weather.suggestion.comfort.info;
        String carWsh = "洗车指数:"+weather.suggestion.carWash.info;
        String sport = "运动指数:"+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWsh);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
