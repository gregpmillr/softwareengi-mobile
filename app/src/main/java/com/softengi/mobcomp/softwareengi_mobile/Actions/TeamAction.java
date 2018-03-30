package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamAction {

    private static final String url = "teams/";

    public static void getListOfAllTeams(final Context ctx, final ListParser callback) {
        RequestAction.createGetRequestArray(ctx, url, new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getListOfYourTeams(final Context ctx, String username, final ListParser callback) {
        RequestAction.createGetRequestArray(ctx, url.concat(username+"/list"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                callback.onSuccessResponse(result);
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getTeam(final Context ctx, int teamId, final DetailParser callback) {

        RequestAction.createGetRequest(ctx, url.concat(String.valueOf(teamId)), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    callback.onSuccessResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ctx, "Team receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void postCreateTeams(final Context ctx, String name, ArrayList<String> selectedUsers) {
        System.out.println("NAME: " + name);
        Map<String,String> map = new HashMap<String,String>();
        map.put("name", name);
        map.put("coach", SharedPrefManager.getInstance(ctx).getUsername());
        map.put("selectedUsers", new Gson().toJson(selectedUsers));

        RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Created team successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
