package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v4.app.Fragment;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListUserPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.UserPlanProgressDataModel;

import java.util.ArrayList;

public class UserPlanProgressFragment  extends Fragment {

    public interface onUserPlanProgressFragmentLoad {
        void loadUserPlanProgressAdapter(ArrayListUserPlanAdapter adapter, ArrayList<UserPlanProgressDataModel> data);
    }

}
