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
public class RLbenchmarkSQL {


    private String packagename = "com.redlicense.benchmark.sqlite";
    private String activity = "com.redlicense.benchmark.sqlite.Main";
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
    public void test() throws Exception {
        myUntil.openScreen();
        myUntil.wifiOff();
        myUntil.entraps(appstart);
        UiObject2 start = device.wait(Until.findObject(By.res("com.redlicense.benchmark.sqlite:id/start_stop")), 1000);
        start.clickAndWait(Until.newWindow(), 2000);
        UiObject2 end = device.wait(Until.findObject(By.text("Overall")), 1000);
        while (end == null && i-- > 0) {
            Thread.sleep(1000);
            end = device.wait(Until.findObject(By.text("Overall")), 1000);
        }
        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.redlicense.benchmark.sqlite:id/scroll"));
        getdata("1000 INSERTs", scrollable);
        getdata("25000 INSERTs in a transaction", scrollable);
        getdata("25000 INSERTs into an indexed table in a transaction", scrollable);
        getdata("100 SELECTs without an index", scrollable);
        getdata("100 SELECTs on a string comparison", scrollable);
        getdata("Creating an index", scrollable);
        getdata("5000 SELECTs with an index", scrollable);
        getdata("1000 UPDATEs without an index", scrollable);
        getdata("25000 UPDATEs with an index", scrollable);
        getdata("INSERTs from a SELECT", scrollable);
        getdata("DELETE without an index", scrollable);
        getdata("DELETE with an index", scrollable);
        getdata("DROP TABLE", scrollable);
        getdata("Overall", scrollable);

    }


    private void getdata(String name, UiScrollable scrollable) throws UiObjectNotFoundException {
        scrollable.scrollTextIntoView(name);
        myUntil.tookscreen(classname, name);
        UiObject2 key = device.wait(Until.findObject(By.text(name)), 1000);
        UiObject2 value;
        if (name.equals("Overall")) {
            value=key.getParent().getParent().getChildren().get(1);
        } else {
            value = key.getParent().getChildren().get(1);
        }
        item myItem = null;
        myItem = new item(key.getText(), value.getText().split("\n")[0]);
        System.out.println(key.getText()+value.getText().split("\n")[0]);
        list.add(myItem);
    }

    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
