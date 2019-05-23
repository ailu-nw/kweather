package android.kweather.com.kweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019-05-22 14:54
 * Description :
 * Comment: //TODO
 */
public class Now {
@SerializedName("tmp")
    public String temperature;
@SerializedName("cond")
    public More more;
public class More{
    @SerializedName("txt")
    public String info;
}
}
