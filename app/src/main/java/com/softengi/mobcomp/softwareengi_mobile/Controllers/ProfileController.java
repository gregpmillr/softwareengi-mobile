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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    /**
     * POST request to API server to update user information
     * @param context Current context
     * @param etUsername Username of user to update
     * @param etEmail Email of user to update
     * @param chkCoach Coach of user to update
     * @return Whether the user has been updated or not
     */
    public static boolean postUpdate(
            final Context context,
            EditText etUsername,
            EditText etEmail,
            EditText etLanguage,
            CheckBox chkCoach
    ) {

        String url = "http://192.168.2.14:8000/users/update";
        final String username = etUsername.getText().toString();
        final String email    = etEmail.getText().toString();
        final String language = etLanguage.getText().toString();
        final boolean coach   = chkCoach.isChecked();

        boolean passedValidation = true;

        if(TextUtils.isEmpty(username)) {
            etUsername.setError("Cannot have empty username");
            etUsername.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(email)) {
            etEmail.setError("Cannot have empty email");
            etEmail.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(language)) {
            etLanguage.setError("Cannot have empty language");
            etLanguage.requestFocus();
            passedValidation = false;
        }

        if(!passedValidation) {
            Toast.makeText(context,"Unable to register!",
                    Toast.LENGTH_SHORT);
            return false;
        }

        return getUpdateResponse(url, null,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            JSONObject response = new JSONObject(result);

                            SharedPrefManager.getInstance(context).userLogin(
                                    response.getString("token")
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, context, username, email, coach, language);

    }

    /**
     * Get the response from POST request when updating user
     * @param url URL for API endpoint
     * @param jsonValue Json value
     * @param callback Callback when call is successful
     * @param mCtx Current context
     * @param username Username of user to update
     * @param email Email of user to update
     * @param coach Whether user is coach or not
     * @param language Email of user to update
     * @return whether the request was successful (200) or not (400)
     */
    static boolean getUpdateResponse(String url, JSONObject jsonValue,
                                     final VolleyCallback callback,
                                     final Context mCtx,
                                     final String username,
                                     final String email,
                                     final boolean coach,
                                     final String language) {

        RequestQueue queue = Volley.newRequestQueue(mCtx);

        StringRequest strreq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(mCtx, e + "error", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("coach", coach ? "true" : "false");
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

        queue.add(strreq);

        return true;

    }

}
