package ui;

import config.GameConfig;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerPoint extends Layer {


    /**
     * 分数y坐标
     */
    private  final int pointY;

    /**
     * 消行y坐标
     */
    private  final int rmlineY;

    /**
     * 经验值槽y坐标
     */
    private final int expY;

    /**
     * 经验值槽宽度
     */
    private final int expW;

    /**
     * 分数最大位数
     */
    private static final int POINT_BIT = 5;

    /**
     * 分数消行值横坐标
     */
    private  int comX;

    /**
     * 升级行数
     */
    private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();



    public LayerPoint(int x, int y, int w, int h) {
        super(x,y,w,h);
        this.comX = this.w - PADDING - POINT_BIT * IMG_NUMBER_W;
        this.pointY = PADDING;
        this.rmlineY = this.pointY + Img.POINT.getHeight(null)+ PADDING;
        this.expY = this.rmlineY + Img.RMLINE.getHeight(null)+ PADDING;
        this.expW = this.w - (PADDING << 1);
    }


    public void paint(Graphics g){
        this.createWindow(g);
        g.drawImage(Img.POINT,this.x+PADDING,this.y+ pointY,null);
        g.drawImage(Img.RMLINE,this.x+PADDING, this.y+ rmlineY, null);
       //绘制分数
        this.drawNumberLeftPad(comX, pointY,this.gameDto.getNowPoint(),POINT_BIT,g);
        //绘制消行
        this.drawNumberLeftPad(comX, rmlineY,this.gameDto.getNowRemoveLine(),POINT_BIT,g);

        int rmLine = this.gameDto.getNowRemoveLine();
        this.drawRect(expY,"下一级",null,(double)(rmLine%20)/(double)(LEVEL_UP),g);
    }







}
