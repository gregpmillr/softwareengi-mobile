package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by br239 on 2018-03-09.
 */

public class StepController {

    private static final String url = "http://142.134.23.52:8000/exercise";

    private static void postSteps(final Context ctx, int numSteps, int planId) {
        if(planId == -1) {

        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, url.concat("email"), null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            // successful response
                            Toast.makeText(ctx, "Success!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError e) {

                                // error response
                                e.printStackTrace();

                                Toast.makeText(ctx,
                                        "Error retrieving data",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("email", email);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(ctx).addToRequestQueue(request);

        }
    }
}
