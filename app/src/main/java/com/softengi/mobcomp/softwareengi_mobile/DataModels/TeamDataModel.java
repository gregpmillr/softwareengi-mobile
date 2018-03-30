package com.softengi.mobcomp.softwareengi_mobile.DataModels;

import java.util.ArrayList;

public class TeamDataModel {

    String mName;
    String mId;
    ArrayList<String> mMembers;

    public TeamDataModel(String name, ArrayList<String> members, String id) {
        mName = name;
        mMembers = members;
        mId = id;
    }

    public String getName() { return mName; }

    public String getId() { return mId; }

    public ArrayList<String> getMembers() { return mMembers; }

}
