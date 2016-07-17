package ui;

import config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Order on 2016/6/21.
 */
public class Img {
    public Img(){}


    /**
     * 图片路径
     */
    private static final String GRAPHICS_PATH = "graphics";



    /**
     * 矩形框照片
     */
    public static Image WINDOW = null;
    /**
     * 矩形植槽图片
     */
    public static Image RECT  = null;

    /**
     * 数字图片
     */
    public static  Image NUMBER  = null;


    /**
     * 数据库图片
     */
    public static Image DB  = null;

    /**
     * 等级标题图片
     */
    public static Image LEVEL  = null;

    /**
     * Disk标题图片
     */
    public static Image DISK  = null;


    /**
     * 窗口标题（分数）
     */
    public static  Image POINT  = null;
    /**
     * 窗口图片（消行）
     */
    public static  Image RMLINE  = null;

    /**
     * 俄罗斯方块图片
     */
    public static Image GAME_RECT  = null;
    /**
     *游戏阴影图片
     */
    public static Image SHADOW  = null;

    /**
     * 开始按钮
     */
    public static ImageIcon BTN_START  = null;

    /**
     * 设置按钮
     */
    public static ImageIcon BTN_CONFIG  = null;

    /**
     * 控制设置背景图片
     */
    public  static Image IMG_PSP  = null;
    /**
     * 暂停图片
     */
    public static Image PAUSE  = null;
    /**
     * 下一个俄罗斯方块图片
     */
    public static  Image[] NEXT_ACT  = null;
    /**
     * 背景图片
     */
    public static List<Image> BG_LIST  = null;


    static {
        setSkin("");
    }

    public static void setSkin(String path){


        String skinPath = GRAPHICS_PATH + path;

         // 矩形框照片

          WINDOW= new ImageIcon(skinPath + "/window/window.png").getImage();


         // 矩形植槽图片

        RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();


         // 数字图片

        NUMBER = new ImageIcon(skinPath + "/string/num.png").getImage();



         // 数据库图片

          DB = new ImageIcon(skinPath + "/string/db.png").getImage();


         // 等级标题图片

          LEVEL = new ImageIcon(skinPath + "/string/level.png").getImage();


         // Disk标题图片

          DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();



         // 窗口标题（分数）
        POINT = new ImageIcon(skinPath + "/string/point.png").getImage();

         // 窗口图片（消行）
        RMLINE = new ImageIcon(skinPath + "/string/rmline.png").getImage();



         // 俄罗斯方块图片
          GAME_RECT = new ImageIcon(skinPath + "/game/rect.png").getImage();

         //游戏阴影图片
          SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();


         // 开始按钮
           BTN_START = new ImageIcon(skinPath + "/string/start.png");


//        System.out.println(skinPath + "/string/start.png");


         // 设置按钮
           BTN_CONFIG = new ImageIcon(skinPath + "/string/config.png");


         // 控制设置背景图片
         IMG_PSP = new ImageIcon("data/bg11.png").getImage();


         // 暂停图片
          PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();


        //下一个方块
        NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
        for (int i = 0; i <NEXT_ACT.length ; i++) {
            NEXT_ACT[i] = new ImageIcon(skinPath + "/game/"+ i +".png").getImage();
        }
        //获取背景图片数组
        //获得一个文件夹
        File dir = new File(skinPath + "/background");
        System.out.println(skinPath + "/background");
        //获取文件里所有文件
        File[] files = dir.listFiles();
        BG_LIST = new ArrayList<Image>();
        //测试打印路径
        for(File file:files){
            //若是文件夹则不打印
            if(file.isDirectory()){
                continue;
            }
            //BG_LIST存储获得图片
//            System.out.println(file.getPath());
            BG_LIST.add(new ImageIcon(file.getPath()).getImage());
        }

//        System.out.println(BG_LIST.size());
    }




}
