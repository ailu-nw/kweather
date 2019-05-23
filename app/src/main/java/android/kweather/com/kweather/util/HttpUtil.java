package android.kweather.com.kweather.util;

import androidx.annotation.RequiresApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2019-05-22 12:40
 * Description :
 * Comment: //TODO
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
