package com.ww.easyplugin.androidhotfix;

/**
 * Created by huangjian on 2016/8/22.
 */
public class TestHotFix {
    public String getTestString(){
        return "这是宿主";
    }

    public String getTestString2(){
        return getTestString() + TestHotFixActivity.getString();
    }
}
