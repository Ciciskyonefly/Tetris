package ui.window;


import Control.GameControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Order on 2016/7/8.
 */
public class FrameSavePoint extends JFrame {

    private JButton btnOK = null;

    private JLabel lbPoint = null;

    private JTextField txName = null;

    private JLabel errMsg = null;


    private GameControl gameControl = null;

    public FrameSavePoint(GameControl gameControl){

        this.gameControl = gameControl;
        //设置Frame文字
        this.setTitle("保存记录");
        this.setSize(256, 128);
        //剧中
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        //设置边界布局
        this.setLayout(new BorderLayout());
        //设置可移动为false
        this.setResizable(false);
        //创建面板布局
        this.createCom();
        //监听ok
        this.createAction();

    }



    public void createCom(){

        //创建北部面板（流式布局）
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建分数标签
        this.lbPoint = new JLabel();
        //创建errMsg标签
        this.errMsg = new JLabel();
        errMsg.setForeground(Color.RED);
        //添加分数标签到北部面板
        north.add(lbPoint);
       // 添加错误信息到北部面板
        north.add(errMsg);
        //北部面板添加到主面板
        this.add(north,BorderLayout.NORTH);

        //创建中部面板（流式布局）
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建文本框
        this.txName = new JTextField(null, 10);
        //设置文字
        center.add(new JLabel("请输入名字:"));
        //文本框添加到中部面板
        center.add(this.txName);
        //中部面板添加到主面板
        this.add(center,BorderLayout.CENTER);
        //创建南部面板（流式布局，按钮剧中)
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //创建确定按钮
        this.btnOK = new JButton("确定");
        //添加按钮到南部面板
        south.add(btnOK);
        //南部面板添加到主流程
        this.add(south,BorderLayout.SOUTH);
    }

    private void createAction() {
        this.btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = txName.getText();
                if(name.length()>16||name==null||"".equals(name)) errMsg.setText("名字输入错误");
                else {

                    gameControl.savePoint(name);
                    txName.setText(null);
                }
            }
        });

    }

    public void showWindow(int nowPoint) {
        this.lbPoint.setText("您的得分" + nowPoint);
        this.setVisible(true);
    }
}
