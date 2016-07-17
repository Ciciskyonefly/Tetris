package ui;

import config.GameConfig;
import dto.Player;

import java.awt.*;

/**
 * Created by Order on 2016/6/22.
 */
public abstract class LayerData extends Layer{


    /**
     * 获取最大数据行
     */
    private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();

    /**
     * 起始y坐标
     */
    private static  int START_Y = 0;

    /**
     *
     */
    private  static final int RECT_H = IMG_RECT_H + 4;
    /**
     * 间距
     */
    private static int SPA = 0;

    public LayerData(int x, int y, int w, int h) {
        super(x, y, w, h);
        SPA = (this.h -  RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null))/MAX_ROW;
        START_Y =  PADDING + Img.DB.getHeight(null) + SPA;
    }

    @Override
    public abstract void paint(Graphics g);

    protected void showData(Image imgTitle, java.util.List<Player> players,Graphics g){
        g.drawImage(imgTitle, this.x+PADDING, this.y+ PADDING, null);

        //players = this.gameDto.getDbRecode();
        int nowPoint = this.gameDto.getNowPoint();
        for(int i=0;i<MAX_ROW;i++){
            Player pla = players.get(i);
            int recodePoint = pla.getPoint();
            double percent = (double)nowPoint/(double)recodePoint;
            percent = percent>1?1:percent;
            this.drawRect(START_Y + i*(RECT_H + SPA),pla.getName(),Integer.toString(recodePoint),percent,g);
        }
    }
}
