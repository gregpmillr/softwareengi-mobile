package com.softengi.mobcomp.softwareengi_mobile.DataModels;

import java.util.ArrayList;

/**
 * Data model for Teams
 */
public class TeamDataModel {

    // member variables
    private String mName;
    private String mId;
    private ArrayList<String> mMembers;

    /**
     * Constructor for Team data mode
     * @param name Name of team
     * @param members Members of this team
     * @param id Id of this team
     */
    public TeamDataModel(String name, ArrayList<String> members, String id) {
        mName = name;
        mMembers = members;
        mId = id;
    }

    /**
     * Constructor for Team data model
     * @param name Name of team
     * @param id Id of team
     */
    public TeamDataModel(String name, String id) {
        mName = name;
        mId = id;
    }

    /**
     * Get the name of this team
     * @return the name of this team
     */
    public String getName() { return mName; }

    /**
     * Get the Id of this team
     * @return the Id of this team
     */
    public String getId() { return mId; }

    /**
     * Get the members of this team
     * @return the members of this team
     */
    public ArrayList<String> getMembers() { return mMembers; }

}
