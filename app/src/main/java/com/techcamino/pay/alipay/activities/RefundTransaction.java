package com.techcamino.pay.alipay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.widget.NumberKeyboard;

import me.abhinay.input.CurrencyEditText;

public class RefundTransaction extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    //private EditText editText;
    CurrencyEditText etInput;
    private CardView clear,pay;
    private NumberKeyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_transaction);
        initToolbar();
        initUI();
    }

    private boolean validateField(){

        if(etInput.getText().toString().isEmpty() || etInput.getText().toString().length() < 1){
            etInput.setError(Constants.AMOUNT_ERROR);
            etInput.requestFocus();
            return false;
        }

        return true;
    }

    private void initUI(){
        //editText = (EditText) findViewById(R.id.editText);
        etInput = (CurrencyEditText) findViewById(R.id.editText);
        //etInput.setCurrency(Currency.NONE);
        etInput.setDelimiter(false);
        etInput.setSpacing(false);
        etInput.setDecimals(true);
        //Make sure that Decimals is set as false if a custom Separator is used
        etInput.setSeparator(".");
        //editText.addTextChangedListener(new NumberTextWatcher(editText, "#,###"));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        keyboard = (NumberKeyboard) findViewById(R.id.keyboard);
        clear = keyboard.findViewById(R.id.btn_c);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener(this);
        clear.setOnClickListener(this);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = etInput.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }


    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.refund_lbl);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_c:
                etInput.setText("");
                break;
            case R.id.pay:
                if(!validateField()){
                    return;
                }
                Intent purchaseCard = new Intent(context,PurchaseCard.class);
                purchaseCard.putExtra(Constants.AMOUNT,etInput.getText().toString().trim());
                startActivity(purchaseCard);
                finish();
                break;
            default:
                break;
        }
    }
}
