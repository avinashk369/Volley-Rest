package com.techcamino.pay.alipay.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.util.Util;
import com.techcamino.pay.alipay.widget.CardKeyboard;
import com.techcamino.pay.alipay.widget.NumberKeyboard;

public class PurchasePin extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private String amount;
    private TextView amountText;
    private EditText editText;
    private CardView clear,pay;
    private CardKeyboard keyboard;
    private AlertDialog dialog,progressDialog;
    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_pin);
        viewGroup = findViewById(android.R.id.content);
        initToolbar();
        initUI();
    }

    private void renderUI(){
        amountText.setText(amount);
    }
    private void initUI(){
        amountText = findViewById(R.id.amount);
        dialog = Util.getCustomDialog(this, viewGroup);
        progressDialog = Util.getProgressDialog(context,viewGroup,Constants.PLEASE_WAIT);

        editText = (EditText) findViewById(R.id.editText);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        keyboard = (CardKeyboard) findViewById(R.id.keyboard);
        clear = keyboard.findViewById(R.id.btn_y);
        pay = keyboard.findViewById(R.id.btn_z);

        pay.setOnClickListener(this);
        clear.setOnClickListener(this);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }


    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.purchase_pin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().hasExtra(Constants.AMOUNT))
            amount = getIntent().getStringExtra(Constants.AMOUNT);

        renderUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent purchaseCard = new Intent(context,PurchaseCard.class);
                purchaseCard.putExtra(Constants.AMOUNT,amount);
                startActivity(purchaseCard);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean validateField(){

        if(editText.getText().toString().isEmpty() || editText.getText().toString().length() < 1){
            editText.setError(Constants.PIN_ERROR);
            editText.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_y:
                editText.setText("");
                break;
            case R.id.btn_z:
                if(!validateField()){
                    return;
                }
                progressDialog.show();
                showDialog();
                /*Intent purchaseCard = new Intent(context,PurchasePin.class);
                purchaseCard.putExtra(Constants.AMOUNT,editText.getText().toString().trim());
                startActivity(purchaseCard);*/
                break;
            default:
                break;
        }
    }

    private void showDialog(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                dialog.show();
                CardView ok = dialog.findViewById(R.id.button_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dialog.isShowing())
                            dialog.dismiss();

                        startActivity(new Intent(context,Dashboard.class));
                        finish();
                    }
                });

            }
        }, 3000);
    }
}
