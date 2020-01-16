package com.techcamino.pay.alipay.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.util.Constants;

import java.util.HashMap;

public class PurchaseCard extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private String amount;
    private TextView amountText;
    private HashMap<String,String> transactionPay;
    private RelativeLayout footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_card);

        initToolbar();
        initUI();

        ImageView swipe = findViewById(R.id.swipe);
        ImageView insert = findViewById(R.id.insert);
        ImageView tap = findViewById(R.id.tap);

        swipe.setOnClickListener(this);
        insert.setOnClickListener(this);
        tap.setOnClickListener(this);
        footer = findViewById(R.id.footer);
        footer.setOnClickListener(this);

    }

    private void showBottomSheet(HashMap<String, String> messageMap){
        View dialogView = getLayoutInflater().inflate(R.layout.bottom_option, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(dialogView);
        dialog.show();

    }

    private void renderUI(){
        amountText.setText(amount);
    }
    private void initUI(){
        amountText = findViewById(R.id.amount);
        transactionPay = new HashMap<>();
    }


    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.purchase_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().hasExtra(Constants.AMOUNT)) {
            amount = getIntent().getStringExtra(Constants.AMOUNT);
        }/*else{
            LinearLayout amtLayouot = findViewById(R.id.amt_layout);
            amtLayouot.setVisibility(View.GONE);
        }*/

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
                startActivity(new Intent(context,Dashboard.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.swipe:
            case R.id.insert:
            case R.id.tap:
                Intent purchaseCard = new Intent(context,PurchasePin.class);
                purchaseCard.putExtra(Constants.AMOUNT,amount);
                startActivity(purchaseCard);
                finish();
                break;
            case R.id.footer:
                transactionPay.put(Constants.DIALOG_MESSAGE,"Testing");
                showBottomSheet(transactionPay);
                break;
            default:
                break;



        }
    }
}
