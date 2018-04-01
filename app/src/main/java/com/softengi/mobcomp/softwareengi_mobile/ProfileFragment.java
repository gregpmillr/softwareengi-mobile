package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

/**
 * Fragment representing a profile
 */
public class ProfileFragment extends Fragment {

    private EditText etProfileEmail, etProfileLanguage;
    private TextView tvProfileUsername, tvTotalSteps, tvTotalPlans, tvTotalTeams, tvRecentSteps, tvRecentPlans;
    private Button btnLogout, btnProfileUpdate;
    private CheckBox chkProfileCoach;

    /**
     * Interface for MainActivity to implement
     */
    public interface onProfileListener {
        /**
         * Updates a profile
         * @param username Username of user
         * @param email New or old email
         * @param language New or old language
         * @param coach New or old coach value
         */
        void updateProfile(TextView username, EditText email, EditText language, CheckBox coach);

        /**
         * Loads profile data
         * @param tvTotalSteps Total steps
         * @param tvTotalPlans Total plans
         * @param tvTotalTeams Total teams
         * @param tvRecentSteps Total steps. Past 7 days
         * @param tvRecentPlans Total plans. Past 7 days
         */
        void loadProfile(TextView tvTotalSteps, TextView tvTotalPlans, TextView tvTotalTeams,
                         TextView tvRecentSteps, TextView tvRecentPlans);

        /**
         * Logs the user out
         */
        void logout();
    }

    onProfileListener mProfileListener;

    /**
     * Required default constructor
     */
    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        tvProfileUsername    = v.findViewById(R.id.tvProfileUsername);
        etProfileEmail       = v.findViewById(R.id.etProfileEmail);
        etProfileLanguage    = v.findViewById(R.id.etProfileLanguage);
        chkProfileCoach      = v.findViewById(R.id.chkProfileCoach);
        btnProfileUpdate     = v.findViewById(R.id.btnProfileUpdate);
        btnLogout            = v.findViewById(R.id.btnLogout);
        tvTotalSteps         = v.findViewById(R.id.tvTotalSteps);
        tvTotalPlans         = v.findViewById(R.id.tvTotalPlans);
        tvTotalTeams         = v.findViewById(R.id.tvTotalTeams);
        tvRecentPlans        = v.findViewById(R.id.tvRecentPlans);
        tvRecentSteps        = v.findViewById(R.id.tvRecentSteps);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvProfileUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        etProfileEmail.setText(SharedPrefManager.getInstance(getContext()).getEmail());
        chkProfileCoach.setChecked(Boolean.valueOf(SharedPrefManager.getInstance(getContext()).getCoach()));
        etProfileLanguage.setText(SharedPrefManager.getInstance(getContext()).getLanguage());
        mProfileListener.loadProfile(tvTotalSteps, tvTotalPlans, tvTotalTeams, tvRecentSteps, tvRecentPlans);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfileListener.logout();
            }
        });

        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileListener.updateProfile(tvProfileUsername, etProfileEmail, etProfileLanguage, chkProfileCoach);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mProfileListener = (ProfileFragment.onProfileListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

}
