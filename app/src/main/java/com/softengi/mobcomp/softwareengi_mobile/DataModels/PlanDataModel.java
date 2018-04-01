package com.softengi.mobcomp.softwareengi_mobile.DataModels;

/**
 * Data model for Plans
 */
public class PlanDataModel {

    // member variables
    private String mTitle;
    private String mRequiredSteps;
    private String mId;

    /**
     * Constructor for Plan Data Model
     * @param title Title for plan
     * @param requiredSteps Required steps for plan
     * @param id Id for plan
     */
    public PlanDataModel(String title, String requiredSteps, String id) {
        mTitle = title;
        mRequiredSteps = requiredSteps;
        mId = id;
    }

    /**
     * Get the title of this plan
     * @return the title of this plan
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the required steps of this plan
     * @return the required steps of this plan
     */
    public String getRequiredSteps() {
        return mRequiredSteps;
    }

    /**
     * Get the id of this plan
     * @return the id of this plan
     */
    public String getId() {
        return mId;
    }

}
