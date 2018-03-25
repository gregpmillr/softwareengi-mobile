package com.softengi.mobcomp.softwareengi_mobile.Validations;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * This class validates user credentials to login and register for the application.
 * Each method sends back a HashMap, which is used to provide to the controller
 * to make the API request
 */
public class AuthValidator {

    /**
     * Validates login credentials
     * @param username username for the user
     * @param password password for the user
     * @return When valid, HashMap with the keys and values of the login credentials, else
     * null is returned
     */
    public static Map<String, String> validateLogin(EditText username, EditText password) {

        boolean valid = true;

        // validate
        if(TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Please enter your username");
            username.requestFocus();
            valid = false;
        }

        if(TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please enter your password");
            valid = false;
        }

        if(valid) {
            // construct map to send back to user
            Map<String, String> map = new HashMap<String,String>();
            map.put("username",username.getText().toString());
            map.put("password",password.getText().toString());
            return map;
        }

        return null;
    }

    /**
     * Validates registration credentials
     * @param username username for the user
     * @param email email for the user
     * @param password password for the user
     * @param language language for the user
     * @param coach coach for the user
     * @return When valid, HashMap with the keys and values of the registration credentials, else
     * null is returned
     */
    public static Map<String, String> validateRegister(EditText username,
                                                       EditText email,
                                                       EditText password,
                                                       EditText language,
                                                       CheckBox coach
    ) {

        boolean valid = true;

        // validate
        if(TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Please enter your username");
            username.requestFocus();
            valid = false;
        }

        if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Please enter your email");
            email.requestFocus();
            valid = false;
        }

        if(TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please enter your password");
            valid = false;
        }
        if(TextUtils.isEmpty(language.getText().toString())) {
            email.setError("Please enter your language");
            email.requestFocus();
            valid = false;
        }

        if(valid) {
            // construct map to send back to user
            Map<String, String> map = new HashMap<String,String>();
            map.put("username",username.getText().toString());
            map.put("email",email.getText().toString());
            map.put("password",password.getText().toString());
            map.put("language",language.getText().toString());
            map.put("coach",String.valueOf(coach.isChecked()));
            return map;
        }

        return null;
    }

}
