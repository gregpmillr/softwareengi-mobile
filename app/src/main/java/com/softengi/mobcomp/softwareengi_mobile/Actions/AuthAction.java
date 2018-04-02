package com.softengi.mobcomp.softwareengi_mobile.Actions;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.softengi.mobcomp.softwareengi_mobile.MainActivity;
import com.softengi.mobcomp.softwareengi_mobile.LoginActivity;
import com.softengi.mobcomp.softwareengi_mobile.R;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Validations.AuthValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles logging in and registering users.
 */
public class AuthAction {

    /**
     * Checks if user info is in DB and can login.
     * @param user Username.
     * @param pass Password.
     * @return Whether data in in DB.
     */
    public static boolean checkLogin(String user, String pass){
        if(!user.equals("bob") || !pass.equals("pass")){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Checks if all fields are entered when registering
     * @param username Username of user to register
     * @param password Password of user to register
     * @param email Email of user to register
     * @param language Preferred language of user to register
     * @return Whether validation passed
     */
    public static boolean checkRegister(
            String username, String password, String email, String language) {

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || language.isEmpty()) {
            return false;
        }

        return true;

    }

    /**
    * Get the response from the POST request to register users
    * @param ctx Context of application
    * @param username EditText of username
    * @param email EditText of email
    * @param password Password for userz
    * @param language Language for user
    * @param coach Checkbox of coach
    */
    public static void postRegister(final Context ctx, String username, String email, String password,
            String language,
            String coach) {

        String urlExtension = "users";
        // add the request body
        // construct map to send back to user
        Map<String, String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("email",email);
        map.put("password",password);
        map.put("language",language);
        map.put("coach",coach);
        // create the API request and save the returned token into shared preferences
        RequestAction.createPostRequest(
                ctx,
                map,
                urlExtension,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject response) {

                        // successful response
                        Toast.makeText(ctx, "Success!",
                                Toast.LENGTH_SHORT).show();

                        try {
                            SharedPrefManager.getInstance(ctx).userLogin(
                                    response.getString("token")
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(ctx, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(intent);

                    }
                }
        );
    }

    /**
     * Send a POST request to login the user
     * @param ctx Context of application
     * @param username Username for user credentials
     * @param password Password for user credentials
     */
    public static void postLogin(final Context ctx, String username, String password, String rememberMe) {

        String urlExtension = "auth";

        // check if the user wants to save the credentials entered
        if(String.valueOf(rememberMe).equals("true")) {
            SharedPrefManager.getInstance(ctx).setRememberMe(true);
            SharedPrefManager.getInstance(ctx).setPassword(password);
        } else {
            SharedPrefManager.getInstance(ctx).setRememberMe(false);
        }

        // construct map to send back to user
        Map<String, String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        // create the API request and save the returned token into shared preferences
        RequestAction.createPostRequest(
                ctx,
                map,
                urlExtension,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject response) {
                        // successful response
                        Toast.makeText(ctx, "Success!",
                                Toast.LENGTH_SHORT).show();
                        try {
                            SharedPrefManager.getInstance(ctx)
                                    .userLogin(response.getString("token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(intent);
                    }
                }
        );
    }
}
