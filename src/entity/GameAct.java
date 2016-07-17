package entity;

import config.GameConfig;

import java.awt.*;
import java.util.List;
/**
 * Created by Order on 2016/6/18.
 */
public class GameAct {



    private Point[] actPoints;

    private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
    private final static int MAX_X = GameConfig.getSystemConfig().getMaxX();
    private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
    private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY();

    private  int typeCode;
    private final  static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
    private final  static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();


    public GameAct(int typeCode){

        this.init(typeCode);
        //TODO 硬编码写到配置文件

    }

    public Point[] getActPoints() {
        return actPoints;
    }


    public void init(int actCode) {
    //TODO
        this.typeCode = actCode;
        Point[] points = TYPE_CONFIG.get(actCode);
        actPoints = new Point[points.length];
        for (int i=0; i<points.length; ++i ){
            actPoints[i] = new Point(points[i].x,points[i].y);
        }
    }

    /**
     * 方块移动
     *
     * @param moveX 横坐标偏移量
     * @param moveY 纵坐标偏移量
     */
    public boolean move(int moveX,int moveY,boolean[][] gameMap){

        for(int i=0; i<actPoints.length; ++i){
            int newX = actPoints[i].x + moveX;
            int newY = actPoints[i].y + moveY;
            if(isOverZone(newX, newY,gameMap))
                return false;
        }

        for(int i=0; i<actPoints.length; ++i){
            actPoints[i].x += moveX;
            actPoints[i].y += moveY;
        }
        return true;
    }


    public void rotation(boolean[][] gameMap) {

        if(!TYPE_ROUND.get(this.typeCode)) {
            return;
        }
        int centerX = actPoints[0].x;
        int centerY = actPoints[0].y;
        for (int i = 0; i < actPoints.length; ++i) {
            int newX = centerY + centerX - actPoints[i].y;
            int newY = centerY - centerX + actPoints[i].x;
            if(isOverZone(newX, newY,gameMap))
                return ;
        }
        for (int i = 0; i < actPoints.length; ++i) {
            int tempX = actPoints[i].x;
            actPoints[i].x = centerY + centerX - actPoints[i].y;
            actPoints[i].y = centerY - centerX + tempX;
        }
    }

    public boolean isOverZone(int x, int y, boolean[][] gameMap){
        if(x <MIN_X||x>MAX_X||y<MIN_Y||y>MAX_Y||gameMap[x][y]){
            return true;
        }
        return false;
    }

    public int getTypeCode() {
        return typeCode;
    }


}
