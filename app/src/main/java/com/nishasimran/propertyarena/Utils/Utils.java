package com.nishasimran.propertyarena.Utils;

import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

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
}
