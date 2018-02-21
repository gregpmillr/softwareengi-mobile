package com.softengi.mobcomp.softwareengi_mobile.Controllers;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softengi.mobcomp.softwareengi_mobile.ProfileActivity;
import com.softengi.mobcomp.softwareengi_mobile.Models.User;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthController {

    static public void postLogin(
            final Context context,
            EditText etUsername,
            EditText etPassword
    ) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        // this is stub////////////////////////////////////////////////////////////
        if(!username.equals("bob") || !password.equals("pass")){
            etPassword.setError("Incorrect username or password");
            etPassword.requestFocus();
            return;
        }

        User user = new User(
                0,
                "bob",
                "bob@email.com"
        );

        // store the user in shared prefs
        SharedPrefManager.getInstance(context).userLogin(user);

        // go to profile
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        return;
        // end of stub/////////////////////////////////////////////////////////////////

        /* The real code not needed yet

        RequestQueue queue = Volley.newRequestQueue(context);

        final String url = "http://httpbin.org/post";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getBoolean("success")) {
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
                                );

                                // store the user in shared prefs
                                SharedPrefManager.getInstance(context).userLogin(user);

                                // go to profile
                                context.startActivity(new Intent(context, ProfileActivity.class));
                            }


                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Error logging in",Toast.LENGTH_SHORT);

                        Log.d("Error.Response", error.getMessage());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        queue.add(postRequest);*/

    }


}
