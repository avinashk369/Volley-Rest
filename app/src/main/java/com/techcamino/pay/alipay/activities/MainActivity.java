package com.techcamino.pay.alipay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.details.PrecreateDetails;
import com.techcamino.pay.alipay.services.VolleySingleton;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private CardView buttonTransaction;
    private RequestQueue mRequestQueue;
    private String url = "http://192.168.0.106:9000/api/precreate";
    private ProgressDialog dialog;
    private HashMap<String,String> transactionPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionPay = new HashMap<>();
        dialog = Util.getProgressDialog(this, Constants.PLEASE_WAIT);
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        buttonTransaction = findViewById(R.id.buttonTransaction);
        buttonTransaction.setOnClickListener(this);

    }

    private void postPrecreate(){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("serviceAddress", "https://intlmapi.alipay.com/gateway.do?");
            jsonBody.put("service", "alipay.acquire.precreate");
            jsonBody.put("partner", "2088621935381570");
            jsonBody.put("inputCharset", "UTF-8");
            jsonBody.put("signType", "RSA2");
            jsonBody.put("productCode", "OVERSEAS_MBARCODE_PAY");
            jsonBody.put("currency", "USD");
            jsonBody.put("transCurrency", "USD");
            jsonBody.put("outTradeNo", "99003912298989");
            jsonBody.put("subject", "Spunk Merchant");
            jsonBody.put("totalFee", "0.01");
            jsonBody.put("sellerId", "2088621935381570");
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
                    JSONObject jsonObject = xmlToJson.toJson();

                    if(dialog.isShowing())
                        dialog.dismiss();

                    try {
                        Gson gson = new Gson();
                        PrecreateDetails precreateDetails=gson.fromJson(jsonObject.getJSONObject("alipay")
                                .getJSONObject("response")
                                .getJSONObject("alipay")
                                .toString(), PrecreateDetails.class);
                        //Toast.makeText(MainActivity.this, precreateDetails.getBigPicUrl(), Toast.LENGTH_SHORT).show();
                        if(!precreateDetails.getResultCode().equalsIgnoreCase(Constants.FAIL)) {
                            Intent precreate = new Intent(MainActivity.this, QrScreen.class);
                            precreate.putExtra(Constants.PRECREATE_DETAIL, precreateDetails);
                            startActivity(precreate);
                        }else{
                            transactionPay.put(Constants.DIALOG_MESSAGE,precreateDetails.getResultCode());
                            showBottomSheet(transactionPay);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    if(dialog.isShowing())
                        dialog.dismiss();
                }
            }) {



                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            mRequestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showBottomSheet(HashMap<String, String> messageMap){
        View dialogView = getLayoutInflater().inflate(R.layout.alert_bottom, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.show();
        TextView productMessage = dialog.findViewById(R.id.product_message);
        productMessage.setText(messageMap.get(Constants.DIALOG_MESSAGE));
        RelativeLayout productImage = dialog.findViewById(R.id.bottom_section);
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonTransaction:
                dialog.show();
                postPrecreate();
                break;
            default:
                break;
        }
    }
}
