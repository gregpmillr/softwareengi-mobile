package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.softengi.mobcomp.softwareengi_mobile.R;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallbackArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestController {

    /**
     * Builds the POST request and sends it to the API server
     * @param ctx Context of application
     * @param params HashMap of user-entered data
     * @param urlExtension String specifying the API URL
     * @param callback Function called when the request is successful
     */
    public static void createPostRequest(final Context ctx, final Map<String, String> params, String urlExtension, final VolleyCallback callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccessResponse(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                // error response
                e.printStackTrace();

                Toast.makeText(ctx,
                        "Error",
                        Toast.LENGTH_SHORT).show();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() {
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

    /**
     * Builds the GET request and sends it to the API server
     * @param ctx Context of application
     * @param urlExtension String specifying the API URL with parameters
     * @param callback Function called when the request is successful
     */
    public static void createGetRequest(final Context ctx, String urlExtension, final VolleyCallback callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {

                        // error response
                        e.printStackTrace();

                        Toast.makeText(ctx,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );

        RequestQueueSingleton.getInstance(ctx).addToRequestQueue(request);

    }

    /**
     * Builds the GET request and sends it to the API server
     * @param ctx Context of application
     * @param urlExtension String specifying the API URL with parameters
     * @param callback Function called when the request is successful
     */
    public static void createGetRequestArray(final Context ctx, String urlExtension, final VolleyCallbackArray callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {

                        // error response
                        e.printStackTrace();

                        Toast.makeText(ctx,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );

        RequestQueueSingleton.getInstance(ctx).addToRequestQueue(request);

    }
}
