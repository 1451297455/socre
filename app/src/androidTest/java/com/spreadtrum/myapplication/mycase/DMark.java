package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.renderscript.Sampler;
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
public class DMark {
    private final String packagename = "com.futuremark.dmandroid.application";
    private final String activity = "com.futuremark.dmandroid.application.activity.SplashPageActivity";
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
        UiObject2 spinner = device.wait(Until.findObject(By.res("android:id/action_bar_spinner")), 5000);
        while (spinner == null && i-- > 0) {
            Thread.sleep(2000);
            spinner = device.wait(Until.findObject(By.res("android:id/action_bar_spinner")), 5000);
        }
        spinner.clickAndWait(Until.newWindow(), 5000);
        UiObject2 BenchMarks = device.wait(Until.findObject(By.text(" BENCHMARKS ")), 5000);
        BenchMarks.clickAndWait(Until.newWindow(), 5000);
        Thread.sleep(5000);
        UiObject2 list1 = device.wait(Until.findObject(By.res("ICE_STORM_U")), 5000);
        list1.clickAndWait(Until.newWindow(), 5000);
        UiObject2 insatll = device.wait(Until.findObject(By.descContains("安装(")), 5000);
        if (insatll != null) {
            insatll.clickAndWait(Until.newWindow(), 5000);
            UiObject2 yes = device.wait(Until.findObject(By.desc("安装")), 5000);
            yes.clickAndWait(Until.newWindow(), 5000);
            UiObject2 progress = device.wait(Until.findObject(By.descContains("正在安装")), 5000);
            i=200;
            while (progress != null && i-- > 0) {
                Thread.sleep(5000);
                progress = device.wait(Until.findObject(By.descContains("正在安装")), 5000);
            }
            assertTrue("网络请求超时", i > 0);
        }
        UiObject2 run = device.wait(Until.findObject(By.desc("运行")), 1000);
        run.clickAndWait(Until.newWindow(), 5000);
        UiObject2 result = device.wait(Until.findObject(By.desc("共享")), 5000);
        i=200;
        while (result == null & i-- > 0) {
            Thread.sleep(5000);
            result = device.wait(Until.findObject(By.desc("共享")), 5000);
        }
        Thread.sleep(2000);
        myUntil.tookscreen(classname, "Unlimited Total");
        //debug
//        UiObject2 BenchMarks = device.wait(Until.findObject(By.text(" MY DEVICE ")), 5000);
//        BenchMarks.click();
//        UiObject2 listsa = device.wait(Until.findObject(By.res("historyTable")), 5000).getChildren().get(1);
//        listsa.click();
//        Thread.sleep(5000);

        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("content"));
        scrollable.scrollToEnd(2);
        myUntil.tookscreen(classname,"Unlimited detail");
        scrollable.scrollDescriptionIntoView("Ice Storm Unlimited");
        getdata("Ice Storm Unlimited", "Ice Storm Unlimited");
        getdata("显卡分数", "Ice Storm Unlimited");
        getdata("物理分数", "Ice Storm Unlimited");
        getdata("显卡测试 1", "Ice Storm Unlimited");
        getdata("显卡测试 2", "Ice Storm Unlimited");
        getdata("物理测试", "Ice Storm Unlimited");
        Thread.sleep(1000);

// test1 Ice storm Extreme
        device.pressBack();
        Thread.sleep(1000);
        list1 = device.wait(Until.findObject(By.res("content")), 5000).getChildren().get(7);
        list1.clickAndWait(Until.newWindow(), 5000);
        insatll = device.wait(Until.findObject(By.descContains("安装(")), 5000);
        if (insatll != null) {
            insatll.clickAndWait(Until.newWindow(), 5000);
            UiObject2 yes = device.wait(Until.findObject(By.desc("安装")), 5000);
            yes.clickAndWait(Until.newWindow(), 5000);
            UiObject2 progress = device.wait(Until.findObject(By.descContains("正在安装")), 5000);
            i=200;
            while (progress != null && i-- > 0) {
                Thread.sleep(5000);
                progress = device.wait(Until.findObject(By.descContains("正在安装")), 5000);
            }
            assertTrue("网络请求超时", i > 0);
        }
        run = device.wait(Until.findObject(By.desc("运行")), 1000);
        run.clickAndWait(Until.newWindow(), 5000);
        result = device.wait(Until.findObject(By.desc("共享")), 5000);
        i=200;
        while (result == null & i-- > 0) {
            Thread.sleep(5000);
            result = device.wait(Until.findObject(By.desc("共享")), 5000);
        }
        Thread.sleep(2000);
        myUntil.tookscreen(classname, "Extreme Total");
        //debug
//        BenchMarks = device.wait(Until.findObject(By.text(" MY DEVICE ")), 5000);
//        BenchMarks.click();
//         listsa = device.wait(Until.findObject(By.desc("8518 ICE STORM EXTREME")), 5000);
//        listsa.click();
//        Thread.sleep(5000);

        scrollable = new UiScrollable(new UiSelector().resourceId("content"));
        scrollable.scrollToEnd(2);
        myUntil.tookscreen(classname,"Extreme detail");
        scrollable.scrollDescriptionIntoView("Ice Storm Extreme");
        getdata("Ice Storm Extreme", "Ice Storm Extreme");
        getdata("显卡分数", "Ice Storm Extreme");
        getdata("物理分数", "Ice Storm Extreme");
        getdata("显卡测试 1", "Ice Storm Extreme");
        getdata("显卡测试 2", "Ice Storm Extreme");
        getdata("物理测试", "Ice Storm Extreme");


    }

    private void getdata(String key, String tag) {
        String name;
        String value = null;
        UiObject2 ce = device.wait(Until.findObject(By.desc(key)), 5000);
        name = ce.getContentDescription();
        if (key.equals("Ice Storm Unlimited") || key.equals("Ice Storm Extreme")) {
            value = ce.getParent().getChildren().get(1).getContentDescription();
        } else if (key.equals("显卡分数")) {
            value = ce.getParent().getChildren().get(3).getContentDescription();
        } else if (key.equals("物理分数")) {
            value = ce.getParent().getChildren().get(5).getContentDescription();
        } else if (key.equals("显卡测试 1")) {
            UiObject2 fa = device.wait(Until.findObject(By.desc(tag)), 5000);
            value = fa.getParent().getChildren().get(7).getContentDescription().split(" ")[0];
        } else if (key.equals("显卡测试 2")) {
            UiObject2 fa = device.wait(Until.findObject(By.desc(tag)), 5000);
            value = fa.getParent().getChildren().get(9).getContentDescription().split(" ")[0];
        } else if (key.equals("物理测试")) {
            UiObject2 fa = device.wait(Until.findObject(By.desc(tag)), 5000);
            value = fa.getParent().getChildren().get(11).getContentDescription().split(" ")[0];
        }
        myitem = new item(name, value);
        list.add(myitem);
    }


    @After
    public void end() throws ParserConfigurationException, TransformerException, IOException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }


}
