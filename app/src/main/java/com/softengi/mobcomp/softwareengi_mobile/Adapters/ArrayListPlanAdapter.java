package com.softengi.mobcomp.softwareengi_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.DataModels.PlanDataModel;
import com.softengi.mobcomp.softwareengi_mobile.R;

import java.util.ArrayList;

public class ArrayListPlanAdapter extends ArrayAdapter<PlanDataModel> implements View.OnClickListener {

    private ArrayList<PlanDataModel> mData;
    private Context mCtx;

    private static class ViewHolder {
        TextView txtTitle;
    }

    public ArrayListPlanAdapter(ArrayList<PlanDataModel> data, Context ctx) {
        super(ctx, R.layout.plan_row_item, data);
        mData = data;
        mCtx = ctx;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        PlanDataModel dataModel = (PlanDataModel)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data item for this position
        PlanDataModel dataModel = (PlanDataModel) getItem(position);
        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.plan_row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.lvItemTitle);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mCtx, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());

        // return completed view to be rendered on screen
        return convertView;
    }

}
