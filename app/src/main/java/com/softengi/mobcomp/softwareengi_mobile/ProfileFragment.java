package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private EditText etProfileUsername, etProfileEmail, etProfileLanguage, etProfileCoach;
    private Button btnLogout, btnProfileEmail, btnProfileLanguage, btnProfileCoach;

    public interface onUpdateProfile {
        void updateEmail();
        void updateLanguage();
        void updateCoach();
    }

    onUpdateProfile onUpdateProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        etProfileUsername    = v.findViewById(R.id.etProfileUsername);
        etProfileEmail       = v.findViewById(R.id.etProfileEmail);
        etProfileLanguage    = v.findViewById(R.id.etProfileLanguage);
        etProfileCoach       = v.findViewById(R.id.etProfileCoach);
        btnLogout            = v.findViewById(R.id.btnLogout);
        btnProfileEmail      = v.findViewById(R.id.btnProfileEmail);
        btnProfileLanguage   = v.findViewById(R.id.btnProfileLanguage);
        btnProfileCoach      = v.findViewById(R.id.btnProfileCoach);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etProfileUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        etProfileEmail.setText(SharedPrefManager.getInstance(getContext()).getEmail());
        etProfileCoach.setText(SharedPrefManager.getInstance(getContext()).getCoach());
        etProfileLanguage.setText(SharedPrefManager.getInstance(getContext()).getLanguage());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onUpdateProfile = (ProfileFragment.onUpdateProfile) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

}
