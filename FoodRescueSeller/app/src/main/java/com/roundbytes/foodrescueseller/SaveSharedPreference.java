package com.roundbytes.foodrescueseller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_UID= "UID";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUID(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_UID, userName);
        editor.commit();
    }

    public static String getUID(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_UID, "");
    }
}
