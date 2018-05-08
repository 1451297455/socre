package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

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

import static org.junit.Assert.assertTrue;

/**
 * Created by Jinchao_1.Zhang on 2017/9/14.
 */

@RunWith(AndroidJUnit4.class)
public class BenchmarkPI {
    private String packagename = "gr.androiddev.BenchmarkPi";
    private String activity = "gr.androiddev.BenchmarkPi.BenchmarkPi";
    private String appstart = " am start -n " + packagename + "/" + activity;
    private String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int i = 200;
    private String classname = getClass().getSimpleName();
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
        myUntil.wifiOff();
        myUntil.entraps(appstart);

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

        UiObject2 run = device.wait(Until.findObject(By.res("gr.androiddev.BenchmarkPi:id/Button01")), 1000);
        run.clickAndWait(Until.newWindow(), 1000);
        SystemClock.sleep(3000);
        UiObject2 message = device.wait(Until.findObject(By.res("android:id/message")), 1000);
        String[] pistr = message.getText().split(" ");
        String pi = pistr[4];
        item myitem = new item("pi", pi);
        list.add(myitem);
        myUntil.tookscreen(classname, "PI");
    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        System.out.println(getClass().getSimpleName());
        myUntil.Writexml(list, getClass().getSimpleName());
        myUntil.entraps(appkill);
    }

}