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

public class AuthController {

    /**
     * Checks if user info is in DB and can login.
     * @param user Username.
     * @param pass Password.
     * @return Whether data in in DB.
     */
    public static boolean checkLogin(String user, String pass){
        if(!user.equals("bob") || !pass.equals("pass")){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Checks if all fields are entered when registering
     * @param username Username of user to register
     * @param password Password of user to register
     * @param email Email of user to register
     * @param language Preferred language of user to register
     * @return Whether validation passed
     */
    public static boolean checkRegister(
            String username, String password, String email, String language) {

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || language.isEmpty()) {
            return false;
        }

        return true;

    }

    /**
     * POST request to API server
     * @param context Current context
     * @param etUsername Username of user to login
     * @param etPassword Password of user to login
     * @return Whether the user is logged in or not
     */
    public static boolean postLogin(
            final Context context,
            EditText etUsername,
            EditText etPassword
    ) {

        String url = "http://192.168.2.14:8000/auth";
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        boolean passedValidation = true;

        if(TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            passedValidation = false;
        }


        if(!passedValidation) {
            Toast.makeText(context,"Unable to register!",
                    Toast.LENGTH_SHORT);
           return false;
        }

        return getLoginResponse(url, null,
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
                }, context, username, password);

    }

    /**
     * Get the response from POST request when logging in
     * @param url URL for API endpoint
     * @param jsonValue Json value
     * @param callback Callback when call is successful
     * @param mCtx Current context
     * @param username Username of user to login
     * @param password Password of user to login
     * @return whether the request was successful (200) or not (400)
     */
    static boolean getLoginResponse(String url, JSONObject jsonValue, final VolleyCallback callback, final Context mCtx, final String username, final String password) {
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
                params.put("password", password);

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

    /**
     *
     * @param context Current context
     * @param etUsername Username to register
     * @param etEmail Email to register
     * @param etPassword Password to register
     * @param etLanguage Language to register
     * @param chkCoach Whether the user is a coach or not
     * @return Whether the POST request is successful or not
     */
    public static boolean postRegister(
            final Context context,
            EditText etUsername,
            EditText etEmail,
            EditText etPassword,
            EditText etLanguage,
            CheckBox chkCoach
    ) {

        final String username = etUsername.getText().toString();
        final String email    = etEmail.getText().toString();
        final String language = etLanguage.getText().toString();
        final String password = etPassword.getText().toString();
        final String coach    = chkCoach.isChecked() ? "true" : "false";
        String url = "http://192.168.2.14:8000/users";
        boolean passedValidation = true;

        if(TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(language)) {
            etLanguage.setError("Please enter your language");
            etLanguage.requestFocus();
            passedValidation = false;
        }

        if(TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            passedValidation = false;
        }

        if(!passedValidation) {
            Toast.makeText(context,"Unable to register!",
                    Toast.LENGTH_SHORT);
            return false;
        }

        return getRegisterResponse(url, null,
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
                }, context, username, email, password, coach, language);

    }

    /**
     * Get the response from the POST request to register users
     * @param url URL of API endpoint
     * @param jsonValue Json value
     * @param callback Callback for when POST request is successful
     * @param mCtx Current context
     * @param username Username of user to register
     * @param email Email of user to register
     * @param password Password of user to register
     * @param coach Whether the user is a coach or not
     * @param language Preferred language of the user
     * @return Whether the POST request was successful or not
     */
    static boolean getRegisterResponse(String url, JSONObject jsonValue, final VolleyCallback callback, final Context mCtx, final String username, final String email, final String password, final String coach, final String language) {
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
                params.put("password", password);
                params.put("coach", coach);
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
