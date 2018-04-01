package com.softengi.mobcomp.softwareengi_mobile.DataModels;

/**
 * Data model for a User's progress on a plan
 */
public class UserPlanProgressDataModel {

    // member variables
    private String mUsername;
    private String mStepsContributed;

    /**
     * Constructor for User's Plan Progress data model
     * @param username Username of user
     * @param stepsContributed Steps contributed to the plan
     */
    public UserPlanProgressDataModel(String username, String stepsContributed) {
        mUsername = username;
        mStepsContributed = stepsContributed;
    }

    /**
     * Get the Username of this user's plan progress
     * @return the username of the current user
     */
    public String getUsername() { return mUsername; }

    /**
     * Get the steps contributed to the plan of this user's progress
     * @return the steps contributed to the plan of this user's progress
     */
    public String getStepsContributed() { return mStepsContributed; }

}
