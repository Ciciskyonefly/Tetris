package dto;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Order on 2016/6/18.
 */
public class GameDto{

    private static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX()+1;
    private static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY()+1;
    private List<Player> dbRecode;
    private List<Player> diskRecode;
    private boolean[][] gameMap;
    private GameAct gameAct;
    private int next;
    private int nowLevel;
    private int nowPoint;
    private int nowRemoveLine;


    /**
     * 是否作弊
     */
    private boolean cheat;
    /**
     * 判断游戏是否开始
     */
    private boolean start;


    /**
     * 暂停
     */
    private boolean  pause;
    /**
     * 是否显示阴影
     */
    private boolean showShadow;

    /**
     * 线程睡眠时间(下落速度)
     */
    private long sleepTime;

    public GameDto(){

        this.dtoInit();
        this.nowLevel = 0;
    }

    public void dtoInit(){
        this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
        this.nowLevel = 0;
        this.nowPoint = 0;
        this.nowRemoveLine = 0;
        this.pause = false;
        this.cheat = false;
        //初始化睡眠时间
        this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
    }

    public int getNowLevel() {
        return nowLevel;
    }

    public void setNowLevel(int nowLevel) {

        //设置函数
        this.nowLevel = nowLevel;
        //计算线程睡眠世界（下落速度)
        this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
    }

    public List<Player> getDbRecode() {
        return dbRecode;
    }

    public void setDbRecode(List<Player> dbRecode) {
        this.setFillRecode(dbRecode);
        this.dbRecode = dbRecode;
    }

    public List<Player> getDiskRecode() {
        return diskRecode;
    }

    public void setDiskRecode(List<Player> diskRecode) {
        this.setFillRecode(diskRecode);
        this.diskRecode = diskRecode;
    }
    public boolean[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(boolean[][] gameMap) {
        this.gameMap = gameMap;
    }

    public GameAct getGameAct() {
        return gameAct;
    }


    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNowPoint() {
        return nowPoint;
    }

    public void setNowPoint(int nowPoint) {
        this.nowPoint = nowPoint;
    }

    public int getNowRemoveLine() {
        return nowRemoveLine;
    }

    public void setNowRemoveLine(int nowRemoveLine) {
        this.nowRemoveLine = nowRemoveLine;
    }


    public void setGameAct(GameAct gameAct) {
        this.gameAct = gameAct;
    }

    /**
     * 如果数据库中不满足五条记录,也显示五条
     */

    private List<Player> setFillRecode(List<Player> diskRecode){
        if(diskRecode == null){
            diskRecode = new ArrayList<Player>();
        }
        for (int i = diskRecode.size();i<5;i++){
            diskRecode.add(new Player("NO Data",0));
        }

        Collections.sort(diskRecode);
        return diskRecode;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isShowShadow() {
        return showShadow;
    }

    public void changeShowShadow() {
        this.showShadow = !this.showShadow;
    }

    public boolean isPause() {
        return pause;
    }

    public void changePause() {
        this.pause = !this.pause;
    }

    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public long getSleepTime() {
        return sleepTime;
    }
}
