package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.ListOfPlanActivity;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlanController {

    private static final String url = "plans/";

    public static void postCreatePlans(final Context ctx, String requiredSteps, String title) {
        Toast.makeText(ctx,title,Toast.LENGTH_SHORT);
        Toast.makeText(ctx, requiredSteps, Toast.LENGTH_SHORT);
        Map<String,String> map = new HashMap<String,String>();
        map.put("required_steps",String.valueOf(requiredSteps));
        map.put("title", String.valueOf(title));
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        RequestController.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Intent i = new Intent(ctx, ListOfPlanActivity.class);
                ctx.startActivity(i);
                Toast.makeText(ctx, "Saved plan successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postUpdateSteps(final Context ctx, String planId, String requiredSteps) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId);
        map.put("requiredSteps", requiredSteps);
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        RequestController.createPostRequest(ctx, map, url.concat("/update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Intent i = new Intent(ctx, ListOfPlanActivity.class);
                ctx.startActivity(i);
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postUpdateTitle(final Context ctx, String planId, String title) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId);
        map.put("title",title);
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));
        RequestController.createPostRequest(ctx, map, url.concat("/update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Intent i = new Intent(ctx, ListOfPlanActivity.class);
                ctx.startActivity(i);
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postDelete(final Context ctx, String planId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("planId", planId);
        RequestController.createPostRequest(ctx, map, url.concat("/delete"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Intent i = new Intent(ctx, ListOfPlanActivity.class);
                ctx.startActivity(i);
                Toast.makeText(ctx, "Updated plan successfully", Toast.LENGTH_SHORT).show();
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
