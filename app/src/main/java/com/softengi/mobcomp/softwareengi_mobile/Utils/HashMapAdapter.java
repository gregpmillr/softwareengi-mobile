package com.softengi.mobcomp.softwareengi_mobile.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;

public class HashMapAdapter extends BaseAdapter {

    private Context mCtx;
    private HashMap mData;
    private String[] mKeys;

    public HashMapAdapter(Context ctx, HashMap<Integer,String> data) {
        mCtx = ctx;
        mData = data;
        mKeys = data.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String key = mKeys[position];
        String value = getItem(position).toString();

        // do view stuff here

        return convertView;
    }
}
