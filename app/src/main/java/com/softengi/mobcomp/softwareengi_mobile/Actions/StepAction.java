package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

public class StepAction {

    private static final String url = "steps/";

    public static void postSteps(final Context ctx, int numSteps, int planId) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("steps",String.valueOf(numSteps));
        map.put("planId", String.valueOf(planId));
        map.put("username", String.valueOf(SharedPrefManager.getInstance(ctx).getUsername()));

        RequestAction.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                Toast.makeText(ctx, "Saved steps successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getStepsByPlan(final Context ctx, String planId, final LineGraphSeries<DataPoint> stepEntries) {
        String username = SharedPrefManager.getInstance(ctx).getUsername();

        RequestAction.createGetRequestArray(ctx, url.concat(username + "/" + planId + "/list"), new VolleyCallbackArray() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                try {
                    /*
                    TimeZone tz = TimeZone.getTimeZone("UTC");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    df.setTimeZone(tz);
                    Date d;
                    */
                    for(int i = 0; i < result.length(); i++) {
                        JSONObject jsonObj = result.getJSONObject(i);
                        //try {
                            //d = df.parse(jsonObj.getString("updated_at"));
                            //stepEntries.appendData(new DataPoint(d, jsonObj.getInt("steps")), false, 99999);
                            stepEntries.appendData(new DataPoint(i, jsonObj.getInt("steps")), true, 99999);
                        //} catch(ParseException e) {
                        //    e.printStackTrace();
                        //}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(ctx, "Steps receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
