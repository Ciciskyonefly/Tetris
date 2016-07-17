package ui.window;

import config.FrameConfig;
import config.GameConfig;
import ui.PanelGame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Order on 2016/6/16.
 */
public class FrameGame extends JFrame{

    public FrameGame(PanelGame panelGame){

        //获得游戏配置
        FrameConfig fCfg = GameConfig.getFrameConfig();
        this.setTitle(fCfg.getTitle());
        this.setSize(fCfg.getWidth(), fCfg.getHeight());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        int x = screen.width - this.getWidth()>>1;
        int y = (screen.height - this.getHeight()>>1) - fCfg.getWindowUP();
        this.setLocation(x,y);

        this.setContentPane(panelGame);
        this.setVisible(true);

    }
}
