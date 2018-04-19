package com.zx.zsmarketmobile.entity;

/**
 * Created by Xiangb on 2018/4/19.
 * 功能：
 */

public class SearchFilterEntity {

    private String name = "";
    private int type = 0;//0输入  1选择
    private String value = "";
    private String[] spinnerData;
    private int selectPositoin = 0;

    public SearchFilterEntity(String name) {
        this.name = name;
        this.type = 0;
    }

    public SearchFilterEntity(String name, String[] spinnerData) {
        this.name = name;
        this.spinnerData = spinnerData;
        this.type = 1;
    }

    public int getSelectPositoin() {
        return selectPositoin;
    }

    public void setSelectPositoin(int selectPositoin) {
        this.selectPositoin = selectPositoin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getSpinnerData() {
        return spinnerData;
    }

    public void setSpinnerData(String[] spinnerData) {
        this.spinnerData = spinnerData;
    }
}
