package ui;

import config.GameConfig;
import entity.GameAct;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerGame extends Layer{

    /**
     * 左偏移量
     */
    private static final int SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();

    //TODO 硬编码左边距
    private static final int LEFT_SIDE = GameConfig.getSystemConfig().getMinX();

    //TODO 硬编码右边距
    private static final int RIGHT_SIDE =  GameConfig.getSystemConfig().getMaxX();

    private static final int LOSE_INDEX = GameConfig.getFrameConfig().getLoseIdx();

    public LayerGame(int x, int y, int w, int h) {
        super(x,y,w,h);
    }

    public void paint(Graphics g){
        this.createWindow(g);
        GameAct act = this.gameDto.getGameAct();
        if(act != null){
            Point[] points =  act.getActPoints();
            //绘制阴影
            this.drawShadow(points,g);
            //绘制活动方块
            this.drawMainAct(points,g);
        }
        //绘制游戏地图
        this.drawGameMap(g);


        if(this.gameDto.isPause()){
            this.drawImageOfCenter(Img.PAUSE,g);
        }

    }

    /**
     * 绘制活动方块
     * @param g
     */
    private void drawMainAct(Point[] points,Graphics g){

        //获取方块类型
        int typeCode = this.gameDto.getGameAct().getTypeCode();
        //打印方块
        for(int i=0; i<points.length; ++i){
           this.drawActByPoint(points[i].x,points[i].y,typeCode+1,g);
        }
    }

    /**
     * 绘制地图
     * @param g
     */
    private void drawGameMap(Graphics g) {

        //获得现在等级
        int lv = this.gameDto.getNowLevel();
        //获取地图
        boolean[][] gameMap = this.gameDto.getGameMap();
        int imgIdx = lv == 0?0:(lv-1)%7+1;
        for(int x = 0;x< gameMap.length;x++){
            for(int y = 0;y<gameMap[x].length;y++){
                if(gameMap[x][y]){
                    this.drawActByPoint(x,y,imgIdx,g);
                }
            }
        }
    }


    private void drawActByPoint(int x,int y,int idx,Graphics g){

        idx = this.gameDto.isStart()?idx:LOSE_INDEX;
        g.drawImage(Img.GAME_RECT,this.x+(x<<SIZE_ROL)+BORDER,this.y+(y<<SIZE_ROL)+BORDER,
                this.x+(x+1<<SIZE_ROL)+BORDER,this.y+(y+1<<SIZE_ROL)+BORDER,
                idx<<5,0, idx+1<<5,32,null);
    }

    /**
     * 绘制阴影
     * @param points
     */
    private void  drawShadow(Point[] points,Graphics g){
        if(!this.gameDto.isShowShadow()){
            return ;
        }
        int leftX = 17;
        int rightX= 0;
        for (Point p :points){
            leftX = p.x < leftX ? p.x:leftX;
            rightX = p.x > rightX ?p.x:rightX;
        }

        g.drawImage(Img.SHADOW,
                this.x + BORDER+(leftX<<SIZE_ROL),this.y + BORDER,
                this.x + BORDER + (rightX + 1 << SIZE_ROL),this.y + this.h - BORDER ,
                0,0,1,1,null);
    }


}
