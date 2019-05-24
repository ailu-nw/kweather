package android.kweather.com.kweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019-05-22 14:54
 * Description :
 * Comment: //TODO
 */
public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
        @SerializedName("utc")
        public String updateTineC;
    }
}

