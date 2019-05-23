package android.kweather.com.kweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019-05-22 14:54
 * Description :
 * Comment: //TODO
 */
public class AQI {
public AQICity city;
public class AQICity{
    public String aqi;
    public String pm25;
}
}
