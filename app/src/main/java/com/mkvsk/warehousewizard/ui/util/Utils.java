package com.mkvsk.warehousewizard.ui.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.request.RequestOptions;
import com.mkvsk.warehousewizard.R;

public class Utils {
    public static RequestOptions options;

    private static Context appContext;

    public static void initContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static RequestOptions getOptions() {
        return options = new RequestOptions()
                .optionalFitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_error);
//                .useAnimationPool(true);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
