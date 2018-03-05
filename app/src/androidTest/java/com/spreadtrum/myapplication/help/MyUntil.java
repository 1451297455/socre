package com.spreadtrum.myapplication.help;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.junit.Assert.assertTrue;


/**
 * Created by Jinchao_1.Zhang on 2017/9/14.
 */

public class MyUntil {
    private UiDevice device;
    public static int x, y;

    public MyUntil() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        x = device.getDisplayWidth();
        y = device.getDisplayHeight();
    }

    public void openScreen() throws RemoteException, InterruptedException {
        if (!device.isScreenOn()) {
            device.wakeUp();
            Thread.sleep(1000);
            device.swipe(x / 2, y / 5 * 4, x / 2, y / 5, 10);
        }

    }

    public boolean wifiOn() throws UiObjectNotFoundException {
        try {
            device.executeShellCommand("svc wifi enable");
            device.executeShellCommand("svc data disable");
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean wifiOff() throws IOException, UiObjectNotFoundException {
        try {
            device.executeShellCommand("svc wifi disable");
            device.executeShellCommand("svc data disable");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void entraps(String command) {
        assertTrue("fail", Startup(command));
    }

    private boolean Startup(String command) {
        try {
            device.executeShellCommand(command);
            Thread.sleep(6000);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public void permession(String appname) throws IOException, UiObjectNotFoundException {
        device.executeShellCommand("am start com.android.settings");
        if (getProp("ro.build.version.release").toString().contains("8.0")) {
            UiScrollable lis = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard_container"));
            lis.scrollTextIntoView("应用和通知");
            UiObject2 app = device.wait(Until.findObject(By.text("应用和通知")), 1000);
            app.clickAndWait(Until.newWindow(), 1000);
            UiObject2 appinfo = device.wait(Until.findObject(By.text("应用信息")), 1000);
            appinfo.clickAndWait(Until.newWindow(), 1000);
            UiScrollable list = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            list.scrollTextIntoView(appname);
            UiObject2 name = device.wait(Until.findObject(By.textContains(appname)), 1000);
            name.clickAndWait(Until.newWindow(), 1000);
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
                device.pressHome();
            } else {
                device.pressBack();
                device.pressHome();
            }

        } else {
            UiScrollable lis = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard_container"));
            lis.scrollTextIntoView("应用");
            UiObject2 app = device.wait(Until.findObject(By.text("应用")), 1000);
            app.clickAndWait(Until.newWindow(), 1000);
            UiScrollable list = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            list.scrollTextIntoView(appname);
            UiObject2 name = device.wait(Until.findObject(By.textContains(appname)), 1000);
            name.clickAndWait(Until.newWindow(), 1000);
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
                device.pressHome();
            }

        }
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


    //获取系统信息
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

    public void tookscreen(String classname, String name) {
        File file = new File("/storage/emulated/0/Pictures/" + classname + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        File picture = new File("/storage/emulated/0/Pictures/" + classname + "/" + name + ".png");
        device.takeScreenshot(picture);
    }

    public void Writexml(ArrayList<item> list, String classname) throws IOException, TransformerException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File Dir = new File("/storage/emulated/0/Pictures/" + classname + "/");
        if (!Dir.exists()) {
            Dir.mkdirs();
        }
        Writer Xml = new FileWriter("/storage/emulated/0/Pictures/" + classname + "/" + classname + ".xml");
        Document document = builder.newDocument();
        Element root = document.createElement("datas");
        item myitem;
        Element data;
        if (list.size() == 0) {

        } else {
            for (int i = 0; i < list.size(); i++) {
                data = document.createElement("data");
                myitem = list.get(i);
                data.setAttribute("name", myitem.getName());
                data.setAttribute("value", myitem.getValue());
                root.appendChild(data);
            }
            document.appendChild(root);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(Xml));
        }
    }

}
