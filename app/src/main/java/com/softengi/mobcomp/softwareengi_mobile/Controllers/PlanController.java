package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
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

public class PlanController {

    private static final String url = "plans/";

    public static void postCreatePlans(final Context ctx, EditText title, EditText requiredSteps, final SuccessListener onSuccess) {

        PlanValidator.validatePlan(title, requiredSteps);
        Map<String, String> map = PlanValidator.validatePlan(
                title,
                requiredSteps);

        if(map != null) {
            map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

            RequestController.createPostRequest(ctx, map, url, new VolleyCallback() {
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

    public static void postUpdate(final Context ctx, EditText title, EditText requiredSteps, String planId, final SuccessListener onSuccess) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("plan_id", planId);
        map.put("new_title", title.getText().toString());
        map.put("new_required_steps", requiredSteps.getText().toString());

        RequestController.createPostRequest(ctx, map, url.concat("update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                onSuccess.successful();
            }
        });
    }

    public static void postDelete(final Context ctx, String planId, final SuccessListener onSuccess) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("plan_id", planId);

        RequestController.createPostRequest(ctx, map, url.concat("delete"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Deleted plan successfully", Toast.LENGTH_SHORT).show();
                onSuccess.successful();
            }
        });
    }

    public static void getListOfPlans(final Context ctx, String username, final ListOfPlanParser callback) {

        RequestController.createGetRequestArray(ctx, url.concat(username+"/list"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                // set the data parameter to the JSONObject result as a string of plans
                callback.onSuccessResponse(result);
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void getPlan(final Context ctx, int planId, final DetailPlanParser callback) {

        RequestController.createGetRequest(ctx, url.concat(String.valueOf(planId)), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    callback.onSuccessResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ctx, "Plan receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
