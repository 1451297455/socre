package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.spreadtrum.myapplication.help.MyUntil;
import com.spreadtrum.myapplication.help.Watchers;
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
public class Quadrant {


    private String packagename = "com.aurorasoftworks.quadrant.ui.professional";
    private String activity = "com.aurorasoftworks.quadrant.ui.professional.QuadrantProfessionalLauncherActivity";
    private String appstart = " am start -n " + packagename + "/" + activity;
    private String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private Watchers myWatch = null;
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
        device.registerWatcher("ok", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 Ok = device.wait(Until.findObject(By.res("android:id/button1")), 1000);
                if (Ok != null) {
                    Ok.clickAndWait(Until.newWindow(), 1000);
                    return true;
                }
                return false;
            }
        });
        UiObject2 run = device.wait(Until.findObject(By.text("Run full benchmark")), 1000);
        run.clickAndWait(Until.newWindow(), 1000);
        UiObject2 result = device.wait(Until.findObject(By.res("com.aurorasoftworks.quadrant.ui.professional:id/chart")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(1000);
            result = device.wait(Until.findObject(By.res("com.aurorasoftworks.quadrant.ui.professional:id/chart")), 1000);
        }
        myUntil.tookscreen(getClass().getSimpleName(), "Quadrant");


    }

    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
//        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
