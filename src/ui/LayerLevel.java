package ui;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerLevel extends Layer{




    private static int IMG_LEVEL_W = Img.LEVEL.getWidth(null);
 /*   private static int IMG_NUMBER_W = IMG_NUMBER.getWidth(null)/10;

    private static int IMG_NUMBER_H = IMG_NUMBER.getHeight(null);*/

    public LayerLevel(int x, int y, int w, int h){
        super(x,y,w,h);
    }

    public void paint(Graphics g){
        this.createWindow(g);
        int centerX = (this.w - IMG_LEVEL_W >> 1);

        //窗口标题
        g.drawImage(Img.LEVEL,this.x+centerX, this.y + PADDING, null);
        //绘制分数
        this.drawNumberLeftPad(centerX,64,this.gameDto.getNowLevel(),2,g);

    }


}
