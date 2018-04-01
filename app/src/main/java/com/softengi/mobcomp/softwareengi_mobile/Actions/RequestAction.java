package com.softengi.mobcomp.softwareengi_mobile.Actions;

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

/**
 * Handles the GET/POST requests made by each action. All URLs are final
 * in this class and ready to be sent to the server. All requests
 * contain the parameters or body values for the request, dependent
 * upon if GET or POST request.
 */
class RequestAction {

    /**
     * Builds the POST request and sends it to the API server
     * @param ctx Context of application
     * @param params HashMap of user-entered data
     * @param urlExtension String specifying the API URL
     * @param callback Function called when the request is successful
     */
    static void createPostRequest(final Context ctx, final Map<String, String> params, String urlExtension, final VolleyCallback callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        // create the POST request
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
                Map<String,String> headers = new HashMap<>();
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
    static void createGetRequest(final Context ctx, String urlExtension, final VolleyCallback callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        // creates the GET request
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
    static void createGetRequestArray(final Context ctx, String urlExtension, final VolleyCallbackArray callback) {

        String url = ctx.getResources().getString(R.string.base_api_url).concat(urlExtension);

        // creaes the GET request
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
