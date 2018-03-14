package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.ListOfPlanActivity;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlanController {

    private static final String url = "plans";

    public static void postCreatePlans(final Context ctx, String requiredSteps, String title) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("requiredSteps",String.valueOf(requiredSteps));
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

    public static void postUpdateSteps(final Context ctx, String requiredSteps) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("requiredSteps",String.valueOf(requiredSteps));
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

}
