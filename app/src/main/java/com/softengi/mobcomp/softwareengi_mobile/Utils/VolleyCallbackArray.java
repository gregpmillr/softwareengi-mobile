package com.softengi.mobcomp.softwareengi_mobile.Utils;

import org.json.JSONArray;

/**
 * Handles response for Volley multi-response requests
 */
public interface VolleyCallbackArray {

    /**
     * Called when a multi-response request is made
     * @param result JSONArray from HTTP request
     */
    void onSuccessResponse(JSONArray result);

}
