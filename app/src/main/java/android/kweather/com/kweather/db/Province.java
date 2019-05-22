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
    private String provinceCode;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
