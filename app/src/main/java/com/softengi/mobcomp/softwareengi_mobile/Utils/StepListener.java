package com.softengi.mobcomp.softwareengi_mobile.Utils;

/**
 * Listens for steps
 */
public interface StepListener {

    /**
     * Called when a sep is taken
     * @param timeNs Time in n
     */
    void step(long timeNs);
}
