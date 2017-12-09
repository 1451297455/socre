package com.spreadtrum.myapplication.help;

/**
 * Created by Jinchao_1.Zhang on 2017/9/16.
 */

public class item {
    private String name;
    private String value;

    public item(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
