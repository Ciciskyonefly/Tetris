package ui;

import java.awt.*;

/**
 * Created by Order on 2016/6/17.
 */
public class LayerDisk extends LayerData {
    public LayerDisk(int x, int y, int w, int h){
        super(x,y,w,h);
    }

    public void paint(Graphics g){
        this.createWindow(g);
        this.showData(Img.DISK,this.gameDto.getDiskRecode(),g);
    }

}
