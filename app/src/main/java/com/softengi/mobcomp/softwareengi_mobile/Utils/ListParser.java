package com.softengi.mobcomp.softwareengi_mobile.Utils;

import org.json.JSONArray;

/**
 * Parser for a list of items
 */
public interface ListParser {

    /**
     * Handles the successful response of an array of items
     * @param data JSONArray response
     */
    void onSuccessResponse(JSONArray data);

}
