package com.softengi.mobcomp.softwareengi_mobile;

import com.softengi.mobcomp.softwareengi_mobile.Actions.AuthAction;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Testing for the login.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginUnitTest {

    /**
     * Tests if user can login
     * @throws Exception
     */
    @Test
    public void testLoginInfo() throws Exception {
        Assert.assertFalse(AuthAction.checkLogin("not bob", "wrong"));
        Assert.assertFalse(AuthAction.checkLogin("bob", "wrong"));
        Assert.assertTrue(AuthAction.checkLogin("bob", "pass"));
    }
}