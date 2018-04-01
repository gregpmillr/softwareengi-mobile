package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
/**
 * Handles CRUD operations for Steps. Uses the base URL of steps/ and all
 * subsequent requests are created with appended URLs. Requests are only
 * built in this class, they are then made in the RequestAction class.
 */
public class StepAction {

    private static final String url = "steps/";

    /**
     * Creates a new step
     * @param ctx Context of application
     * @param numSteps Number of steps completed
     * @param planId Id of plan for this step record
     */
    public static void postSteps(final Context ctx, int numSteps, int planId) {
        // add the request body
        Map<String,String> map = new HashMap<>();
        map.put("steps",String.valueOf(numSteps));
        map.put("planId", String.valueOf(planId));
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        // create the request
        RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    if(result.getString("completed").equals("true")) {
                        Toast.makeText(ctx, "Congrats! You've completed the plan.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Get steps by plan
     * @param ctx Context of application
     * @param planId Id of plan
     * @param callback Success callack
     */
    public static void getStepsByPlan(final Context ctx, String planId, final ListParser callback) {
        String username = SharedPrefManager.getInstance(ctx).getUsername();

        // create the request
        RequestAction.createGetRequestArray(ctx, url.concat(username + "/" + planId + "/list"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
                Toast.makeText(ctx, "Steps receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
