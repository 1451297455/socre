package com.spreadtrum.myapplication.test;

import android.content.Intent;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Created by Jinchao_1.Zhang on 2017/12/5.
 */
@RunWith(AndroidJUnit4.class)
public class testandro {
    private final String packagename = "com.andromeda.androbench2";
    private final String activity = "com.andromeda.androbench2.main";
    private final String appstart = " am start -n " + packagename + "/" + activity;
    private UiDevice device = null;

    @Before
    public void init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void test() throws IOException, InterruptedException {
        String a = device.executeShellCommand("getprop");
        if (a.contains("ro.com.google.gmsversion")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        Intent intent = new Intent();
    }

    @After
    public void end() {

    }
}
