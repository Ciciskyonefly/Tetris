package ui.window;

import Control.GameControl;
import ui.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Order on 2016/7/4.
 */
public class FrameeConfig extends JFrame{

    private JButton btnOK = new JButton("确定");

    private JButton btnCancel = new JButton("取消");

    private JButton btnUser = new JButton("应用");

    private TextCtrl[] keyTexts = new TextCtrl[8];

    /**
     * 按键标签
     */
    private JLabel[] keyLables = new JLabel[8];

    /**
     * 皮肤列表
     */
    private JList skinList  = null;

    /**
     * 序列化的listModel模型
     */
    private DefaultListModel skinData = new DefaultListModel();


    /**
     * 游戏控制器
     */
    private GameControl gameControl;


    private JLabel errorMsg = new JLabel();
    private final static String[] METHOD_NAMES = {
            "keyUp","keyLeft", "keyDown","keyRight"
            ,"keyFunUp","keyFunDown","keyFunLeft","keyFunRight"
    };

    private final static String[] labelNames = {
            "keyUp(向上键）","keyLeft（向左键）", "keyDown（向下键）","keyRight（向右键）"
            ,"cheat(作弊键)","straightDown(一键到底）","shadowSwitch(阴影开关）","pause（暂停键）"
    };

    /**
     * 游戏按键配置文件
     */
    private static final String PATH = "data/control.dat";

    public FrameeConfig(GameControl gameControl){

        //获得游戏控制器对象
        this.gameControl = gameControl;
        //设置布局管理器为"边界布局"
        this.setLayout(new BorderLayout());
        //设置面板标题
        this.setTitle("设置");
        //初始化按键输入框
        this.initKeyText();
        //添加主面板
        this.add(creatMainPanel(),BorderLayout.CENTER);
        //添加按钮面板
        this.add(this.createButtonPanel(),BorderLayout.SOUTH);
        //设置界面位置
        this.setLocation(100, 100);
        //设置界面大小
        this.setSize(800,550);
        //窗口设置为不可改变大小
        this.setResizable(false);

    }


    /**
     * 初始化按键输入框
     */
    private void initKeyText() {
        int x = 300;
        int y = 20;
        int w = 128;
        int h = 40;

        for (int i = 0; i < 8; i++) {
            keyTexts[i] = new TextCtrl(x,y,w,h,METHOD_NAMES[i]);
            keyLables[i] = this.setLable(x-200,y,w+40,h,labelNames[i]);
            y += 40;
        }
        try {
            //读对象流
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
            HashMap<Integer,String> cfgSet = (HashMap)ois.readObject();
            Set<Map.Entry<Integer,String>> entrySet = cfgSet.entrySet();
            ois.close();
            for(Map.Entry<Integer,String> e:entrySet){
                e.getKey();
                e.getValue();
                for(TextCtrl tc:keyTexts){
                    if(tc.getMethodName().equals(e.getValue())){
                        tc.setKeyCode(e.getKey());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 创建主面板(选项卡面板)
     */
    private JTabbedPane creatMainPanel(){

        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("控制设置",this.createControlPanel());
        jtp.addTab("皮肤设置",this.creatSkinPanel());
        return jtp;
    }

    /**
     * 创建皮肤面板
     */
    private JPanel creatSkinPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());

        skinData.addElement("111111");
        skinData.addElement("222222");
        skinData.addElement("333333");
        skinData.addElement("QIPA");

        this.skinList = new JList(skinData);
        jPanel.add(new JScrollPane(this.skinList),BorderLayout.WEST);
        return jPanel;
    }

    /**
     *创建控制面板
     */
    private JPanel createControlPanel() {

        JPanel jp = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(Img.IMG_PSP,0,0,800,550,100,100,900,650,null);
            }
        };
        jp.setLayout(null);
        for (int i = 0; i <keyTexts.length ; i++) {
            jp.add(keyTexts[i]);
            jp.add(keyLables[i]);
        }

        return jp;
    }


    /**
     * 创建按钮面板
     */
    private  JPanel createButtonPanel(){
        //创建按钮面板，流式布局
        JPanel  jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //给确定按钮增加事件监听
        this.btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(writeConfig()){
                   setVisible(false);
                   gameControl.setOver();
               }


            }
        });

        this.errorMsg.setForeground(Color.RED);

        jp.add(errorMsg);
        jp.add(btnOK);
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                gameControl.setOver();
            }
        });
        jp.add(this.btnCancel);
        this.btnUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeConfig();
            }
        });
        jp.add(this.btnUser);
        return jp;

    }


    /**
     * 写入游戏配置
     */
    private boolean writeConfig() {

        HashMap<Integer,String> keySet = new HashMap<Integer, String>();
        for (int i = 0; i < keyTexts.length ; i++) {
            int keyCode = keyTexts[i].getKeyCode();
            if(keyCode == 0){
                this.errorMsg.setText("错误按键");
                return false;
            }

            keySet.put(keyCode,keyTexts[i].getMethodName());
        }

        if(keySet.size() != 8){
            this.errorMsg.setText("重复按键");
            return false;
        }


        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
            oos.writeObject(keySet);
            oos.close();
            System.out.println("写入成功");
        } catch (Exception e) {
            this.errorMsg.setText(e.getMessage());
            return false;
        }

        return true;
    }


    /**
     *设置JLable属性
     */
    private JLabel setLable(int x,int y, int w,int h, String labelName){

        JLabel jLabel = new JLabel(labelName);
        jLabel.setForeground(Color.BLUE);
        jLabel.setBounds(x,y,w,h);
        jLabel.setFont(new Font("微软雅黑",Font.BOLD,14));
        return jLabel;
    }


}
