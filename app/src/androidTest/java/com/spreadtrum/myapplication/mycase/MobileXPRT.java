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
 * Created by Jinchao_1.Zhang on 2017/10/20.
 */
@RunWith(AndroidJUnit4.class)
public class MobileXPRT {
    private final String packagename = "com.mobilexprt2015";
    private final String activity = "com.mobilexprt2015.MobileXPRT";
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


        UiObject2 progress = device.wait(Until.findObject(By.text("正在从APK下载测试内容…")), 1000);
        if (progress != null && i-- > 0) {
            Thread.sleep(10000);
            progress = device.wait(Until.findObject(By.text("正在从APK下载测试内容…")), 1000);
        }
//test1
//        UiObject2 resu = device.wait(Until.findObject(By.text("结果")), 1000);
//        resu.clickAndWait(Until.newWindow(), 5000);
//        UiObject2 item = device.wait(Until.findObject(By.res("android:id/list")), 5000).getChildren().get(0);
//        item.clickAndWait(Until.newWindow(), 1000);

        UiObject2 start = device.wait(Until.findObject(By.res("com.mobilexprt2015:id/customize_screen_start_tests")), 5000);
        start.clickAndWait(Until.newWindow(), 1000);
        UiObject2 result = device.wait(Until.findObject(By.text("总体得分")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(20000);
            result = device.wait(Until.findObject(By.text("总体得分")), 1000);
        }
        assertTrue("等待超时", i > 0);
        myUntil.tookscreen(classname, "Total");
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.mobilexprt2015:id/elv"));
        getdata(scrollable, "总体得分");
        getdata(scrollable, "应用照片特效");
        getdata(scrollable, "创建照片拼接");
        getdata(scrollable, "创建幻灯片");
        getdata(scrollable, "加密个人内容");
        getdata(scrollable, "通过人脸检测整理照片");


    }

    private void getdata(UiScrollable scrollable, String key) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(key);
        UiObject2 total = device.wait(Until.findObject(By.text(key)), 1000);
        String value;
        if (key.equals("总体得分")) {
            value = total.getParent().getChildren().get(1).getText();
        } else {
            value = total.getParent().getParent().getChildren().get(1).getText().split(" ")[0];
        }
        myitem = new item(total.getText(), value);
        list.add(myitem);
        myUntil.tookscreen(classname, key);
    }

    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }

}
