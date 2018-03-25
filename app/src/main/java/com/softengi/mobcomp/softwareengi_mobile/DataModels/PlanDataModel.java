package com.softengi.mobcomp.softwareengi_mobile.DataModels;

public class PlanDataModel {

    String mTitle;
    String mRequiredSteps;
    String mId;

    public PlanDataModel(String title, String requiredSteps, String id) {
        mTitle = title;
        mRequiredSteps = requiredSteps;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getRequiredSteps() {
        return mRequiredSteps;
    }

    public String getId() {
        return mId;
    }

}
