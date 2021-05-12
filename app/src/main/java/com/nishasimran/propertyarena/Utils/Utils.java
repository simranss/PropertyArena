package com.nishasimran.propertyarena.Utils;

import androidx.annotation.NonNull;

public class Utils {

    @NonNull
    public static String [] stringArrayToArray(@NonNull String stringArray) {
        return stringArray.substring(1, stringArray.length() - 1).split(",");
    }
}
