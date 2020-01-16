package com.techcamino.pay.alipay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.techcamino.pay.alipay.R;

public class PasswordKeyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public PasswordKeyboard(Context context) {
        this(context, null, 0);
    }

    public PasswordKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private CardView mButton1;
    private CardView mButton2;
    private CardView mButton3;
    private CardView mButton4;
    private CardView mButton5;
    private CardView mButton6;
    private CardView mButton7;
    private CardView mButton8;
    private CardView mButton9;
    private CardView mButton0;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.password_keyboard, this, true);
        mButton1 = (CardView) findViewById(R.id.btn_1);
        mButton2 = (CardView) findViewById(R.id.btn_2);
        mButton3 = (CardView) findViewById(R.id.btn_3);
        mButton4 = (CardView) findViewById(R.id.btn_4);
        mButton5 = (CardView) findViewById(R.id.btn_5);
        mButton6 = (CardView) findViewById(R.id.btn_6);
        mButton7 = (CardView) findViewById(R.id.btn_7);
        mButton8 = (CardView) findViewById(R.id.btn_8);
        mButton9 = (CardView) findViewById(R.id.btn_9);
        mButton0 = (CardView) findViewById(R.id.btn_0);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.btn_1, "1");
        keyValues.put(R.id.btn_2, "2");
        keyValues.put(R.id.btn_3, "3");
        keyValues.put(R.id.btn_4, "4");
        keyValues.put(R.id.btn_5, "5");
        keyValues.put(R.id.btn_6, "6");
        keyValues.put(R.id.btn_7, "7");
        keyValues.put(R.id.btn_8, "8");
        keyValues.put(R.id.btn_9, "9");
        keyValues.put(R.id.btn_0, "0");
        /*keyValues.put(R.id.button_enter, "\n");
        keyValues.put(R.id.button_enter, "\n");
        keyValues.put(R.id.button_enter, "\n");*/
    }

    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        String value = keyValues.get(v.getId());
        inputConnection.commitText(value, 1);
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
}
