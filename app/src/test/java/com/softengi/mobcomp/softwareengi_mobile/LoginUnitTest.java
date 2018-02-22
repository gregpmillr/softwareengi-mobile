package com.softengi.mobcomp.softwareengi_mobile;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.AuthController;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void testLogin() throws Exception {
        Assert.assertFalse(AuthController.checkLogin("not bob", "wrong"));
        Assert.assertFalse(AuthController.checkLogin("bob", "wrong"));
        Assert.assertTrue(AuthController.checkLogin("bob", "pass"));
    }
}