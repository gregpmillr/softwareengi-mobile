package com.softengi.mobcomp.softwareengi_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.R;

import java.util.ArrayList;

/**
 * List adapter for teams.
 */
public class ArrayListTeamAdapter extends ArrayAdapter<TeamDataModel> implements View.OnClickListener {

    // member variables
    private ArrayList<TeamDataModel> mData;
    private Context mCtx;

    /**
     * ViewHolder for UI elements
     */
    private static class ViewHolder {
        TextView txtName;
    }

    /**
     * Constructor for this adapter
     * @param data DataModels used to populate the adapter
     * @param ctx Context of application
     */
    public ArrayListTeamAdapter(ArrayList<TeamDataModel> data, Context ctx) {
        super(ctx, R.layout.plan_row_item, data);
        mData = data;
        mCtx = ctx;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        TeamDataModel dataModel = (TeamDataModel)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data item for this position
        TeamDataModel dataModel = (TeamDataModel ) getItem(position);
        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.plan_row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.lvItemTitle);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mCtx, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());

        // return completed view to be rendered on screen
        return convertView;
    }

}
