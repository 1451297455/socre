package com.spreadtrum.myapplication.help;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

/**
 * Created by Jinchao_1.Zhang on 2017/9/22.
 */

public class Watchers {
    private final UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    public void Registwatchers(String name, final String res) {

        device.registerWatcher(name, new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 Ok = device.wait(Until.findObject(By.res(res)), 1000);
                if (Ok != null) {
                    Ok.clickAndWait(Until.newWindow(), 1000);
                    return true;
                }
                return false;
            }
        });
        device.registerWatcher(name, new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 Ok = device.wait(Until.findObject(By.text(res)), 1000);
                if (Ok != null) {
                    Ok.clickAndWait(Until.newWindow(), 1000);
                    return true;
                }
                return false;
            }
        });
        device.registerWatcher(name, new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 Ok = device.wait(Until.findObject(By.descContains(res)), 1000);
                if (Ok != null) {
                    Ok.clickAndWait(Until.newWindow(), 1000);
                    return true;
                }
                return false;
            }
        });
        device.registerWatcher(name, new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 Ok = device.wait(Until.findObject(By.textContains(res)), 1000);
                if (Ok != null) {
                    Ok.clickAndWait(Until.newWindow(), 1000);
                    return true;
                }
                return false;
            }
        });
        if (device.hasWatcherTriggered(name)) {
            device.resetWatcherTriggers();
        }
    }
}
