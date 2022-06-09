package com.weiwei.easyplugin.androidhotfix;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


import com.weiwei.easyplugin.MainActivity;
import com.weiwei.easyplugin.R;
import com.weiwei.easyplugin.androidhotfix.hotfixTest.MyInterface;

import zeus.plugin.ZeusBaseActivity;

/**
 * 补丁测试页面
 *
 * @author adison
 * @date 16/8/21
 * @time 上午2:04
 */
public class TestHotFixActivity extends ZeusBaseActivity {

    private MyInterface test = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test1 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test2 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test3 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test4 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test5 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test6 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test7 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    private MyInterface test8 = new MyInterface() {
        @Override
        public String getString() {
            return MainActivity.class.getName();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testhotfix);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setTextSize(38);
        textView.setGravity(Gravity.CENTER);
        textView.setText(new TestHotFix().getTestString());
        textView.setTextColor(getResources().getColor(android.R.color.black));
        setContentView(textView);
        setTitle(new TestHotFix().getTestString2());
        Toast.makeText(this, test.getString(), Toast.LENGTH_LONG).show();
        if(test1 == test2 ||
                test3 == test4||
                test5 == test6||
                test7 == test8
                ){
            int a = 0;
            int b =a;
        }
    }

    public static String getString(){
        return "页面";
    }
}
