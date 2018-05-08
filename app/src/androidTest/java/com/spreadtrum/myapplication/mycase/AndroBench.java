package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.support.test.uiautomator.*;

import com.spreadtrum.myapplication.help.MyUntil;
import com.spreadtrum.myapplication.help.item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by Jinchao_1.Zhang on 2017/10/20.
 */
@RunWith(AndroidJUnit4.class)
public class AndroBench {

    private final String packagename = "com.andromeda.androbench2";
    private final String activity = "com.andromeda.androbench2.main";
    private final String appstart = " am start -n " + packagename + "/" + activity;
    private final String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int i = 200;
    private final String classname = getClass().getSimpleName();
    private item myitem = null;
    private ArrayList<item> list = null;

    @Before
    public void init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        myUntil = new MyUntil();
        list = new ArrayList<>();

    }

    @Test
    public void test() throws Exception {
        myUntil.openScreen();
        myUntil.wifiOn();
        myUntil.entraps(appstart);
        Thread.sleep(2000);
        device.registerWatcher("batterDialog", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 yes = device.wait(Until.findObject(By.text("确定")), 1000);
                if (yes != null) {
                    yes.clickAndWait(Until.newWindow(), 2000);
                    return true;
                }
                return false;
            }
        });
        /*UiObject2 run = device.wait(Until.findObject(By.res("com.andromeda.androbench2:id/btnStartingBenchmarking")), 1000);
        if (run != null) {
            int x = run.getVisibleBounds().centerX();
            int y = run.getVisibleBounds().centerY();
            Log.d("exsit", x + ":" + y);
            device.click(x, y);
            run.clickAndWait(Until.newWindow(), 1200);
            UiObject2 yes = device.wait(Until.findObject(By.text("Yes")), 1000);
            yes.clickAndWait(Until.newWindow(), 1000);
        } else {
            device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 2);
            Thread.sleep(2000);
            UiObject2 yes = device.wait(Until.findObject(By.text("Yes")), 1000);
            yes.clickAndWait(Until.newWindow(), 1000);
        }
*/
//        UiObject2 run = device.wait(Until.findObject(By.res("com.andromeda.androbench2:id/btnStartingBenchmarking")), 1000);
//        if (run != null) {
//            int x = run.getVisibleBounds().centerX();
//            int y = run.getVisibleBounds().centerY();
//            Log.d("exsit", x + ":" + y);
//            run.clickAndWait(Until.newWindow(), 1200);
//            device.click(x + 1, y - 1);
//        }
        device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 2);
        device.click(device.getDisplayWidth() / 2, 467);
        Thread.sleep(2000);
        UiObject2 yes = device.wait(Until.findObject(By.text("Yes")), 1000);
        yes.clickAndWait(Until.newWindow(), 1000);
        UiObject2 result = device.wait(Until.findObject(By.text("Cancel")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(5000);
            result = device.wait(Until.findObject(By.text("Cancel")), 1000);
        }
        result.clickAndWait(Until.newWindow(), 1000);

        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("android:id/content"));
        if (scrollable == null) {
            scrollable = new UiScrollable(new UiSelector().resourceId("com.andromeda.androbench2:id/TestingView"));
        }
        device.swipe(device.getDisplayWidth() / 2, device.getDisplayHeight() / 5 * 4, device.getDisplayWidth() / 2, device.getDisplayHeight() / 5, 40);
        SystemClock.sleep(1000);
        getdate(scrollable, "Sequential Read");
        getdate(scrollable, "Sequential Write");
        getdate(scrollable, "Random Read");
        getdate(scrollable, "Random Write");
        getdate(scrollable, "SQLite Insert");
        getdate(scrollable, "SQLite Update");
        getdate(scrollable, "SQLite Delete");


    }

    private void getdate(UiScrollable scrollable, String key) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(key);
        if (key.equals("SQLite Delete")) {
            device.swipe(device.getDisplayWidth() / 2, device.getDisplayHeight() / 5 * 4, device.getDisplayWidth() / 2, device.getDisplayHeight() / 5, 40);
        }
        UiObject2 name = device.wait(Until.findObject(By.text(key)), 1000);
        String value = name.getParent().getChildren().get(1).getText().split(" ")[0];
        myitem = new item(name.getText(), value);
        list.add(myitem);
        myUntil.tookscreen(classname, key);
    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);

    }


}
