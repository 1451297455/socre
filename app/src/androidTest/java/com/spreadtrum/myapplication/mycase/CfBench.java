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
public class CfBench {


    private String packagename = "eu.chainfire.cfbench";
    private String activity = "eu.chainfire.cfbench.MainActivity";
    private String appstart = " am start -n " + packagename + "/" + activity;
    private String appkill = "am force-stop " + packagename;
    private MyUntil myUntil = null;
    private UiDevice device = null;
    int i = 200;
    private String classname = getClass().getSimpleName();
    private item myitem = null;
    private ArrayList<item> list = null;


    @Before
    public void init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        myUntil = new MyUntil();
        list = new ArrayList<>();
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

        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        scrollable.scrollTextIntoView("Full Benchmark");
        UiObject2 Full_Benchmark = device.wait(Until.findObject(By.text("Full Benchmark")), 1000);
        Full_Benchmark.clickAndWait(Until.newWindow(), 1000);
        UiObject2 progress = device.wait(Until.findObject(By.res("android:id/progress")), 1000);
        while (progress != null && i-- > 0) {
            Thread.sleep(3000);
            progress = device.wait(Until.findObject(By.res("android:id/progress")), 1000);
        }
        getdata(scrollable, "Mhz");
        getdata(scrollable, "Native MIPS");
        getdata(scrollable, "Java MIPS");
        getdata(scrollable, "Native MSFLOPS");
        getdata(scrollable, "Java MSFLOPS");
        getdata(scrollable, "Native MDFLOPS");
        getdata(scrollable, "Java MDFLOPS");
        getdata(scrollable, "Native MALLOCS");
        getdata(scrollable, "Native Memory Read");
        getdata(scrollable, "Java Memory Read");
        getdata(scrollable, "Native Memory Write");
        getdata(scrollable, "Java Memory Write");
        getdata(scrollable, "Native Disk Read");
        getdata(scrollable, "Native Disk Write");
        getdata(scrollable, "Java Efficiency MIPS");
        getdata(scrollable, "Java Efficiency MSFLOPS");
        getdata(scrollable, "Java Efficiency MDFLOPS");
        getdata(scrollable, "Java Efficiency Memory Read");
        getdata(scrollable, "Java Efficiency Memory Write");
        getdata(scrollable, "Native Score");
        getdata(scrollable, "Java Score");
        getdata(scrollable, "Overall Score");
    }

    private void getdata(UiScrollable scrollable, String key) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(key);
        UiObject2 name = device.wait(Until.findObject(By.text(key)), 1000);
        String value = name.getParent().getChildren().get(1).getText();
        myitem = new item(name.getText(), value);
        list.add(myitem);
        myUntil.tookscreen(classname, key);
    }


    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
