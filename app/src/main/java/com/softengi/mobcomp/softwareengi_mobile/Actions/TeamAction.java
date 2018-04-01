package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.TeamsLoader;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Handles CRUD operations for Teams. Uses the base URL of teams/ and all
 * subsequent requests are created with appended URLs. Requests are only
 * built in this class, they are then made in the RequestAction class.
 */
public class TeamAction {

    private static final String url = "teams/";

    /**
     * Get a list of all teams
     * @param ctx Context of application
     * @param callback Callback for array response
     */
    public static void getListOfAllTeams(final Context ctx, final ListParser callback) {
        // create the request
        RequestAction.createGetRequestArray(ctx, url, new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
            }
        });
    }

    /**
     * Get a list of the chosen user's teams
     * @param ctx Context of application
     * @param username Name of user to get respective teams
     * @param callback Callback for array response
     */
    public static void getListOfYourTeams(final Context ctx, String username, final ListParser callback) {
        // create the request
        RequestAction.createGetRequestArray(ctx, url.concat(username), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
            }
        });
    }

    /**
     * Get a specific team
     * @param ctx Context of application
     * @param teamId Id of team to get
     * @param callback Callback to handle specific plan
     */
    public static void getTeam(final Context ctx, int teamId, final DetailParser callback) {
        // create the request
        RequestAction.createGetRequest(ctx, url.concat(String.valueOf(teamId)), new VolleyCallback() {
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
     * Create a new team
     * @param ctx Context of application
     * @param name Name of team
     * @param selectedUsers Users to add to this team
     * @param teamsLoader Loaded to re-populate new teams after request
     */
    public static void postCreateTeams(final Context ctx, String name, ArrayList<String> selectedUsers, final TeamsLoader teamsLoader) {
        // add the request body
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("coach", SharedPrefManager.getInstance(ctx).getUsername());
        map.put("selectedUsers", new Gson().toJson(selectedUsers));

        // create the request
        RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Created team successfully", Toast.LENGTH_SHORT).show();
                teamsLoader.loadTeams();
            }
        });
    }

    /**
     * Adds a user to the team
     * @param ctx Context of application
     * @param username Username to get user to add to team
     * @param teamId Id of team to add user to
     */
    public static void postJoinTeam(final Context ctx, String username, String teamId) {
        // add the request body
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("teamId", teamId);

        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("join"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Joined team", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Delete a team
     * @param ctx Context of application
     * @param teamId Id of team to delete
     * @param callback Success callback
     */
    public static void postDeleteTeam(final Context ctx, String teamId, final SuccessListener callback) {
        // add the request body
        Map<String, String> map = new HashMap<>();
        map.put("teamId", teamId);

        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("delete"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                callback.successful();
                Toast.makeText(ctx, "Deleted team", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Get list of users in the team
     * @param ctx Context of application
     * @param teamId Id of team to get list of users for
     * @param callback Callback to handle array response
     */
    public static void getListOfUsersInTeam(final Context ctx, final String teamId, final ListParser callback) {

        // create the request
        RequestAction.createGetRequestArray(ctx, url.concat(teamId).concat("/members"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                // set the data parameter to the JSONObject result as a string of plans
                callback.onSuccessResponse(result);
            }
        });

    }

    /**
     * Assigns a team (multiple users) to a plan
     * @param ctx Context of application
     * @param planId Id of plan
     * @param teamId Id of team
     */
    public static void massAssignTeam(final Context ctx, final String planId, final String teamId) {
        // add the request body
        Map<String,String> map = new HashMap<>();
        map.put("planId", planId);
        map.put("teamId", teamId);
        // create the request
        RequestAction.createPostRequest(ctx, map, url.concat("massAssign"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Successfully assigned team to plan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
