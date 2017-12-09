package com.spreadtrum.myapplication.mycase;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
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
public class Linkpack {


    private String packagename = "com.greenecomputing.linpackpro";
    private String activity = "com.greenecomputing.linpackpro.Linpack";
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
        UiObject2 single = device.wait(Until.findObject(By.text("Run Single Thread")), 1000);
        single.clickAndWait(Until.newWindow(), 1000);
        Thread.sleep(5000);
        UiObject2 SingleMFLOPS = device.wait(Until.findObject(By.res("com.greenecomputing.linpackpro:id/txtmflops_result")), 1000);
        myitem = new item("Single Thread", SingleMFLOPS.getText());
        list.add(myitem);
        myUntil.tookscreen(getClass().getSimpleName(),"Single Thread");
        UiObject2 Multi = device.wait(Until.findObject(By.text("Run Multi-Thread")), 1000);
        Multi.clickAndWait(Until.newWindow(), 1000);
        Thread.sleep(5000);
        UiObject2 MultiMFLOPS = device.wait(Until.findObject(By.res("com.greenecomputing.linpackpro:id/txtmflops_result")), 1000);
        myitem = new item("Multi Thread", MultiMFLOPS.getText());
        list.add(myitem);
        myUntil.tookscreen(getClass().getSimpleName(),"Multi Thread");

    }

    @After
    public void end() throws IOException, TransformerException, ParserConfigurationException {
        myUntil.Writexml(list, classname);
        myUntil.entraps(appkill);
    }
}
