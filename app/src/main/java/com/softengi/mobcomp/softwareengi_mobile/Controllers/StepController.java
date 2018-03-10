package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StepController {

    private static final String url = "steps";

    public static void postSteps(final Context ctx, int numSteps, int planId) {


        Map<String,String> map = new HashMap<String,String>();
        map.put("steps",String.valueOf(numSteps));
        map.put("planId", String.valueOf(planId));

        RequestController.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Saved steps successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
