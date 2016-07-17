package ui;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerBackGround extends Layer {


    public LayerBackGround(int x,int y,int w,int h){
        super(x,y,w,h);
    }

    @Override
    public void paint(Graphics g) {
        int bgIdx = this.gameDto.getNowLevel()%Img.BG_LIST.size();
        g.drawImage(Img.BG_LIST.get(bgIdx),0,0,1200,754,null);
    }
}
