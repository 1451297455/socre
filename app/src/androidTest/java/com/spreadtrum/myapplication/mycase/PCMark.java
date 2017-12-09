package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

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
 * Created by Jinchao_1.Zhang on 2017/10/20.
 */
@RunWith(AndroidJUnit4.class)
public class PCMark {
    private final String packagename = "com.futuremark.pcmark.android.benchmark";
    private final String activity = "com.futuremark.gypsum.activity.SplashPageActivity";
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
    public void test() throws RemoteException, InterruptedException, UiObjectNotFoundException {
        myUntil.openScreen();
        myUntil.wifiOn();
        myUntil.entraps(appstart);
        UiObject2 pcmark = device.wait(Until.findObject(By.res("com.futuremark.pcmark.android.benchmark:id/actionBarMenuItemText")), 1000);
        while (pcmark == null && i-- > 150) {
            Thread.sleep(2000);
            pcmark = device.wait(Until.findObject(By.res("com.futuremark.pcmark.android.benchmark:id/actionBarMenuItemText")), 1000);
        }
        assertTrue("应用崩溃", i > 150);
        pcmark.clickAndWait(Until.newWindow(), 5000);

        /*//debug
        UiObject2 ok = device.wait(Until.findObject(By.text("我的设备")), 1000);
        ok.clickAndWait(Until.newWindow(), 5000);
        UiObject2 lista = device.wait(Until.findObject(By.res("historyTable")), 5000);
        lista.clickAndWait(Until.newWindow(), 5000);
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("content"));
        scrollable.scrollToEnd(2);
        Thread.sleep(2000);
        myUntil.tookscreen(classname, "detail");
        UiObject2 parent = device.wait(Until.findObject(By.desc("操作系统版本")), 1000).getParent();
        String name, value;
        for (int j = 0; j < 10; j = j + 2) {
            name = parent.getChildren().get(j).getContentDescription();
            value = parent.getChildren().get(j + 1).getContentDescription();
            myitem = new item(name, value);
            list.add(myitem);
        }*/


        UiObject2 testcase = device.wait(Until.findObject(By.text("基准测试")), 1000);
        testcase.clickAndWait(Until.newWindow(), 5000);
        UiObject2 work = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(0);
        work.clickAndWait(Until.newWindow(), 5000);
        Thread.sleep(2000);
        work = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(0);
        if (work.getContentDescription().contains("安装(")) {
            work.clickAndWait(Until.newWindow(), 5000);
            Thread.sleep(2000);
            UiObject2 install = device.wait(Until.findObject(By.desc("3DMark Android UI")), 1000).getChildren().get(3);
            install.clickAndWait(Until.newWindow(), 5000);
            work = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(0);
            i = 200;
            while (work.getContentDescription().contains("正在安装 ") && i-- > 0) {
                Thread.sleep(10000);
                work = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(0);
            }
            assertTrue("网络请求超时", i > 0);
            UiObject2 run = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(2);
            run.clickAndWait(Until.newWindow(), 1000);
            UiObject2 result = device.wait(Until.findObject(By.desc("共享")), 5000);
            i = 200;
            while (result == null && i-- > 0) {
                Thread.sleep(10000);
                result = device.wait(Until.findObject(By.desc("共享")), 5000);
            }
            assertTrue("网络请求超时", i > 0);
            myUntil.tookscreen(classname, "Total");
            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("content"));
            scrollable.scrollToEnd(2);
            myUntil.tookscreen(classname, "detail");
            UiObject2 parent = device.wait(Until.findObject(By.res("content")), 1000).getChildren().get(10);
            String name, value;
            for (int j = 0; j < 10; j = j + 2) {
                name = parent.getChildren().get(j).getText();
                value = parent.getChildren().get(j + 1).getText();
                myitem = new item(name, value);
                list.add(myitem);
            }


        } else {
            UiObject2 run = device.wait(Until.findObject(By.res("content")), 5000).getChildren().get(1);
            run.clickAndWait(Until.newWindow(), 5000);
            UiObject2 result = device.wait(Until.findObject(By.desc("共享")), 5000);
            i = 200;
            while (result == null && i-- > 0) {
                Thread.sleep(10000);
                result = device.wait(Until.findObject(By.desc("共享")), 5000);
            }
            assertTrue("网络请求超时", i > 0);
            myUntil.tookscreen(classname, "Total");
            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("content"));
            scrollable.scrollToEnd(2);
            myUntil.tookscreen(classname, "detail");
            UiObject2 parent = device.wait(Until.findObject(By.desc("操作系统版本")), 1000).getParent();
            String name, value;
            for (int j = 0; j < 10; j = j + 2) {
                name = parent.getChildren().get(j).getContentDescription();
                value = parent.getChildren().get(j + 1).getContentDescription();
                myitem = new item(name, value);
                list.add(myitem);
            }
        }

    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }

}
