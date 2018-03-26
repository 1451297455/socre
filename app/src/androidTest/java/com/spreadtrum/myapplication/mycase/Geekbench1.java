package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.widget.Toast;

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

import static junit.framework.Assert.assertTrue;

/**
 * Created by Jinchao_1.Zhang on 2017/10/20.
 */
@RunWith(AndroidJUnit4.class)
public class Geekbench1 {
    private final String packagename = "com.primatelabs.geekbench";
    private final String activity = "com.primatelabs.geekbench.HomeActivity";
    private final String appstart = " am start -n " + packagename + "/" + activity;
    private final String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int x, y, i = 600;
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

        UiObject2 accept = device.wait(Until.findObject(By.text("同意")), 5000);
        if (accept != null) {
            accept.clickAndWait(Until.newWindow(), 5000);
        }
        UiObject2 run = device.wait(Until.findObject(By.res("com.primatelabs.geekbench:id/runCpuBenchmarks")), 1000);
        if (run != null) {
            run.clickAndWait(Until.newWindow(), 1000);
        }
        UiObject2 netweak = null;
        UiObject2 result = device.wait(Until.findObject(By.text("跑分结果")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(10000);
            result = device.wait(Until.findObject(By.text("跑分结果")), 1000);
            netweak = device.wait(Until.findObject(By.text("Geekbench1 在与 Geekbench1 浏览器通信时遇到了错误。Geekbench1 需要有效的互联网连接才可运行跑分。")), 1000);
            if (netweak != null) {
                break;
            }
        }
        assertTrue("网络请求超时", netweak == null);
        assertTrue("请求超时", i > 0);
        if (result != null) {
            myUntil.tookscreen(classname, "Total");
//            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.primatelabs.geekbench:id/benchmarkWebView"));
            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.primatelabs.geekbench:id/resultsViewPager"));
            getScreen(scrollable, "单核结果");
            getScreen(scrollable, "多核结果");
        }


        device.pressBack();
        Thread.sleep(3000);
        UiObject2 renderScript = device.wait(Until.findObject(By.text("运算跑分")), 2000);
        renderScript.clickAndWait(Until.newWindow(), 2000);
        Thread.sleep(2000);
        UiObject2 renderRun = device.wait(Until.findObject(By.text("运行运算跑分")), 2000);
        renderRun.clickAndWait(Until.newWindow(), 2000);
        Thread.sleep(2000);

        UiObject2 renderResult = device.wait(Until.findObject(By.text("跑分结果")), 1000);
        while (renderResult == null && i-- > 0) {
            Thread.sleep(10000);
            renderResult = device.wait(Until.findObject(By.text("跑分结果")), 1000);
            netweak = device.wait(Until.findObject(By.text("Geekbench1 在与 Geekbench1 浏览器通信时遇到了错误。Geekbench1 需要有效的互联网连接才可运行跑分。")), 1000);
            if (netweak != null) {
                break;
            }
        }
        assertTrue("网络请求超时", netweak == null);
        assertTrue("请求超时", i > 0);
        myUntil.tookscreen(classname, "renderScript");


    }


    private void getScreen(UiScrollable scrollable, String key) throws UiObjectNotFoundException {
        scrollable.scrollDescriptionIntoView(key);
        myUntil.tookscreen(classname, key);

    }


    private void getTotaldata(UiScrollable scrollable, String key, String aa) throws UiObjectNotFoundException {

        UiObject2 name;
        String value;

        if (key.equals("Single-Core Score")) {
            try {
                name = device.wait(Until.findObject(By.text(key)), 2000);
                value = name.getParent().getChildren().get(1).getText();
            } catch (Exception e) {
                name = device.wait(Until.findObject(By.desc(key)), 2000);
                value = name.getParent().getChildren().get(1).getContentDescription();
            }

        } else if (key.equals("Multi-Core Score")) {
            try {
                name = device.wait(Until.findObject(By.text(key)), 2000);
                value = name.getParent().getChildren().get(3).getText();
            } catch (Exception e) {
                name = device.wait(Until.findObject(By.desc(key)), 2000);
                value = name.getParent().getChildren().get(3).getContentDescription();
            }
        } else {
            scrollable.scrollTextIntoView(key);
            UiObject single = device.findObject(new UiSelector().className("android.view.View").text(key));
            if (single == null) {
                device.swipe(x / 2, y / 5 * 3, x / 2, y / 5 * 2, 100);
            }
            name = device.wait(Until.findObject(By.text(aa)), 3000);
            value = name.getParent().getChildren().get(1).getText();
        }
        myitem = new item(name.getText(), value);
        list.add(myitem);
        System.out.println(key + name.getText() + ":" + value);
        myUntil.tookscreen(classname, key + aa.split(" ")[0]);


//        UiObject single = device.findObject(new UiSelector().className("android.view.View").text(key));
//        scrollable.scrollIntoView(single);
////        device.swipe(x / 2, y / 5 * 3, x / 2, y / 5, 100);
//        name = device.wait(Until.findObject(By.text(aa)), 3000);
//        value = name.getParent().getChildren().get(1).getText();
//        myitem = new item(name.getText(), value);
//        list.add(myitem);
//        System.out.println(key+name.getText()+":"+value);
//        myUntil.tookscreen(classname, key + aa.split(" ")[0]);


    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }

}
