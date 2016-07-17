package Service;

/**
 * Created by Order on 2016/7/5.
 */
public interface GameService {

    /**
     * 方向键向上
     */
    public boolean keyUp();

    /**
     * 方向键向下
     */
    public boolean keyDown();

    /**
     * 方向键向左
     */
    public boolean keyLeft();

    /**
     * 方向键向右
     */
    public boolean keyRight();

    /**
     * 三角
     */
    public boolean keyFunUp();

    /**
     * 大叉
     */
    public boolean keyFunDown();

    /**
     * 方块
     */
    public boolean keyFunLeft();

    /**
     * 圆圈
     */
    public boolean keyFunRight();


    /**
     * 启动主线程，开始游戏
     */
    void startGame();


    /**
     * 游戏主要行为
     */
    public void mainAction();
}
