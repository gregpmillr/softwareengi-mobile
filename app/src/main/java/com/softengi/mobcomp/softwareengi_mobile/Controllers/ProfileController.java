package com.softengi.mobcomp.softwareengi_mobile.Controllers;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    private static final String url = "http://142.134.23.52:8000/users/update/";

    public static void postEmail(final Context ctx, EditText etEmail) {

        final String email = etEmail.getText().toString();

        if(TextUtils.isEmpty(email)) {
            etEmail.setError("Email cannot be empty");
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
                    }, new Response.ErrorListener() {
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
                    params.put("email", email);
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

    public static void postLanguage(final Context ctx, EditText etLanguage) {
        final String language = etLanguage.getText().toString();

        if(TextUtils.isEmpty(language)) {
            etLanguage.setError("Language cannot be empty");
        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,  url.concat("language"), null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            // successful response
                            Toast.makeText(ctx, "Success!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
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
                    params.put("language", language);
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

    public static void postCoach(final Context ctx, EditText etCoach) {

        final String coach = etCoach.getText().toString();

        if(TextUtils.isEmpty(coach)) {
            etCoach.setError("Coach cannot be empty");
        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, url.concat("coach"), null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            // successful response
                            Toast.makeText(ctx, "Success!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
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
                    params.put("coach", coach);
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
