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
 * Created by Jinchao_1.Zhang on 2017/9/14.
 */
@RunWith(AndroidJUnit4.class)
public class ludashi {


    private String packagename = "com.ludashi.benchmark";
    private String activity = "com.ludashi.benchmark.SplashActivity";
    private String appstart = " am start -n " + packagename + "/" + activity;
    private String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int i = 600, x = 0, y = 0;
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

        UiObject2 close = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/iv_close")), 1000);
        if (close != null) {
            close.clickAndWait(Until.newWindow(), 2000);
        }
        myUntil.entraps(appkill);
        myUntil.entraps(appstart);

        UiObject2 yes = device.wait(Until.findObject(By.text("下次再说")), 1000);
        if (yes != null) {
            yes.clickAndWait(Until.newWindow(), 2000);
            SystemClock.sleep(2000);
        }
        device.click(device.getDisplayWidth() / 8, device.getDisplayHeight() / 200 * 199);
        SystemClock.sleep(2000);
//        UiObject2 performance = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/ll_performance_test_item")), 2000);
//        performance.clickAndWait(Until.newWindow(), 2300);
        if (y == 1920) {
            device.click(device.getDisplayWidth() / 3, device.getDisplayHeight() / 100 * 51);
        } else if (y == 845) {
            device.click(device.getDisplayWidth() / 3, device.getDisplayHeight() / 5 * 3);
        } else if (y == 1280) {
            device.click(device.getDisplayWidth() / 4, device.getDisplayHeight() / 8 * 5);
        }

        Thread.sleep(1000);
        try {
            UiObject2 starttest = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/btn_rebench").text("重新评测")), 1000);
            starttest.clickAndWait(Until.newWindow(), 1000);
            SystemClock.sleep(2000);
        } catch (Exception e) {
            System.out.println("first test1");
        }
        UiObject2 result = device.wait(Until.findObject(By.text("【鲁大师】性能评测结果")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(1000);
            result = device.wait(Until.findObject(By.text("【鲁大师】性能评测结果")), 1000);
        }
        myUntil.tookscreen(getClass().getSimpleName(), "性能评测");
        UiObject2 total = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/score")), 1000);
        myitem = new item("PerforTotal", total.getText());
        list.add(myitem);
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.ludashi.benchmark:id/score_list"));
        getdata("CPU", scrollable);
        getdata("整数多线程运算能力", scrollable);
        getdata("浮点多线程运算能力", scrollable);
        getdata("整数单线程运算能力", scrollable);
        getdata("浮点单线程运算能力", scrollable);
        getdata("GPU", scrollable);
        getdata("2D绘图能力", scrollable);
        getdata("实测游戏场景", scrollable);
        getdata("室内光线移景", scrollable);
        getdata("离屏渲染储备", scrollable);
        getdata("RAM", scrollable);
        getdata("内存性能", scrollable);
        getdata("内存大小", scrollable);
        getdata("存储性能", scrollable);
        getdata("ROM", scrollable);
        getdata("存储卡", scrollable);
        getdata("数据库", scrollable);
        device.pressBack();
        Thread.sleep(1000);
        //体验评测
//        UiObject2 expierence = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/ll_expierence_test_item")), 2000);
//        expierence.clickAndWait(Until.newWindow(), 2000);
        if (y == 1920) {
            device.click(device.getDisplayWidth() / 3 * 2, device.getDisplayHeight() / 100 * 51);
        } else if (y == 845) {
            device.click(device.getDisplayWidth() / 3 * 2, device.getDisplayHeight() / 5 * 3);
        } else if (y == 1280) {
            device.click(device.getDisplayWidth() / 4 * 3, device.getDisplayHeight() / 8 * 5);
        }
        Thread.sleep(1000);

        UiObject2 retest = device.wait(Until.findObject(By.text("重新评测")), 1000);
        if (retest != null) {
            retest.clickAndWait(Until.newWindow(), 1000);
        }
        result = device.wait(Until.findObject(By.text("【鲁大师】体验评测结果")), 1000);
        while (result == null && i-- > 0) {
            Thread.sleep(1000);
            result = device.wait(Until.findObject(By.text("【鲁大师】体验评测结果")), 1000);
        }
        myUntil.tookscreen(getClass().getSimpleName(), "体验评测");
        UiObject2 Total = device.wait(Until.findObject(By.res("com.ludashi.benchmark:id/tv_total_score")), 1000);
        myitem = new item("ExpTotal", Total.getText());
        list.add(myitem);
        UiScrollable scrollable1 = new UiScrollable(new UiSelector().resourceId("com.ludashi.benchmark:id/elv_result_detail"));
        getdata(scrollable1, "桌面使用");
        getdata(scrollable1, "桌面滑动");
        getdata(scrollable1, "APP使用");
        getdata(scrollable1, "点击");
        getdata(scrollable1, "左右滑动");
        getdata(scrollable1, "上下滑动");
        getdata(scrollable1, "网页内容加载与滑动");
        getdata(scrollable1, "解析耗时");
        getdata(scrollable1, "加载耗时");
        getdata(scrollable1, "网页滑动");
        getdata(scrollable1, "照片查看与操作");
        getdata(scrollable1, "图片解码效率");
        getdata(scrollable1, "相册缩略图列表加载");
        getdata(scrollable1, "单张照片查看与操作");
        getdata(scrollable1, "视觉体验");
        getdata(scrollable1, "文件拷贝与闪存测试");
        getdata(scrollable1, "拷贝速度");
        getdata(scrollable1, "闪存读取速度");
        getdata(scrollable1, "闪存写入速度");
        getdata(scrollable1, "开机自启动");
        getdata(scrollable1, "开机自启应用耗费总内存得分");
        getdata(scrollable1, "开机自启应用数得分");
        getdata(scrollable1, "总内存得分");
        getdata(scrollable1, "剩余内存得分");

    }

    private void getdata(UiScrollable scrollable, String key) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(key);
        UiObject2 value = null;
        UiObject2 name = device.wait(Until.findObject(By.text(key)), 10000);
        if (key.equals("桌面使用") || key.equals("APP使用") || key.equals("网页内容加载与滑动") || key.equals("照片查看与操作") || key.equals("文件拷贝与闪存测试") || key.equals("开机自启动")) {
            value = name.getParent().getChildren().get(1);
        } else {
            value = name.getParent().getChildren().get(2);
        }
        myitem = new item(name.getText(), value.getText());
        System.out.println(name.getText() + ":" + value.getText());
        list.add(myitem);
        myUntil.tookscreen(getClass().getSimpleName(), key);
    }

    private void getdata(String key, UiScrollable scrollable) throws UiObjectNotFoundException {
        if (key.equals("数据库")) {
            scrollable.scrollToEnd(5);
        } else {
            scrollable.scrollTextIntoView(key);
        }
        UiObject2 name = device.wait(Until.findObject(By.text(key)), 1000);
        UiObject2 value = null;
        if (key.equals("CPU") || key.equals("GPU")) {
            value = name.getParent().getParent().getChildren().get(2).getChildren().get(0);
        } else if (key.equals("RAM") || key.equals("存储性能")) {
            try {
                value = name.getParent().getParent().getChildren().get(3).getChildren().get(0);
            } catch (IndexOutOfBoundsException e) {
                value = name.getParent().getParent().getChildren().get(2).getChildren().get(0);
            }
        } else {
            value = name.getParent().getChildren().get(1);
        }
        System.out.println(name.getText() + ":" + value.getText());
        myitem = new item(name.getText(), value.getText());
        list.add(myitem);
        myUntil.tookscreen(getClass().getSimpleName(), key);

    }

    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
