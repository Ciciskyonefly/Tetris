package config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Created by Order on 2016/6/17.
 */
public class GameConfig implements Serializable{

    /**
     * 窗口配置文件
     */
    private static FrameConfig FRAME_CONFIG = null;
    /**
     * 游戏配置文件
     */
    private static DataConfig DATA_CONFIG = null;
    /**
     * 系统配置文件
     */
    private static SystemConfig SYSTEM_CONFIG = null;


    private static boolean IS_DEBUG  = false;


    static {
        try {

            if(IS_DEBUG){
                //创建XML读取器
                SAXReader reader = new SAXReader();
                //读取XML文件
                Document doc = null;
                doc = reader.read("data/cfg.xml");
                Element game = doc.getRootElement();
                //创建界面配置对象
                FRAME_CONFIG = new  FrameConfig(game.element("frame"));
                //创建数据访问配置对象
                DATA_CONFIG = new DataConfig(game.element("data"));
                //创建系统对象
                SYSTEM_CONFIG = new SystemConfig(game.element("system"));
            }else{

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/framecfg.dat"));
                FRAME_CONFIG =(FrameConfig)ois.readObject();
                ois = new ObjectInputStream(new FileInputStream("data/systemcfg.dat"));
                SYSTEM_CONFIG = (SystemConfig)ois.readObject();
                ois = new ObjectInputStream(new FileInputStream("data/datacfg.dat"));
                DATA_CONFIG = (DataConfig)ois.readObject();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造器私有化
     */
    private GameConfig(){}

    /**
     * 获得窗口配置
     */
    public static FrameConfig getFrameConfig(){
        return FRAME_CONFIG;
    }

    /**
     * 获得系统配置
     */
    public static SystemConfig getSystemConfig(){
        return SYSTEM_CONFIG;
    }

    /**
     * 获得数据访问配置
     */
    public static DataConfig getDataConfig(){
        return DATA_CONFIG;
    }






}
