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
public class Geekbench {
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

       /* //test1
        UiObject2 back = device.wait(Until.findObject(By.descContains("转到上一层级")), 1000);
        back.clickAndWait(Until.newWindow(), 1000);
        UiObject2 history = device.wait(Until.findObject(By.text("历史记录")), 1000);
        history.clickAndWait(Until.newWindow(), 1000);
        UiObject2 item = device.wait(Until.findObject(By.res("com.primatelabs.geekbench:id/historyRecycler")), 1000).getChildren().get(0);
        item.clickAndWait(Until.newWindow(), 5000);
        Thread.sleep(3000);*/
        UiObject2 accept = device.wait(Until.findObject(By.text("同意")), 5000);
        if (accept != null) {
            accept.clickAndWait(Until.newWindow(), 5000);
        }
        UiObject2 run = device.wait(Until.findObject(By.res("com.primatelabs.geekbench:id/runCpuBenchmarks")), 1000);
        if (run != null) {
            run.clickAndWait(Until.newWindow(), 1000);
        }
        UiObject2 netweak=null;
        UiObject2 result = device.wait(Until.findObject(By.text("跑分结果")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(10000);
            result = device.wait(Until.findObject(By.text("跑分结果")), 1000);
            netweak = device.wait(Until.findObject(By.text("Geekbench 在与 Geekbench 浏览器通信时遇到了错误。Geekbench 需要有效的互联网连接才可运行跑分。")),1000);
            if (netweak!=null){
                break;
            }
        }
        assertTrue("网络请求超时", netweak==null);
        assertTrue("请求超时", i > 0);
        if (result != null) {
            myUntil.tookscreen(classname, "Total");
//            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.primatelabs.geekbench:id/benchmarkWebView"));
            UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.primatelabs.geekbench:id/resultsViewPager"));
            getTotaldata(scrollable, "Single-Core Score");
            getTotaldata(scrollable, "Multi-Core Score");
            getTotaldata(scrollable, "单核结果");
            getTotaldata(scrollable, "多核结果");
        }
    }

    private void getTotaldata(UiScrollable scrollable, String key) throws UiObjectNotFoundException {

        UiObject2 name;
        String value;
        if (key.equals("Multi-Core Score")) {
            name = device.wait(Until.findObject(By.desc(key)), 5000);
            value = name.getParent().getChildren().get(3).getContentDescription();
            myitem = new item(name.getContentDescription(), value);
            list.add(myitem);
        } else if (key.equals("Single-Core Score")) {
            name = device.wait(Until.findObject(By.desc(key)), 5000);
            Log.d("Sys", name.getContentDescription());
            value = name.getParent().getChildren().get(1).getContentDescription();
            myitem = new item(name.getContentDescription(), value);
            list.add(myitem);
        } else {
            if (key.equals("单核结果")) {
                scrollable.scrollDescriptionIntoView(key);
                device.swipe(x / 2, y / 5 * 3, x / 2, y / 5, 100);
            } else {
                scrollable.scrollDescriptionIntoView(key);
            }

//            UiObject2 parent = device.wait(Until.findObject(By.desc("密钥加密跑分 Score")), 5000).getParent().getParent().getParent();
//            for (int j = 0; j < 4; j++) {
//                name = parent.getChildren().get(j).getChildren().get(0).getChildren().get(0);
//                value = parent.getChildren().get(j).getChildren().get(0).getChildren().get(1).getContentDescription();
//            myitem = new item(name.getContentDescription(), value);
//            list.add(myitem);
//            }
            myUntil.tookscreen(classname, key);

        }


    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
//        myUntil.Writexml(list, classname);
//        myUntil.entraps(appkill);
    }

}
