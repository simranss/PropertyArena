package com.nishasimran.propertyarena.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import com.nishasimran.propertyarena.Values.Values;

import java.util.Date;
import java.util.Locale;

public class Utils {

    @NonNull
    public static String [] stringArrayToArray(@NonNull String stringArray) {
        return stringArray.substring(1, stringArray.length() - 1).split(",");
    }

    @NonNull
    public static int [] intArrayToArray(@NonNull String intArray) {
        String [] stringArray = stringArrayToArray(intArray);

        int [] array = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            array[i] = Integer.parseInt(stringArray[i].trim());
        }
        return array;
    }

    public static String getFormattedDate(long milliSec) {
        Date date = new Date(milliSec);

        // date
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy", Locale.getDefault());

        return df.format(date);
    }

    public static int getStampDutyTax(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return (preferences.getInt(Values.SHARED_PREFS_STAMP_DUTY, 5) / 100);
    }

    public static int getGstTax(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return (preferences.getInt(Values.SHARED_PREFS_GST, 5) / 100);
    }

    public static int getRegFee(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getInt(Values.SHARED_PREFS_REGISTRATION_FEE, 30000);
    }

    public static void setStampDutyTax(Application application, int value) {
        SharedPreferences.Editor editor = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt(Values.SHARED_PREFS_STAMP_DUTY, value).apply();
    }

    public static void setGstTax(Application application, int value) {
        SharedPreferences.Editor editor = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt(Values.SHARED_PREFS_GST, value).apply();
    }

    public static void setRegFee(Application application, int value) {
        SharedPreferences.Editor editor = application.getSharedPreferences(Values.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt(Values.SHARED_PREFS_REGISTRATION_FEE, value).apply();
    }
}
