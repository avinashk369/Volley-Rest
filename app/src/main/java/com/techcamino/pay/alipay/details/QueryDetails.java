package com.techcamino.pay.alipay.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class QueryDetails implements Serializable {

    /**
     * <alipay_buyer_login_id>cnb*@alitest.*</alipay_buyer_login_id>
     *             <alipay_buyer_user_id>2088622937243084</alipay_buyer_user_id>
     *             <alipay_trans_id>2019112022001443081000035414</alipay_trans_id>
     *             <alipay_trans_status>WAIT_BUYER_PAY</alipay_trans_status>
     *             <currency>USD</currency>
     *             <exchange_rate>6.64640000</exchange_rate>
     *             <forex_total_fee>0.01</forex_total_fee>
     *             <out_trade_no>99003911198989</out_trade_no>
     *             <partner_trans_id>99003911198989</partner_trans_id>
     *             <result_code>SUCCESS</result_code>
     *             <trans_amount>0.01</trans_amount>
     *             <trans_amount_cny>0.07</trans_amount_cny>
     *             <trans_forex_rate>1</trans_forex_rate>
     */

    @SerializedName("alipay_buyer_login_id")
    private @Getter@Setter String buyerLoginId;
    @SerializedName("alipay_buyer_user_id")
    private @Getter@Setter String buyerUserId;
    @SerializedName("alipay_trans_id")
    private @Getter@Setter String transactionId;
    @SerializedName("alipay_trans_status")
    private @Getter@Setter String transactionStatus;
    @SerializedName("currency")
    private @Getter@Setter String currency;
    @SerializedName("exchange_rate")
    private @Getter@Setter String exchangeRate;
    @SerializedName("forex_total_fee")
    private @Getter@Setter String totalFee;
    @SerializedName("out_trade_no")
    private @Getter@Setter String outTradeNo;
    @SerializedName("partner_trans_id")
    private @Getter@Setter String partnerTransactionId;
    @SerializedName("result_code")
    private @Getter@Setter String resultCode;
    @SerializedName("trans_amount")
    private @Getter@Setter double transactionAmount;
    @SerializedName("trans_amount_cny")
    private @Getter@Setter double transactionAmountCny;
    @SerializedName("trans_forex_rate")
    private @Getter@Setter String forexRate;

}
