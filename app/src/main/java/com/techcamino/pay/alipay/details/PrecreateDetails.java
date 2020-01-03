package com.techcamino.pay.alipay.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PrecreateDetails implements Serializable {

    @SerializedName("out_trade_no")
    private @Getter@Setter String outTradeNo;
    @SerializedName("display_message")
    private @Getter@Setter String displayMessage;
    @SerializedName("result_code")
    private @Getter@Setter String resultCode;
    @SerializedName("detail_error_code")
    private @Getter@Setter String detailErrorCode;
    @SerializedName("detail_error_des")
    private @Getter@Setter String detailErrorDes;
    @SerializedName("big_pic_url")
    private @Getter@Setter String bigPicUrl;
    @SerializedName("pic_url")
    private @Getter@Setter String picUrl;
    @SerializedName("qr_code")
    private @Getter@Setter String qrCode;
    @SerializedName("small_pic_url")
    private @Getter@Setter String smallPicUrl;
    @SerializedName("voucher_type")
    private @Getter@Setter String voucherType;

}
