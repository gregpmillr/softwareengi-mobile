package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StepAction {

    private static final String url = "steps";

    public static void postSteps(final Context ctx, int numSteps, int planId) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("steps",String.valueOf(numSteps));
        map.put("planId", String.valueOf(planId));
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Saved steps successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
