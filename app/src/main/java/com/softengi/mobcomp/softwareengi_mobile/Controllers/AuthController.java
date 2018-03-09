package com.softengi.mobcomp.softwareengi_mobile.Controllers;
import android.content.Context;
import android.content.Intent;import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.softengi.mobcomp.softwareengi_mobile.ListOfPlanActivity;
import com.softengi.mobcomp.softwareengi_mobile.R;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;
import com.softengi.mobcomp.softwareengi_mobile.Validations.AuthValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthController {

    /**
    * Get the response from the POST request to register users
    * @param ctx Context of application
    * @param etUsername EditText of username
    * @param etEmail EditText of email
    * @param etPassword EditText of password
    * @param etLanguage EditText of language
    * @param chkCoach CheckBox of coach
    */
    public static void postRegister(
            final Context ctx,
            EditText etUsername,
            EditText etEmail,
            EditText etPassword,
            EditText etLanguage,
            CheckBox chkCoach
    ) {


        Map<String, String> map = AuthValidator.validateRegister(
                etUsername,
                etEmail,
                etPassword,
                etLanguage,
                chkCoach
        );

        if(map != null) {
            // create the API request and save the returned token into shared preferences
            createRequest(
                    ctx,
                    map,
                    "users/",
                    new VolleyCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject response) {

                            // successful response
                            Toast.makeText(ctx, "Success!",
                                    Toast.LENGTH_SHORT).show();

                            try {
                                SharedPrefManager.getInstance(ctx).userLogin(
                                        response.getString("token")
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(ctx, ListOfPlanActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);

                        }
                    }
            );
        } else {
            Toast.makeText(ctx, "Unable to login", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Send a POST request to login the user
     * @param ctx Context of application
     * @param username Username for user credentials
     * @param password Password for user credentials
     */
    public static void postLogin(final Context ctx, EditText username, EditText password) {

        // store all inputs into HashMap
        Map<String, String> map = AuthValidator.validateLogin(username, password);

        if(map != null) {
            // create the API request and save the returned token into shared preferences
            createRequest(
                    ctx,
                    map,
                    "auth",
                    new VolleyCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject response) {

                                // successful response
                                Toast.makeText(ctx, "Success!",
                                        Toast.LENGTH_SHORT).show();

                                try {
                                    SharedPrefManager.getInstance(ctx)
                                            .userLogin(response.getString("token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            Intent intent = new Intent(ctx, ListOfPlanActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ctx.startActivity(intent);

                        }
                    }
            );
        } else {
            Toast.makeText(ctx, "Unable to login", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Builds the request and sends it to the API server
     * @param ctx Context of application
     * @param params HashMap of user-entered data
     * @param urlExtension String specifying the API URL
     * @param callback Function called when the request is successful
     */
    private static void createRequest(final Context ctx, final Map<String, String> params, String urlExtension, final VolleyCallback callback) {

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
                                "Error retrieving data",
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

}
