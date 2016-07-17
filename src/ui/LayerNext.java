package ui;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerNext extends Layer{


    public LayerNext(int x, int y, int w, int h) {
        super(x, y, w, h);
    }


    public void paint(Graphics g) {
        this.createWindow(g);
        if(this.gameDto.isStart()){
            this.drawImageOfCenter(Img.NEXT_ACT[this.gameDto.getNext()],g);
        }
    }



}