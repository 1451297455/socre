package com.spreadtrum.myapplication.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.spreadtrum.myapplication.help.MyUntil;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jinchao_1.Zhang on 2017/10/18.
 */
@RunWith(AndroidJUnit4.class)
public class tset {
    UiDevice device;
    MyUntil until;

    @Test
    public void test() throws UiObjectNotFoundException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        int x = device.getDisplayWidth();
        int y = device.getDisplayHeight();
        until = new MyUntil();
        until.entraps("am start com.android.settings");
        if (getProp("ro.build.version.release").toString().contains("8.0")){
            UiScrollable lis = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard_container"));
            lis.scrollTextIntoView("应用和通知");
            UiObject2 app = device.wait(Until.findObject(By.text("应用和通知")), 1000);
            app.clickAndWait(Until.newWindow(), 1000);
            UiObject2 appinfo = device.wait(Until.findObject(By.text("应用信息")), 1000);
            appinfo.clickAndWait(Until.newWindow(), 1000);
            UiObject2 list = device.wait(Until.findObject(By.res("android:id/list")), 1000);
            for (int n = 0; n < 6; n++) {
                oppressions(device, list);
                device.swipe(x / 2, y / 5 * 4, x / 2, y / 5, 30);
            }
        }else {
            UiScrollable lis = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard_container"));
            lis.scrollTextIntoView("应用");
            UiObject2 app = device.wait(Until.findObject(By.text("应用")), 1000);
            app.clickAndWait(Until.newWindow(), 1000);
            UiObject2 list = device.wait(Until.findObject(By.res("android:id/list")), 1000);
            for (int n = 0; n < 6; n++) {
                oppressions(device, list);
                device.swipe(x / 2, y / 5 * 4, x / 2, y / 5, 30);
            }
        }

        System.out.println("系统android版本号:" + getProp("ro.build.version.release"));
//        String a = System.getProperty("ro.build.version.sdk");
//        System.out.print("asd"+a);
    }

    @After
    public void end() {
        device.pressHome();
        until.entraps("am force-stop com.android.settings");
    }

    private void oppressions(UiDevice device, UiObject2 list) {
        for (int i = 0; i < list.getChildCount(); i++) {
            UiObject2 item = list.getChildren().get(i);
            item.clickAndWait(Until.newWindow(), 1000);
            UiObject2 permission = device.wait(Until.findObject(By.text("权限")), 1000);
            permission.clickAndWait(Until.newWindow(), 1000);
            UiObject2 lis1 = device.wait(Until.findObject(By.res("android:id/list")), 1000);
            if (lis1 != null) {
                for (int j = 0; j < lis1.getChildCount(); j++) {
                    UiObject2 item1 = lis1.getChildren().get(j);
                    if (!item1.getChildren().get(1).getChildren().get(0).getText().equals("其他权限")) {
                        UiObject2 swicth = item1.getChildren().get(2).getChildren().get(0);
                        if (swicth.getText().equals("关闭")) {
                            swicth.clickAndWait(Until.newWindow(), 1000);
                        }
                    }
                }
                device.pressBack();
                device.pressBack();
            } else {
                device.pressBack();
            }
        }
    }

    public String getProp(String key) {
        try {
            //命令窗口输入命令
            Process p = Runtime.getRuntime().exec("getprop");
            //从命令中提取的输入流
            InputStream in = p.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader buff = new BufferedReader(reader);
            //逐行读取并输出
            String line = "";
            while ((line = buff.readLine()) != null) {
                if (line.contains("[" + key + "]")) {
                    String[] s = line.split("\\[");
                    //返回值
                    return s[2].replaceAll("\\].*", "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果没取到就返回这个
        return "未找到对应KEY";
    }

}
