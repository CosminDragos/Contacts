package ro.project.contacts.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cosmin on 18.09.2017.
 */

public class PrefUtils {

    public static final String SHARED_PREFS_NAME = "shared_prefs";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String FULL_ID = "full_id";
    public static final String ICON_THUMBNAIL = "icon_thumbnail";
    public static final String ICON_MEDIUM = "icon_medium";
    public static final String ICON_LARGE = "icon_large";

    public static int getSharedPreference(Context context, String key, int defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultValue);
    }

    public static String getSharedPreference(Context context, String key, String defaultValue) {
        SharedPreferences prefs = PrefUtils.getSharedPreferences(context);
        return prefs.getString(key, defaultValue);
    }

    public static boolean getSharedPreference(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = PrefUtils.getSharedPreferences(context);
        return prefs.getBoolean(key, defaultValue);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setSharedPreference(Context context, String key, String value) {
        SharedPreferences prefs = PrefUtils.getSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPreference(Context context, String key, Boolean value) {
        SharedPreferences prefs = PrefUtils.getSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

}
