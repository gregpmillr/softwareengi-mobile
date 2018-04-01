package com.softengi.mobcomp.softwareengi_mobile.Utils;

import org.json.JSONObject;

/**
 * Handles response for Volley single-response requests
 */
public interface VolleyCallback {

    /**
     * Called when a single-response request is made
     * @param result JSONObject from HTTP request
     */
    void onSuccessResponse(JSONObject result);

}
