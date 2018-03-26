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
public class Vellamo {
    private final String packagename = "com.quicinc.vellamo";
    private final String activity = "com.quicinc.vellamo.main.MainActivity";
    private final String appstart = " am start -n " + packagename + "/" + activity;
    private final String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int x, y, i = 200;
    private final String classname = getClass().getSimpleName();
    private item myitem = null;
    private ArrayList<item> list = null;

    @Before
    public void init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        myUntil = new MyUntil();
        list = new ArrayList<>();
        x = device.getDisplayWidth();
        y = device.getDisplayHeight();

    }

    @Test
    public void test() throws RemoteException, InterruptedException, UiObjectNotFoundException {
        myUntil.openScreen();
        myUntil.wifiOn();
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

        UiObject2 accept = device.wait(Until.findObject(By.text("接受")), 1000);
        if (accept != null) {
            accept.clickAndWait(Until.newWindow(), 1000);
        }
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.quicinc.vellamo:id/cards_container"));
        scrollable.scrollTextIntoView("浏览器");
        UiObject2 broswer = device.wait(Until.findObject(By.text("浏览器")), 1000);
        UiObject2 run = broswer.getParent().getParent().getChildren().get(3).getChildren().get(0);
        run.clickAndWait(Until.newWindow(), 1000);
        UiObject2 know = device.wait(Until.findObject(By.text("知道了")), 1000);
        if (know != null) {
            know.clickAndWait(Until.newWindow(), 1000);
        }
        UiObject2 result = device.wait(Until.findObject(By.text("与其他设备进行比较?")), 1000);
        i = 200;
        while (result == null && i-- > 0) {
            Thread.sleep(5000);
            result = device.wait(Until.findObject(By.text("与其他设备进行比较?")), 1000);
        }
        UiObject2 cancle = device.wait(Until.findObject(By.res("com.quicinc.vellamo:id/button_no")), 1000);
        cancle.clickAndWait(Until.newWindow(), 1000);
        Thread.sleep(2000);
        myUntil.tookscreen(classname, "total");
        device.swipe(x / 2, y / 5, x / 2, y / 5 * 4, 20);
        SystemClock.sleep(2000);
        UiScrollable scrollable1 = new UiScrollable(new UiSelector().resourceId("com.quicinc.vellamo:id/score_pane_cards"));
        scrollable1.scrollTextIntoView("各项子测试得分");
        UiObject2 socreitem = device.wait(Until.findObject(By.text("各项子测试得分")), 1000);
        socreitem.clickAndWait(Until.newWindow(), 1000);
        myUntil.tookscreen(classname, "socre1");
        device.swipe(x / 2, y / 5 * 4, x / 2, y / 5, 20);
        myUntil.tookscreen(classname, "socre2");

    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
//        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }

}
