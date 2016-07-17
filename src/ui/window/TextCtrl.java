package ui.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Order on 2016/7/5.
 */
public class TextCtrl extends JTextField {


    private int keyCode;

    private final String methodName;

    public TextCtrl(int x,int y,int w,int h,String methodName){

        //设置文本框位置
        this.setBounds(x,y,w,h);
        //初始化方法名
        this.methodName = methodName;

        this.setFont(new Font("楷体",Font.BOLD,14));



        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            /**
             *键盘松开
             */
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                setKeyCode(e.getKeyCode());
            }
        });
    }





    public String getMethodName() {
        return methodName;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        //设置数据对象
        this.setText(KeyEvent.getKeyText(this.keyCode));
    }
}
