package android.kweather.com.kweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.SortedMap;

/**
 * Created by Administrator on 2019-05-22 14:54
 * Description :
 * Comment: //TODO
 */
public class Suggestion {
@SerializedName("comf")
    public Comfort comfort;
@SerializedName("cw")
    public CarWash carWash;
public Sport sort;
public class Comfort{
    @SerializedName("txt")
    public String info;
}
public class CarWash{
    @SerializedName("txt")
    public String info;

}
public class Sport{
    @SerializedName("txt")
    public String info;
}
}
