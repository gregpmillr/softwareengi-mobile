package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

public class ProfileFragment extends Fragment {

    private EditText etProfileUsername, etProfileEmail, etProfileLanguage, etProfileCoach;
    private Button btnLogout, btnProfileUpdate;

    public interface onProfileListener {
        void updateProfile(EditText username, EditText email, EditText language, EditText coach);
        void logout();
    }

    onProfileListener mProfileListener;

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
        btnProfileUpdate     = v.findViewById(R.id.btnProfileUpdate);
        btnLogout            = v.findViewById(R.id.btnLogout);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etProfileUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        etProfileEmail.setText(SharedPrefManager.getInstance(getContext()).getEmail());
        etProfileCoach.setText(SharedPrefManager.getInstance(getContext()).getCoach());
        etProfileLanguage.setText(SharedPrefManager.getInstance(getContext()).getLanguage());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfileListener.logout();
            }
        });

        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileListener.updateProfile(etProfileUsername, etProfileEmail, etProfileCoach, etProfileLanguage);
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
