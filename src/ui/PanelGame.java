package ui;

import Control.GameControl;
import Control.PlayerControl;
import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import dto.GameDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Created by Order on 2016/6/16.
 */
public class PanelGame extends JPanel {


    private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
    private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
    java.util.List<LayerConfig> layersConfig;
    java.util.List<Layer> layers;

    private JButton btnStart;

    private JButton btnConfig;
    /**
     * 连接游戏控制器
     */
    private GameControl gameControl;



    public PanelGame(GameControl gameControl,GameDto gameDto){
        //连接游戏控制器
        this.gameControl = gameControl;
        //自由设置布局
        this.setLayout(null);
        //初始化组件
        this.initComponent();
        //初始化层
        this.initLayer(gameDto);
        //安装键盘监听器
        this.addKeyListener(new PlayerControl(gameControl,gameDto));

    }


    /**
     * 初始化组件（监听）
     */
    public  void initComponent(){
        //初始化开始按钮
        //TODO error
        System.out.println(Img.BTN_START);
        this.btnStart = new JButton(Img.BTN_START);

        //设置开始按钮位置
        btnStart.setBounds(
                GameConfig.getFrameConfig().getButtonConfig().getStartX(),
                GameConfig.getFrameConfig().getButtonConfig().getStartY(),
                BTN_SIZE_W,BTN_SIZE_H);
        //给开始按钮增加事件监听
        this.btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.start();
            }
        });
        //添加按钮到面板
        this.add(btnStart);
        //初始化设置按钮
        this.btnConfig = new JButton(Img.BTN_CONFIG);

        //设置开始按钮位置
        btnConfig.setBounds(
                GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(),
                GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(),
                BTN_SIZE_W,BTN_SIZE_H);

        //设置按钮事件监听
        btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.showUserConfig();
            }
        });
        //添加按钮到面板
        this.add(btnConfig);
    }



    /**
     * 初始化层(界面）
     */
    private void initLayer(GameDto gameDto){
        try {
            FrameConfig fCfg = GameConfig.getFrameConfig();
            layersConfig = fCfg.getLayerConfig();
            layers = new ArrayList<Layer>(layersConfig.size());
            for(LayerConfig layerConfig:layersConfig) {
                //反射，根据字符串获取类
                Class<?> cls = Class.forName(layerConfig.getClassName());
                //获取构造函数
                Constructor<?> ctr = cls.getConstructor(int.class,int.class,int.class,int.class);
                //实例化构造函数
                Layer layer = (Layer)ctr.newInstance(layerConfig.getX(),layerConfig.getY(),layerConfig.getW(),layerConfig.getH());
                layers.add(layer);
                //设置游戏数据对象
                layer.setGameDto(gameDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void paintComponent(Graphics g){

        //调用基类方法
        super.paintComponent(g);
        //绘制窗体
       for(int i=0;i<layers.size();layers.get(i++).paint(g));
        //绘制焦点
        this.requestFocus();

    }

    /**
     * 按钮是否可点击
     * @param onOff
     */
    public void buttonSwitch(boolean onOff){
        this.btnConfig.setEnabled(onOff);
        this.btnStart.setEnabled(onOff);
    }

}
