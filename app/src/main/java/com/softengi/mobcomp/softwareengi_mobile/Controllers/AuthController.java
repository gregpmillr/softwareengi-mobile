package com.softengi.mobcomp.softwareengi_mobile.Controllers;
import android.content.Context;
import android.text.TextUtils;
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

    private boolean success = false;

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

    public static boolean postLogin(
            final Context context,
            EditText etUsername,
            EditText etPassword
    ) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
        }

        if(TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
        }

        if(!checkLogin(username,password)){
            etPassword.setError("Incorrect username or password");
            etPassword.requestFocus();
        }

        String url = "http://192.168.2.14:8000/auth";

        return getResponse(url, null,
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

    static boolean getResponse(String url, JSONObject jsonValue, final VolleyCallback callback, final Context mCtx, final String username, final String password) {
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

}
