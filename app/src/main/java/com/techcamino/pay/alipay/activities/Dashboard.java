package com.techcamino.pay.alipay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.widget.NumberKeyboard;
import com.techcamino.pay.alipay.widget.NumberTextWatcher;

import me.abhinay.input.CurrencyEditText;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private Context context = this;
    //private EditText editText;
    CurrencyEditText etInput;
    private CardView clear,pay;
    private NumberKeyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Purchase");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

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
        /*editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new NumberTextWatcher(editText, "#,###"));*/

        etInput = (CurrencyEditText) findViewById(R.id.editText);
        //etInput.setCurrency(Currency.NONE);
        etInput.setDelimiter(false);
        etInput.setSpacing(false);
        etInput.setDecimals(true);
        //Make sure that Decimals is set as false if a custom Separator is used
        etInput.setSeparator(".");

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Handler mHandler = new Handler(); // use handler only when required
        int elapse = 220;

        if(id == R.id.refund) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context,RefundTransaction.class));
                }
            },elapse);

        }
        else if(id == R.id.cancel_transaction) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context,Cancellation.class));
                }
            },elapse);
        }
        else if(id == R.id.authorization) {

        }
        else if(id == R.id.purchase_advice) {

        }
        else if(id == R.id.settlement) {

        }
        else if(id == R.id.network_login) {

        }
        else if(id == R.id.merchant_menu) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context,MerchantLogin.class));
                }
            },elapse);


        }
        else if(id == R.id.admin_menu) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context,AdminLogin.class));
                }
            },elapse);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
