package com.softengi.mobcomp.softwareengi_mobile.Utils;


import org.json.JSONException;
import org.json.JSONObject;

public interface DetailParser {

    void onSuccessResponse(JSONObject data) throws JSONException;

}
