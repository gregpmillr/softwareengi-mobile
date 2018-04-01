package com.softengi.mobcomp.softwareengi_mobile.DataModels;

public class UserPlanProgressDataModel {

    private String mUsername;
    private String mStepsContributed;

    public UserPlanProgressDataModel(String username, String stepsContributed) {
        mUsername = username;
        mStepsContributed = stepsContributed;
    }

    public String getUsername() { return mUsername; }

    public String getStepsContributed() { return mStepsContributed; }

}
