package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;

public class UserAction {

    private static final String url = "users/";

    public static void getListOfUsers(final Context ctx, final ListParser callback) {

        RequestAction.createGetRequestArray(ctx, url, new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                // set the data parameter to the JSONObject result as a string of plans
                Toast.makeText(ctx, "List received successfully", Toast.LENGTH_SHORT).show();
                callback.onSuccessResponse(result);
            }
        });

    }

}
