package com.softengi.mobcomp.softwareengi_mobile.Utils;

import com.softengi.mobcomp.softwareengi_mobile.Models.User;
import com.softengi.mobcomp.softwareengi_mobile.MainActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;



public class SharedPrefManager {
    // constants
    public static final String SHARED_PREF_NAME = "softwareengisharedpref";
    public static final String KEY_USERNAME = "keyusername";
    public static final String KEY_EMAIL = "keyemail";
    public static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        this.mCtx = context;
    }

    public static SharedPrefManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /**
     * Stores the user login information into shared preferences
     * @param user
     */
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    /**
     * Check if user is logged in
     * @return boolean for current login status
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    /**
     * Log the user out
     */
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
