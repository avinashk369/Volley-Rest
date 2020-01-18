package com.techcamino.pay.alipay.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.adapter.MenuItemAdapter;
import com.techcamino.pay.alipay.details.MenuDetails;
import com.techcamino.pay.alipay.util.Constants;
import com.techcamino.pay.alipay.util.Util;

import java.util.ArrayList;

public class AdminMenu extends AppCompatActivity implements MenuItemAdapter.CustomDialogListener{

    private Context context = this;
    private ArrayList<MenuDetails> adminMenu;
    private RecyclerView adminMenuList;
    private GridLayoutManager gridLayoutManager;
    private AlertDialog dialog,progressDialog;
    private ViewGroup viewGroup;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        viewGroup = findViewById(android.R.id.content);
        initToolbar();
        initUI();
        renderMenu();
    }

    private void initUI(){
        dialog = Util.getCustomDialog(this, viewGroup);
        progressDialog = Util.getProgressDialog(context,viewGroup, Constants.PLEASE_WAIT);
        adminMenuList = findViewById(R.id.admin_menu);
        title = findViewById(R.id.title);
        title.setText(R.string.admin_menu_lbl);
    }


    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    @Override
    protected void onStart() {
        super.onStart();
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

    private void renderMenu(){
        adminMenu = new ArrayList<MenuDetails>(){
            {
                add(new MenuDetails("INIT SETUP",R.color.red));
                add(new MenuDetails("SET PARAMETERS",R.color.red));
                add(new MenuDetails("REMOTE DOWNLOAD",R.color.red));
                add(new MenuDetails("SET USER PROFILE",R.color.red));
                add(new MenuDetails("HOST SETTINGS",R.color.red));
                add(new MenuDetails("BLUETOOTH SETTINGS",R.color.red));
                add(new MenuDetails("PRINT TERMINAL DATA",R.color.red));
                add(new MenuDetails("DEBUG MENU",R.color.red));
                add(new MenuDetails("KEY INJECTION",R.color.red));
                add(new MenuDetails("LANGUAGE",R.color.red));
                add(new MenuDetails("ADD LICENCE",R.color.red));
                add(new MenuDetails("GENERATE LICENSE",R.color.red));
                add(new MenuDetails("CHECK LICENSE",R.color.red));
                add(new MenuDetails("RESET MERCHANT PASSWORD ",R.color.red));
                add(new MenuDetails("INTEGRATION",R.color.red));
                add(new MenuDetails("POWER OFF",R.color.red));
                add(new MenuDetails("REBOOT",R.color.red));
            }
        };

        MenuItemAdapter chipAdapter = new MenuItemAdapter(adminMenu,context);
        //gridLayoutManager =  new GridLayoutManager(getApplicationContext(),1, GridLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adminMenuList.setLayoutManager(mLayoutManager);
        adminMenuList.setAdapter(chipAdapter);
    }

    @Override
    public void onItemClicked(MenuDetails menuDetails) {
        Toast.makeText(context,menuDetails.getMenuName(),Toast.LENGTH_LONG).show();
    }
}
