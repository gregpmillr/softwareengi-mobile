package com.softengi.mobcomp.softwareengi_mobile.Validations;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Greg on 3/20/2018.
 */

public class PlanValidator {

    /**
     * Validates a new plan
     * @param title title of the plan
     * @param requiredSteps steps required to complete the plan
     * @return
     */
    public static Map<String, String> validatePlan(EditText title, EditText requiredSteps) {

        boolean valid = true;

        // validate
        if(TextUtils.isEmpty(title.getText().toString())) {
            title.setError("Please enter a title");
            title.requestFocus();
            valid = false;
        }

        if(TextUtils.isEmpty(requiredSteps.getText().toString())) {
            requiredSteps.setError("Please enter required steps");
            valid = false;
        }

        if(valid) {
            // construct map to send back to user
            Map<String, String> map = new HashMap<String,String>();
            map.put("title",title.getText().toString());
            map.put("required_steps",requiredSteps.getText().toString());
            return map;
        }

        return null;
    }
}
