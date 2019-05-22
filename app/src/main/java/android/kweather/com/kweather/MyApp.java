package android.kweather.com.kweather;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2019-05-22 12:34
 * Description :
 * Comment: //TODO
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
