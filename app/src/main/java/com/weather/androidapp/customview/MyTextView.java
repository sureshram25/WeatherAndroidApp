package com.weather.androidapp.customview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by zcodia on 5/18/2015.
 */
public class MyTextView extends AppCompatTextView {

    SharedPreferences sharedPreferences;
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
             init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
               init();
    }

    public MyTextView(Context context) {
        super(context);
               init();
    }

    private void init() {
        if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
                setTypeface(tf);

        }
    }

}