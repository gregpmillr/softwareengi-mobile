package com.softengi.mobcomp.softwareengi_mobile.Validations;

import android.widget.EditText;

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



        return null;
    }

}
