package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PlansDetailFragment extends Fragment {

    public interface onPlansDetail {
        void deletePlan(String title);
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PLAN_TITLE = "planTitle";

    private String mPlanTitle;
    private EditText etPlanTitle;
    private Button btnDetailDelete;
    onPlansDetail plansDetail;

    public PlansDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlanTitle = getArguments().getString(ARG_PLAN_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plans_detail, container, false);
        etPlanTitle = v.findViewById(R.id.etPlanDetailTitle);
        btnDetailDelete = v.findViewById(R.id.btnDetailDelete);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            plansDetail = (PlansDetailFragment.onPlansDetail) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etPlanTitle.setText(mPlanTitle);

        btnDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansDetail.deletePlan(etPlanTitle.getText().toString());
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
