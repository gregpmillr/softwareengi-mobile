package com.softengi.mobcomp.softwareengi_mobile.Utils;

import com.auth0.android.jwt.JWT;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages all actions for shared preferences to ensure that there is never
 * more than one instance of Shared Preferences at one time.
 */
public class SharedPrefManager {

    // constants
    private static final String SHARED_PREF_NAME = "softwareengisharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_COACH = "keycoach";
    private static final String KEY_LANGUAGE = "keylanguage";
    private static final String KEY_PASSWORD= "keypassword";
    private static final String KEY_ID = "keyid";
    private static final String KEY_REMEMBER_ME = "keyrememberme";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    /**
     * Constructor
     * @param context Application context
     */
    private SharedPrefManager(Context context) {
        this.mCtx = context;
    }

    /**
     * Gets the instance of Shared Preferences for the context
     * @param context Application context
     * @return the SharedPrefManager for the context
     */
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
        editor.putInt(KEY_ID, jwt.getClaim("id").asInt());
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
     * Get the username of the logged in user
     * @return The username of the logged in user
     */
    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, "No username exists");
    }

    /**
     * Get the email of the logged in user
     * @return The email of the logged in uer
     */
    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "No email exists");
    }

    /**
     * Get the coach value of the logged in user
     * @return The coach vlaue of the logged in user
     */
    public String getCoach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getBoolean(KEY_COACH, false));
    }

    /**
     * Get the language of the logged in uer
     * @return The language of the logged in user
     */
    public String getLanguage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, "No language exists");
    }

    /**
     * Get the password of the logged in user
     * @return The password of the logged in user
     */
    public String getPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASSWORD, "No password exists");
    }

    /**
     * Get whether the logged in user is a coach or not
     * @return Boolean of if the logged in user is a coach
     */
    public boolean isUserCoach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_COACH, false);
    }

    /**
     * Check if the user wants the app to remember their credentials
     * @return Boolean for remembering the user
     */
    public boolean getRememberMe() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
    }

    /**
     * Set the user to remember their credentials or not
     * @param flag Boolean value to remember credentials or not
     */
    public void setRememberMe(boolean flag) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_REMEMBER_ME, flag);
        editor.apply();
    }

    /**
     * Set the password of the user
     * @param password Password of user to save
     */
    public void setPassword(String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    /**
     * Get the Id of the current user
     * @return Id of current user
     */
    public int getId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0);
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
