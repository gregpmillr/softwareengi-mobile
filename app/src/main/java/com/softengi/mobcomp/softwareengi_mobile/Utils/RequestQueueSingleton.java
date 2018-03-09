package com.softengi.mobcomp.softwareengi_mobile.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Volley's RequestQueue implemented as a Singleton to ensure only one instance of this class
 * exists at one time
 */
public class RequestQueueSingleton {

    private static RequestQueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestQueueSingleton(Context context){

        mCtx = context.getApplicationContext();
        mRequestQueue = getRequestQueue();

    }

    /**
     * Synchronized method for thread-safety in case the instance is accessed by another thread.
     * Ensures only one instance is created by returning the member variable instance. If the
     * instance has not been created yet, instantiate a new instance of this class.
     * @param context Application's context
     * @return current instance of the RequestQueue
     */
    public static synchronized RequestQueueSingleton getInstance(Context context) {

        if(mInstance == null) {
            mInstance = new RequestQueueSingleton(context.getApplicationContext());
        }
        return mInstance;

    }

    /**
     * Current RequestQueue that is a member variable of this class. If the object is currently
     * null, create a new one, or else use the previously created RequestQueue.
     * @return the instance variable RequestQueue
     */
    public RequestQueue getRequestQueue() {

        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;

    }

    /**
     * Adds a new item to the request queue to be executed
     * @param req Request object to add to the request.
     * @param <T> Generic return
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
