package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.R;
import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * Handles CRUD operations for Profiles. Uses the base URL of users/ and all
 * subsequent requests are created with appended URLs. Requests are only
 * built in this class, they are then made in the RequestAction class.
 */
public class ProfileAction {

    private static final String url = "users/";

    /**
     * Updates a profile
     * @param ctx Conext of application
     * @param username New or previous username
     * @param email New or previous email
     * @param language New or previous language
     * @param coach New or previous coach
     */
    public static void postUpdate(final Context ctx, String username, String email, String language, String coach) {
        // add the request body
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        map.put("language", language);
        map.put("coach", coach);
        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, R.string.updated, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Gets a profile
     * @param ctx Context of application
     * @param tvTotalSteps Total steps of all users
     * @param tvTotalPlans Total plans of all users
     * @param tvTotalTeams Total teams of all users
     * @param tvRecentPlans Recent plans (past 7 days)
     * @param tvRecentSteps Recent steps (past 7 days)
     * @param callback Success callback
     */
    public static void getProfile(final Context ctx, final TextView tvTotalSteps,
                                  final TextView tvTotalPlans,
                                  final TextView tvTotalTeams,
                                  final TextView tvRecentPlans,
                                  final TextView tvRecentSteps,
                                  final DetailParser callback) {

        // create the request
        RequestAction.createGetRequest(ctx, url.concat(SharedPrefManager.getInstance(ctx).getUsername()), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    callback.onSuccessResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ctx, "Profile receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
