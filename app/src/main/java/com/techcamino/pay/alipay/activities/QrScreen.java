package com.techcamino.pay.alipay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.details.PrecreateDetails;
import com.techcamino.pay.alipay.details.QueryDetails;
import com.techcamino.pay.alipay.services.VolleySingleton;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class QrScreen extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private CardView buttonStatus;
    private HashMap<String,String> transactionPay;
    private RequestQueue mRequestQueue;
    private String url = "http://192.168.0.106:9000/api/query";
    private PrecreateDetails precreateDetails;
    private ImageView qrImage;
    private ImageLoader imageLoader;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_screen);

        dialog = Util.getProgressDialog(this, Constants.PLEASE_WAIT);
        precreateDetails = new PrecreateDetails();

        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        transactionPay = new HashMap<>();

        buttonStatus = findViewById(R.id.buttonStatus);
        qrImage = findViewById(R.id.qr_image);
        buttonStatus.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().hasExtra(Constants.PRECREATE_DETAIL)) {
            precreateDetails = (PrecreateDetails) getIntent().getSerializableExtra(Constants.PRECREATE_DETAIL);

            imageLoader.get(precreateDetails.getBigPicUrl(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    qrImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    qrImage.setImageResource(R.mipmap.ic_launcher);
                }
            });
        }

    }

    private void postQuery(){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("serviceAddress", "https://intlmapi.alipay.com/gateway.do");
            jsonBody.put("service", "alipay.acquire.overseas.query");
            jsonBody.put("partner", "2088621935381570");
            jsonBody.put("inputCharset", "UTF-8");
            jsonBody.put("signType", "RSA2");
            jsonBody.put("partnerTransId", "99003911198989");

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
                        QueryDetails queryDetails=gson.fromJson(jsonObject.getJSONObject("alipay")
                                .getJSONObject("response")
                                .getJSONObject("alipay")
                                .toString(), QueryDetails.class);

                        if(!queryDetails.getResultCode().equalsIgnoreCase(Constants.FAIL)) {
                            transactionPay.put(Constants.DIALOG_MESSAGE,queryDetails.getResultCode());
                            showBottomSheet(transactionPay);
                        }else{
                            transactionPay.put(Constants.DIALOG_MESSAGE,queryDetails.getResultCode());
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
            case R.id.buttonStatus:
                dialog.show();
                postQuery();
                break;
            default:
                break;
        }
    }
}
