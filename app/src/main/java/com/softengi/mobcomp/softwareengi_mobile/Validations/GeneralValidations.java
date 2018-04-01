package com.softengi.mobcomp.softwareengi_mobile.Validations;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the similar validations in this application
 */
public class GeneralValidations {

    /**
     * Validates the inputs when updating a profile
     * @param username New or old username
     * @param email New or old email
     * @param language New or old language
     * @param coach New or old coach
     * @return Map of the fields if they are valid, otherwise this will be null
     */
    public static Map<String, String> validateUpdateProfile(TextView username,
                                                                EditText email,
                                                                EditText language,
                                                                CheckBox coach) {
        boolean valid = true;

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

        if(TextUtils.isEmpty(language.getText().toString())) {
            language.setError("Please enter your language");
            language.requestFocus();
            valid = false;
        }

        if(valid) {
            // add the request body
            Map<String, String> map = new HashMap<>();
            map.put("username", username.getText().toString());
            map.put("email", email.getText().toString());
            map.put("language", language.getText().toString());
            map.put("coach", String.valueOf(coach.isChecked()));
            return map;
        }

        return null;
    }


}
