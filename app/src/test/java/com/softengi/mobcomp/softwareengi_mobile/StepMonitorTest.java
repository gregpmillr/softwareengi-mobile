package com.softengi.mobcomp.softwareengi_mobile;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

/**
 * Testing for step sensor detection.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class StepMonitorTest{

    //TODO
    @Test
    public void testDetection() throws Exception {
/*
        SensorEvent mockEvent = mock(SensorEvent.class);
        mockEvent.values[0] = 324;
        mockEvent.values[1] = 345;
        mockEvent.values[2] = 634;
        activity.onSensorChanged(mockEvent);
        */
    }

    @Test
    public void testPaceCalculation() {
        LinkedList<Integer> timestamps = new LinkedList<>();
        timestamps.add(3);
        timestamps.add(3);
        timestamps.add(7);
        timestamps.add(10);
        timestamps.add(18);
        Assert.assertEquals(3.0, StepFragment.calculatePace(timestamps));
    }
}
