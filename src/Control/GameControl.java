package Control;

import Service.GameService;
import Service.GameTetris;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import ui.PanelGame;
import ui.window.FrameGame;
import ui.window.FrameSavePoint;
import ui.window.FrameeConfig;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 接受玩家键盘事件
 * 控制画面
 * 控制游戏逻辑
 * Created by Order on 2016/6/18.
 * @author QIQI
 */
public class GameControl {


    /**
     * 数据访问接口
     */
    private Data dataA;
    /**
     * 数据访问接口
     */
    private Data dataB;

    /**
     * 游戏界面层
     */
    private PanelGame panelGame;
    /**
     * 游戏逻辑层
     */
    private GameService gameTetris;


    /**
     * 游戏控制窗口
     */
    private FrameeConfig frameeConfig;

    /**
     * 按键控制列表
     */
    private Map<Integer,Method> actionList;

    /**
     *游戏线程
     */
    private Thread gameThread = null;


    private FrameSavePoint frameSavePoint;
    /**
     * 游戏数据源
     */
    private GameDto dto = null;

    public GameControl() {

        //创建游戏数据源
        this.dto = new GameDto();

        //创建游戏逻辑快（连接游戏数据源）
        this.gameTetris = new GameTetris(dto);

        //从数据接口A获得数据库记录
        this.dataA = this.createDataObject(GameConfig.getDataConfig().getDataA());
        //设置数据库记录到游戏
        this.dto.setDbRecode(dataA.loadData());
        //从数据接口获得本地磁盘记录
        this.dataB = this.createDataObject(GameConfig.getDataConfig().getDataB());
        //设置本地磁盘记录到游戏
        this.dto.setDiskRecode(dataB.loadData());
        //创建游戏版面
        this.panelGame = new PanelGame(this,dto);
        //读取用户设置
        this.setControlConfig();
        //创建分数面板
        this.frameSavePoint = new FrameSavePoint(this);
        //初始化用户配置窗口
        this.frameeConfig = new FrameeConfig(this);
        //创建游戏窗口(安装游戏面板)
        new FrameGame(this.panelGame);
    }

    /**
     * 读取用户控制设置
     */
    private void setControlConfig(){

        //创建键盘码与方法名的映射数组
       this.actionList = new HashMap<Integer, Method>();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
            HashMap<Integer,String> cfgSet = (HashMap)ois.readObject();
            Set<Map.Entry<Integer,String>> entrySet = cfgSet.entrySet();
            for (Map.Entry<Integer,String> e:entrySet){
                actionList.put(e.getKey(),this.gameTetris.getClass().getMethod(e.getValue()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private Data createDataObject(DataInterfaceConfig cfg){
        try {
            //获得类对象
            Class<?> cls = Class.forName(cfg.getClassName());
            //获得构造器
            Constructor<?> ctr = cls.getConstructor(HashMap.class);
           return (Data)ctr.newInstance(cfg.getParam());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据玩家控制来决定行为
     */
    public void actionByKeyCode(int keyCode) {
        try {
            if(this.actionList.containsKey(keyCode)){
                //调用方法
                actionList.get(keyCode).invoke(this.gameTetris);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.panelGame.repaint();
    }

    /**
     * 显示玩家控制窗口
     */
    public void showUserConfig() {

        this.frameeConfig.setVisible(true);

    }

    /**
     * 子窗口关闭事件
     */
    public void setOver() {
        this.panelGame.repaint();
        this.setControlConfig();

    }

    /**
     * 开始按钮事件
     */
    public void start() {

        //面板按钮设置为不可点击
        this.panelGame.buttonSwitch(false);
        //关闭窗口
        this.frameeConfig.setVisible(false);
        this.frameSavePoint.setVisible(false);
        //游戏数据初始化
        this.gameTetris.startGame();
        //创建线程对象
        this.gameThread = new MainThread();
        //启动线程
        this.gameThread.start();
        //刷新画面
        this.panelGame.repaint();


    }

    /**
     * 失败之后的处理
     */
    public void afterLose(){
        //显示保存加分窗口
        if(!this.dto.isCheat()){
            frameSavePoint.showWindow(this.dto.getNowPoint());
            frameSavePoint.setVisible(true);
        }

       //按钮设置为true
        panelGame.buttonSwitch(true);

    }

    /**
     *保存分数
     */
    public void savePoint(String name) {

        Player pla = new Player(name,this.dto.getNowPoint());

        this.frameSavePoint.setVisible(false);

        //保存数据至数据库
        dataA.saveData(pla);
        //保存数据至本地记录
        dataB.saveData(pla);

        //设置数据库到本地记录
        this.dto.setDbRecode(dataA.loadData());
        //设置磁盘到本地记录
        this.dto.setDiskRecode(dataB.loadData());
        //刷新画面
        panelGame.repaint();


    }



    private class MainThread extends Thread{
        @Override
        public void run(){
            //主循环
            while (dto.isStart()){
                try {
                    //等待0.5秒
                    Thread.sleep(dto.getSleepTime());
                    if(dto.isPause()){
                        panelGame.buttonSwitch(true);
                        continue;
                    }
                    //方块下落
                    gameTetris.mainAction();
                    //画面刷新
                    panelGame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            afterLose();

        }

    }
}
