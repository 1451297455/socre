package com.spreadtrum.myapplication.mycase;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.spreadtrum.myapplication.help.MyUntil;
import com.spreadtrum.myapplication.help.item;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import android.os.Environment;

/**
 * Created by Jinchao_1.Zhang on 2017/9/14.
 */

@RunWith(AndroidJUnit4.class)
public class Antutu {

    private final String packagename = "com.antutu.ABenchMark";
    private final String activity = "com.antutu.ABenchMark.ABenchMarkStart";
    private final String appstart = " am start -n " + packagename + "/" + activity;
    private final String appkill = "am force-stop " + packagename;
    private final String path = Environment.getExternalStorageDirectory().toString() + "/.antutu/last_result.json";
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int i = 200;
    private final String classname = getClass().getSimpleName();
    private item myitem = null;
    private ArrayList<item> list = null;
    private ArrayList<item> lowlist = null;

    @Before
    public void init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        myUntil = new MyUntil();
        list = new ArrayList<>();
        lowlist = new ArrayList<>();
    }

    @Test
    public void antutu() throws Exception {
        myUntil.openScreen();
        myUntil.wifiOff();
        myUntil.entraps(appstart);
        Thread.sleep(5000);
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
        if (device.hasWatcherTriggered("batterDialog")) {
            device.resetWatcherTriggers();
        }
        try {
            UiObject2 start = device.wait(Until.findObject(By.res(Pattern.compile("com.antutu.ABenchMark:id/start_test_region|com.antutu.ABenchMark:id/start_test_text"))), 1000);
            start.clickAndWait(Until.newWindow(),2000);
        }catch (Exception e){
            device.executeShellCommand("am start -S -W -n com.antutu.ABenchMark/com.antutu.ABenchMark.ABenchMarkStart");
            device.executeShellCommand("am start -S -W -n com.antutu.ABenchMark/com.antutu.ABenchMark.ABenchMarkStart -e 74Sd42l35nH e57b6eb9906e27062fc7fcfcc820b957a5c33b649");
        }

      UiObject2 result = null;
        while (result == null && i-- > 1) {
            Thread.sleep(1000);
            result = device.wait(Until.findObject(By.res("com.antutu.ABenchMark:id/title_text").text("测试详情")), 1000);
        }
        myUntil.tookscreen(classname, "Total");
        UiObject2 Total = device.wait(Until.findObject(By.res("com.antutu.ABenchMark:id/tv_score")), 1000);
        myitem = new item("Total", Total.getText());
        list.add(myitem);
        UiObject2 detial = device.wait(Until.findObject(By.text("详细")), 1000);
        detial.clickAndWait(Until.newWindow(), 1000);
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.antutu.ABenchMark:id/elv_detail_score"));
        getdata("3D性能", scrollable);
        getdata("3D《孤立》", scrollable);
        getdata("3D《花园》", scrollable);
        getdata("UX性能", scrollable);
        getdata("UX 数据安全", scrollable);
        getdata("UX 数据处理", scrollable);
        getdata("UX 策略游戏", scrollable);
        getdata("UX 图像处理", scrollable);
        getdata("UX I/O 性能", scrollable);
        getdata("CPU性能", scrollable);
        getdata("CPU 算数运算", scrollable);
        getdata("CPU 常用算法", scrollable);
        getdata("CPU 多核性能", scrollable);
        getdata("RAM性能", scrollable);
        getItemsocre(path, myitem);
    }

    private void getItemsocre(String path, item lowitem) {
        String result = "", line = "", key = "", value = "";
        try {
            FileInputStream Fis = new FileInputStream(path);
            BufferedReader buf = new BufferedReader(new InputStreamReader(Fis));
            while ((line = buf.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = new JSONObject(result);
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = jsonObject.getString(key);
                lowitem = new item(key, value);
                lowlist.add(lowitem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getdata(String name, UiScrollable scrollable) throws Exception {
        if (name.equals("RAM性能")) {
            scrollable.scrollToEnd(3);
        } else {
            scrollable.scrollTextIntoView(name);
        }
        UiObject2 data = device.wait(Until.findObject(By.text(name)), 1000);
        data.clickAndWait(Until.newWindow(), 1000);
        UiObject2 score = data.getParent().getChildren().get(2);
        myitem = new item(data.getText(), score.getText());
        System.out.println(myitem.getName() + ":" + myitem.getValue());
        list.add(myitem);
        myUntil.tookscreen(classname, name);
    }

    @After
    public void end() throws Exception {
        myUntil.Writexml(list, classname);
        myUntil.Writexml(lowlist, classname + "low");
        myUntil.entraps(appkill);
        device.pressHome();
    }

}
