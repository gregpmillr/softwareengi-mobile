package com.softengi.mobcomp.softwareengi_mobile.Utils;

/**
 * Class used for filtering accelerometer values to only detect for step-like sensor changes
 */
public class SensorFilter {

    /**
     * Default constructor
     */
    private SensorFilter() {}

    /**
     * Add all values of the array into a single variable to return
     * @param array array of values aggregated
     * @return aggregated valued
     */
    static float sum(float[] array) {
        float retval = 0;
        for (int i = 0; i < array.length; i++) {
            retval += array[i];
        }
        return retval;
    }

    /**
     * Gets the difference between multiplication of two array indexes
     * @param arrayA First array
     * @param arrayB Second array
     * @return the difference between multiplication of two array indexes
     */
    public static float[] cross(float[] arrayA, float[] arrayB) {
        float[] retArray = new float[3];
        retArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
        retArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
        retArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
        return retArray;
    }

    /**
     * Used for normalization. Takes a square root out of sum of squares.
     * @param array Array to normalize
     * @return The sum of squares
     */
    static float norm(float[] array) {
        float retval = 0;
        for (int i = 0; i < array.length; i++) {
            retval += array[i] * array[i];
        }
        return (float) Math.sqrt(retval);
    }

    /**
     * Add all multiplication between two arrays
     * @param a First array
     * @param b Second array
     * @return The multiplied array
     */
    static float dot(float[] a, float[] b) {
        float retval = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
        return retval;
    }

    /**
     * Normalization
     * @param a Array to normalize
     * @return Normalized array
     */
    public static float[] normalize(float[] a) {
        float[] retval = new float[a.length];
        float norm = norm(a);
        for (int i = 0; i < a.length; i++) {
            retval[i] = a[i] / norm;
        }
        return retval;
    }
}
