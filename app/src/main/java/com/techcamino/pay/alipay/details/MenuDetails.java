package com.techcamino.pay.alipay.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class MenuDetails implements Serializable {

    @SerializedName("menuName")
    private @Getter@Setter String menuName;
    @SerializedName("menuColor")
    private @Getter@Setter int color;

    public MenuDetails(String name, int color) {
        this.menuName = name;
        this.color = color;
    }
}
