package com.softengi.mobcomp.softwareengi_mobile.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parser for a specific item's detail
 */
public interface DetailParser {

    /**
     * Handles the successful response of a specific item
     * @param data JSONObject response
     * @throws JSONException If a JSON object cannot be converted
     */
    void onSuccessResponse(JSONObject data) throws JSONException;

}
