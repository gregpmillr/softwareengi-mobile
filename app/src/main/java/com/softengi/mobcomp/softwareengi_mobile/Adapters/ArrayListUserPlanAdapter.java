package com.softengi.mobcomp.softwareengi_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.DataModels.UserPlanProgressDataModel;
import com.softengi.mobcomp.softwareengi_mobile.R;

import java.util.ArrayList;

public class ArrayListUserPlanAdapter extends ArrayAdapter<UserPlanProgressDataModel> implements View.OnClickListener{

    private ArrayList<UserPlanProgressDataModel> mData;
    private Context mCtx;

    private static class ViewHolder {
        TextView txtUsername;
        TextView txtContributedSteps;
    }

    public ArrayListUserPlanAdapter(ArrayList<UserPlanProgressDataModel> data, Context ctx) {
        super(ctx, R.layout.user_plan_progress_row_item, data);
        mData = data;
        mCtx = ctx;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        UserPlanProgressDataModel dataModel = (UserPlanProgressDataModel)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data item for this position
        UserPlanProgressDataModel dataModel = (UserPlanProgressDataModel) getItem(position);
        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_plan_progress_row_item, parent, false);
            viewHolder.txtUsername = (TextView) convertView.findViewById(R.id.lvUserPlanProgressUsername);
            viewHolder.txtContributedSteps = (TextView) convertView.findViewById(R.id.lvUserPlanProgressContributedSteps);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mCtx, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtUsername.setText(dataModel.getUsername());

        viewHolder.txtContributedSteps.setText(getContext().getResources().getString(R.string.contributed_steps).concat(dataModel.getStepsContributed()));

        // return completed view to be rendered on screen
        return convertView;
    }
}
