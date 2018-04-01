package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;

import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
/**
 * Handles CRUD operations for Users. Uses the base URL of users/ and all
 * subsequent requests are created with appended URLs. Requests are only
 * built in this class, they are then made in the RequestAction class.
 */
public class UserAction {

    private static final String url = "users/";

    /**
     * Get a list of all users
     * @param ctx Context of application
     * @param callback Callback to handle array response
     */
    public static void getListOfUsers(final Context ctx, final ListParser callback) {
        // create the request
        RequestAction.createGetRequestArray(ctx, url, new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                // set the data parameter to the JSONObject result as a string of plans
                callback.onSuccessResponse(result);
            }
        });

    }

}
