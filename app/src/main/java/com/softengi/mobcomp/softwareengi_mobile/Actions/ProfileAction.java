package com.softengi.mobcomp.softwareengi_mobile.Actions;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Utils.ProfileParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileAction {

    private static final String url = "users/";

    public static void postUpdate(final Context ctx, TextView username, EditText email, EditText language, CheckBox coach, final SuccessListener onSuccess) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username.getText().toString());
        map.put("email", email.getText().toString());
        map.put("language", language.getText().toString());
        map.put("coach", String.valueOf(coach.isChecked()));

        RequestAction.createPostRequest(ctx, map, url.concat("update"), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                onSuccess.successful();
            }
        });
    }

    public static void getProfile(final Context ctx, final TextView tvTotalSteps,
                                  final TextView tvTotalPlans,
                                  final TextView tvTotalTeams,
                                  final TextView tvRecentPlans,
                                  final TextView tvRecentSteps,
                                  final ProfileParser callback) {

        RequestAction.createGetRequest(ctx, url.concat(SharedPrefManager.getInstance(ctx).getUsername()), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    // tvTotalSteps.setText();
                    tvTotalSteps.setText(result.getString("total_steps"));
                    tvTotalPlans.setText(result.getString("total_plans"));
                    tvTotalTeams.setText(result.getString("total_teams"));
                    tvRecentPlans.setText(result.getString("recent_plans"));
                    tvRecentSteps.setText(result.getString("recent_steps"));

                    callback.onSuccessResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ctx, "Profile receieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
