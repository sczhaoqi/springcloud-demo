package com.zq.springcloud.application.beans;

/***
 * @author 01367477
 */
public class PCDDTO {
    private String province;
    private String city;
    private String district;

    public PCDDTO() {
    }

    public PCDDTO(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return province + '\'' + city + '\''+ district  ;
    }
}

