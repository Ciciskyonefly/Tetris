package Service;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

import java.awt.*;
import java.util.Random;

/**
 * Created by Order on 2016/6/18.
 */
public class GameTetris implements GameService {

    /**
     * 随机数生成器
     */
    private Random random = new Random();
    /**
     * 方块种类数
     */
    private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
    //TODO 配置文件 等级分数
    private static final int LEVEL_POINT = 200;
    //TODO 配置文件 每行分数
    private static final int LINE_POINT = 10;
    GameDto dto = new GameDto();

    public GameTetris(GameDto dto) {
        this.dto = dto;

    }


    public boolean keyUp() {
        if(this.dto.isPause()){
            return true;
        }
        synchronized (this.dto) {
            this.dto.getGameAct().rotation(this.dto.getGameMap());
        }
        return true;
    }

    /**
     * core method
     */
    public boolean keyDown() {
        if(this.dto.isPause()){
            return true;
        }

        synchronized (this.dto) {

            int rmLine = this.dto.getNowRemoveLine();

            if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
                return false;
            }
            boolean[][] gameMap = this.dto.getGameMap();
            Point[] act = this.dto.getGameAct().getActPoints();
            for (int i = 0; i < act.length; ++i) {
                gameMap[act[i].x][act[i].y] = true;
            }

            //消行
            this.ckeckLine(rmLine);
            //计算分数
            int nowPoint = this.countNowPoint(rmLine);
            this.dto.setNowPoint(nowPoint);

            //计算等级,升级
            int nowLevel = this.dto.getNowPoint() / LEVEL_POINT;
            this.dto.setNowLevel(nowLevel);

            //生成下一个方块
            this.dto.getGameAct().init(this.dto.getNext());
            //生成在下一个方块
            this.dto.setNext(random.nextInt(MAX_TYPE));

            if (this.isLose()) {
                //结束游戏
                this.dto.setStart(false);
            }
        }
        return true;
    }


    /**
     * 判断游戏是否失败
     */
    private boolean isLose() {

        //获得现在的活动方块
        Point[] actPoints = this.dto.getGameAct().getActPoints();
        //获得现在的游戏地图
        boolean[][] map = this.dto.getGameMap();
        for (int i = 0; i < actPoints.length; i++) {
            if (map[actPoints[i].x][actPoints[i].y])
                return true;
        }
        return false;
    }



    public boolean keyLeft() {
        if(this.dto.isPause()){
            return true;
        }
        synchronized (this.dto) {
            this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
        }
        return true;
    }

    public boolean keyRight() {
        if(this.dto.isPause()){
            return true;
        }
        synchronized (this.dto) {
            this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
        }
        return true;
    }

    /**
     * 作弊键
     */
    @Override
    public boolean keyFunUp() {

        this.dto.setCheat(true);
        synchronized (this.dto) {

            int point = this.dto.getNowPoint();
            int level = this.dto.getNowLevel();
            int rmLine = this.dto.getNowRemoveLine();
            point += 10;
            rmLine += 1;
            if (rmLine % 20 == 0) {
                level += 1;
            }
            this.dto.setNowPoint(point);
            this.dto.setNowLevel(level);
            this.dto.setNowRemoveLine(rmLine);
        }
        return true;
    }

    /**
     * 直线下落
     */
    @Override
    public boolean keyFunDown() {
        if(this.dto.isPause()){
            return true;
        }
        while (!keyDown()) ;
        return true;
    }

    /**
     *显示阴影
     */
    @Override
    public boolean keyFunLeft() {
        synchronized (this.dto) {
            this.dto.changeShowShadow();
        }
        return true;
    }

    /**
     *暂停
     */
    @Override
    public boolean keyFunRight() {
        synchronized (this.dto){
            if(this.dto.isStart()){
                this.dto.changePause();
            }

        }
        return true;
    }

    /**
     * 判断这一行是否可以消去
     */
    public void ckeckLine(int rmLine) {

        boolean[][] gameMap = this.dto.getGameMap();
        int countNewRemoveLine = 0;
        for (int y = gameMap[0].length - 1; y >= 0; y--) {

            if (canRemoveLine(y, gameMap)) {
                countNewRemoveLine += 1;
                this.eliminateLine(y, gameMap);
                y = gameMap[0].length;
            }
        }

        this.dto.setNowRemoveLine(rmLine + countNewRemoveLine);
    }


    private void eliminateLine(int y, boolean[][] gameMap) {
        for (int x = 0; x < gameMap.length; ++x) {
            for (int i = y; i > 0; --i) {
                gameMap[x][i] = gameMap[x][i - 1];
            }
            gameMap[x][0] = false;
        }
    }


    private boolean canRemoveLine(int y, boolean[][] gameMap) {
        for (int x = 0; x < gameMap.length; x++) {
            if (!gameMap[x][y]) {
                return false;
            }
        }
        return true;
    }


    private int countNowPoint(int rmLine) {
        int newRmLine = (this.dto.getNowRemoveLine() - rmLine);
        int eachLinePoint = newRmLine * LINE_POINT;
        int newAddPoint = eachLinePoint * newRmLine;
        int nowPoint = this.dto.getNowPoint() + newAddPoint;
        return nowPoint;

    }


    @Override
    public void startGame() {
        //随机生成下一方块
        dto.setNext(random.nextInt(MAX_TYPE));
        //随机生成现在方块
        dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
        //把游戏状态设置为开始
        dto.setStart(true);
        //dto初始化
        dto.dtoInit();
    }

    /**
     * 游戏主行为
     */
    @Override
    public void mainAction() {
        this.keyDown();
    }
}


