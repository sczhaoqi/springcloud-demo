package com.zq.springcloud.application.beans;

/***
 * @author 01367477
 * pcd数据类型
 */
public class PcdData {
    private String code;
    private String province;
    private String city;
    private String district;
    private Double latitude;
    private Double longitude;
    private String details;

    public PcdData() {
    }

    public PcdData(String code, String province, String city, String district, Double latitude, Double longitude, String details) {
        this.code = code;
        this.province = province;
        this.city = city;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.details = details;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return super.toString();
    }
}

