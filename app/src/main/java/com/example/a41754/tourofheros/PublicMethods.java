package com.example.a41754.tourofheros;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PublicMethods {



    public static void hideKeyBoard(Context context,View windowView) {
        InputMethodManager manager = ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(windowView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }

}
