package Control;

import dto.GameDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Order on 2016/6/18.
 */
public class PlayerControl extends KeyAdapter {

    private GameControl gameControl;
    private GameDto gameDto;
    public PlayerControl(GameControl gameControl,GameDto gameDto){

        this.gameControl = gameControl;
        this.gameDto = gameDto;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!gameDto.isStart()){
            JPanel reminderPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JOptionPane.showMessageDialog(reminderPanel1, "请开始游戏", "提示", JOptionPane.WARNING_MESSAGE);
        }else
            this.gameControl.actionByKeyCode(e.getKeyCode());

    }

}
