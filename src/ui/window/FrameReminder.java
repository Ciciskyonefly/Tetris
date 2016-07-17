package ui.window;

import Control.GameControl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Order on 2016/7/11.
 */
public class FrameReminder extends JPanel{


    private GameControl gameControl = null;



    public FrameReminder(GameControl gameControl){

        this.gameControl = gameControl;
        JPanel reminderPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JOptionPane.showMessageDialog(reminderPanel1, "请开始游戏", "提示", JOptionPane.WARNING_MESSAGE);
    }


}
