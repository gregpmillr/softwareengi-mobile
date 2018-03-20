package com.softengi.mobcomp.softwareengi_mobile.Utils;

import com.auth0.android.jwt.JWT;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    // constants
    public static final String SHARED_PREF_NAME = "softwareengisharedpref";
    public static final String KEY_USERNAME = "keyusername";
    public static final String KEY_EMAIL = "keyemail";
    public static final String KEY_COACH = "keycoach";
    public static final String KEY_LANGUAGE = "keylanguage";

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
     * @param token
     */
    public void userLogin(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JWT jwt = new JWT(token);

        editor.putString(KEY_USERNAME, jwt.getClaim("username").asString());
        editor.putString(KEY_EMAIL, jwt.getClaim("email").asString());
        editor.putBoolean(KEY_COACH, jwt.getClaim("coach").asBoolean());
        editor.putString(KEY_LANGUAGE, jwt.getClaim("language").asString());
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

    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, "No username exists");
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "No email exists");
    }

    public String getCoach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getBoolean(KEY_COACH, false));
    }

    public String getLanguage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, "No language exists");
    }

    public boolean isUserCoach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_COACH, false);
    }

    /**
     * Log the user out
     */
    public void logout() {
        clear();
    }

    /**
     * Clear SharedPreferences
     */
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
