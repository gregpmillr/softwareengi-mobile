package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Validations.PlanValidator;

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

    public static void postUpdateSteps(final Context ctx, EditText planId, EditText requiredSteps, final SuccessListener onSuccess) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId.getText().toString());
        map.put("requiredSteps", requiredSteps.getText().toString());
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        RequestController.createPostRequest(ctx, map, url.concat("/update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
                onSuccess.successful();
            }
        });
    }

    public static void postUpdateTitle(final Context ctx, EditText planId, EditText title, final SuccessListener onSuccess) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId.getText().toString());
        map.put("title",title.getText().toString());
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));
        RequestController.createPostRequest(ctx, map, url.concat("/update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
                onSuccess.successful();
            }
        });
    }

    public static void postDelete(final Context ctx, String planId, final SuccessListener onSuccess) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId);
        RequestController.createPostRequest(ctx, map, url.concat("/delete"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
                onSuccess.successful();
            }
        });
    }

    public static void getListOfPlans(final Context ctx, String username, final ListOfPlanParser callback) {

        RequestController.createGetRequest(ctx, url.concat(username+"/list"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                // set the data parater to the JSONObject result as a string of plans
                try {
                    callback.onSuccessResponse(result.getJSONArray("plans"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
