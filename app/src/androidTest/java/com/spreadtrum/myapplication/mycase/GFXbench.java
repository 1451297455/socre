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

/**
 * Created by Jinchao_1.Zhang on 2017/9/14.
 */
@RunWith(AndroidJUnit4.class)
public class GFXbench {


    private String packagename = "com.glbenchmark.glbenchmark27.corporate";
    private String activity = "net.kishonti.gfxbench.GfxMainActivity";
    private String appstart = " am start -n " + packagename + "/" + activity;
    private String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int x, y, i = 200;
    private String classname = getClass().getSimpleName();
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
    public void test() throws RemoteException, IOException, InterruptedException, UiObjectNotFoundException {
        myUntil.openScreen();
        myUntil.wifiOff();
//        myUntil.permession("CF-Bench");
        myUntil.entraps(appstart);
        Thread.sleep(2000);

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

        UiObject2 sure = device.wait(Until.findObject(By.text("确定")), 1000);
        if (sure != null) {
            sure.clickAndWait(Until.newWindow(), 1000);
            Thread.sleep(5000);
            myUntil.entraps(appkill);
            Thread.sleep(3000);
            myUntil.entraps(appstart);
            Thread.sleep(2000);
        }
        device.click(x / 2, y / 2);
        UiObject2 result = device.wait(Until.findObject(By.text("高水平测试")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(10000);
            result = device.wait(Until.findObject(By.text("高水平测试")), 1000);
        }

//        device.click(x / 10 * 3, y - 10);
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.glbenchmark.glbenchmark27.corporate:id/results_testList"));
        getdata(scrollable, "曼哈顿", "");
        getdata(scrollable, "曼哈顿", "Mail-400MP(fps)");
        getdata(scrollable, "1080p 曼哈顿离屏", "");
        getdata(scrollable, "1080p 曼哈顿离屏", "Mail-400MP(fps)");
        getdata(scrollable, "霸王龙", "");
        getdata(scrollable, "霸王龙", "Mail-400MP(fps)");
        getdata(scrollable, "1080p 霸王龙离屏", "");
        getdata(scrollable, "1080p 霸王龙离屏", "Mail-400MP(fps)");
        getdata(scrollable, "算术逻辑单元", "");
        getdata(scrollable, "算术逻辑单元", "Mail-400MP(fps)");
        getdata(scrollable, "1080p 算术逻辑单元离屏", "");
        getdata(scrollable, "1080p 算术逻辑单元离屏", "Mail-400MP(fps)");
        getdata(scrollable, "阿尔法混合", "");
        getdata(scrollable, "1080p 阿尔法混合离屏", "");
        getdata(scrollable, "驱动程序开销", "");
        getdata(scrollable, "驱动程序开销", "Mail-400MP(fps)");
        getdata(scrollable, "1080p 驱动程序开销离屏", "");
        getdata(scrollable, "1080p 驱动程序开销离屏", "Mail-400MP(fps)");
        getdata(scrollable, "填充", "");
        getdata(scrollable, "1080p 填充离屏", "");
        getdata(scrollable, "渲染质量", "");
        getdata(scrollable, "渲染质量（高精度）", "");

//        UiObject2 start = device.wait(Until.findObject(By.text("全部开始")), 1000);
//        start.clickAndWait(Until.newWindow(), 1000);


    }

    private void getdata(UiScrollable scrollable, String key, String key2) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(key);
        UiObject2 name = device.wait(Until.findObject(By.text(key)), 1000);
        String value;
        if (key2.equals("") || key.contains("填充") || key.contains("渲染质量")||key.contains("阿尔法混合")) {
            value = name.getParent().getParent().getParent().getParent().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getText();
            myitem = new item(key, value);
        } else {
            value = name.getParent().getParent().getParent().getParent().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(1).getText();
            myitem = new item(key2, value);
        }
        list.add(myitem);
        myUntil.tookscreen(classname, key);
    }

    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
