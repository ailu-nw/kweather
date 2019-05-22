package android.kweather.com.kweather.db;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

/**
 * Created by Administrator on 2019-05-22 11:23
 * Description :
 * Comment:
 * /
 **/
public class Province extends DataSupport {
    private int id;
    private int provinceCode;
    private String provinceName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
