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
import com.softengi.mobcomp.softwareengi_mobile.R;
import com.softengi.mobcomp.softwareengi_mobile.Utils.RequestQueueSingleton;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    private static final String url = "users/update";

    public static void postUpdate(final Context ctx, EditText username, EditText email, EditText language, EditText coach, final SuccessListener onSuccess) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username.getText().toString());
        map.put("email", email.getText().toString());
        map.put("language", language.getText().toString());
        map.put("coach", coach.getText().toString());

        RequestController.createPostRequest(ctx, map, url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                onSuccess.successful();
            }
        });
    }

}
