package android.kweather.com.kweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019-05-22 14:54
 * Description :
 * Comment: //TODO
 */
public class Forecast {

public String date;

@SerializedName("tmp")
    public Temperature temperature;

@SerializedName("cond")
    public More more;

public class Temperature{
    public String max;
    public String min;
}
public class More{
    @SerializedName("txt_d")
    public String info;
}
}
