package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;
import com.softengi.mobcomp.softwareengi_mobile.Validations.PlanValidator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles CRUD operations for Plans. Uses the base URL of plans/ and all
 * subsequent requests are created with appended URLs. Requests are only
 * built in this class, they are then made in the RequestAction class.
 */
public class PlanAction {

    private static final String url = "plans/";

    /**
     * Creates a plan
     * @param ctx Context of application
     * @param title Plan title
     * @param requiredSteps Plan required steps
     * @param onSuccess Success callback
     */
    public static void postCreatePlans(final Context ctx, EditText title, EditText requiredSteps, final SuccessListener onSuccess) {

        // validate the plan
        PlanValidator.validatePlan(title, requiredSteps);
        Map<String, String> map = PlanValidator.validatePlan(
                title,
                requiredSteps);

        // ensure valid
        if(map != null) {
            map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

            // if logged in user is a coach, then assign a coachId to this plan.
            if(SharedPrefManager.getInstance(ctx).getCoach().equals("true")) {
                map.put("coachId", String.valueOf(SharedPrefManager.getInstance(ctx).getId()));
            }

            // create the request
            RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    Toast.makeText(ctx, "Saved plan successfully", Toast.LENGTH_SHORT).show();
                    onSuccess.successful();
                }
            });
        } else {
            Toast.makeText(ctx, "Unable to create new plan", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Update a plan
     * @param ctx Context of application
     * @param title New or previous plan title
     * @param requiredSteps New or previous required steps
     * @param planId Id of plan to update
     * @param onSuccess Success callback
     */
    public static void postUpdate(final Context ctx, EditText title, EditText requiredSteps, String planId, final SuccessListener onSuccess) {

        // add request body
        Map<String,String> map = new HashMap<>();
        map.put("plan_id", planId);
        map.put("new_title", title.getText().toString());
        map.put("new_required_steps", requiredSteps.getText().toString());

        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                onSuccess.successful();
            }
        });
    }

    /**
     * Deletes a plan
     * @param ctx Context of application
     * @param planId Id of plan to delete
     * @param onSuccess Success callback
     */
    public static void postDelete(final Context ctx, String planId, final SuccessListener onSuccess) {

        // add request body
        Map<String,String> map = new HashMap<>();
        map.put("plan_id", planId);

        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("delete"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Deleted plan successfully", Toast.LENGTH_SHORT).show();
                onSuccess.successful();
            }
        });
    }

    /**
     * Get a list of plans for user
     * @param ctx Context of application
     * @param username Username associated with plans
     * @param callback Success callback
     */
    public static void getListOfPlans(final Context ctx, String username, final ListParser callback) {

        // create the request
        RequestAction.createGetRequestArray(ctx, url.concat(username+"/list"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                // set the data parameter to the JSONObject result as a string of plans
                callback.onSuccessResponse(result);
            }
        });

    }

    /**
     * Get a specific plan by planId
     * @param ctx Context of application
     * @param planId Id of plan
     * @param callback Success callback
     */
    public static void getPlan(final Context ctx, int planId, final DetailParser callback) {

        // create the request
        RequestAction.createGetRequest(ctx, url.concat(String.valueOf(planId)), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    callback.onSuccessResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Get the progress of each user for a plan
     * @param ctx Context of application
     * @param planId Id of plan
     * @param callback Success callback
     */
    public static void getUserProgressForPlans(final Context ctx, String planId, final ListParser callback) {
        // create the request
        RequestAction.createGetRequestArray(ctx, url.concat(planId + "/userProgress"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
