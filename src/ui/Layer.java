package ui;


import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

import java.awt.*;

abstract class Layer {
    /*
     *窗口左上角x
     */
    protected final int x;
    /*
     * 窗口左上角y坐标
     */
    protected final int y;
    /*
     * 窗口宽度
     */
    protected final int w;
    /*
     * 窗口高度
     */
    protected final int h;
    /**
     * 窗口位移
     */
    protected static final int PADDING;
    protected static final int BORDER;


    /**
     * 游戏数据
     */
    protected GameDto gameDto = null;

    static {
        //获得窗口配置
        FrameConfig fCfg = GameConfig.getFrameConfig();
        //获得窗口内边距
        PADDING = fCfg.getPadding();
        //获得窗口厚度
        BORDER = fCfg.getBorder();
    }

    private static int WINDOW_W = Img.WINDOW.getWidth(null);
    private static int WINDOW_H = Img.WINDOW.getHeight(null);
    protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
    private final int IMG_RECT_W = Img.RECT.getWidth(null);

    /**
     * 矩形植槽宽度
     */

    private final int rectW;

    private static final Font DEF_FONT = new Font("黑体",Font.BOLD,20);



    /**
     * 数字图片宽度
     */
    protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
    /**
     * 数字图片高度
     */
    private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);

    public Layer(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rectW = this.w - (PADDING << 1);
    }


    /*
     * 绘制窗口用
     */
    protected void createWindow(Graphics g) {

        g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);

        g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W - BORDER, BORDER, null);

        g.drawImage(Img.WINDOW, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0, WINDOW_W, BORDER, null);

        g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);

        g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER, WINDOW_H - BORDER, null);

        g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W, WINDOW_H - BORDER, null);

        g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);

        g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER, WINDOW_H, null);

        g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W, WINDOW_H, null);
    }

    abstract public void paint(Graphics g);


    /**
     * 打印数字
     *
     * @param x 数字位置x坐标
     * @param y 数字位置y坐标
     * @param num 数字
     * @param maxBit 数字最大位数
     * @param g 画笔
     */
    protected void drawNumberLeftPad(int x,int y,int num,int maxBit,Graphics g){

        String numString = Integer.toString(num);
        for (int i = 0; i < maxBit; i++) {
            if(maxBit - i <= numString.length()){
                int idx = i - maxBit + numString.length();
                int tempNum = numString.charAt(idx) - '0';
                g.drawImage(Img.NUMBER,this.x+x+i*IMG_NUMBER_W,this.y+y,this.x+x+(i+1)*IMG_NUMBER_W,this.y+y+IMG_NUMBER_H,
                        IMG_NUMBER_W*tempNum,0,IMG_NUMBER_W*(tempNum+1),IMG_NUMBER_H
                        ,null);
            }

        }

    }


    public void setGameDto(GameDto gameDto) {
        this.gameDto = gameDto;
    }

    protected void drawRect(int expY,String title,String number,double p,Graphics g) {
        int rect_x = this.x + PADDING;
        int rect_y = this.y + expY;
        //设置画笔
        g.setColor(Color.black);
        g.fillRect(this.x + PADDING, this.y + expY, rectW, IMG_RECT_H + 4);
        g.setColor(Color.white);
        g.fillRect(this.x + PADDING + 1, this.y + expY + 1, rectW - 2, IMG_RECT_H + 2);
        g.setColor(Color.black);
        g.fillRect(this.x + PADDING + 2, this.y + expY + 2, rectW - 4, IMG_RECT_H);
        g.setColor(Color.blue);
        int w = (int) (p * (rectW - 4));
        int subIdx = (int) (p * IMG_RECT_W) - 1;
        g.drawImage(Img.RECT,
                rect_x + 2, rect_y + 2,
                rect_x + 2 + w, rect_y + 2 + IMG_RECT_H,
                subIdx, 0, subIdx + 1, IMG_RECT_H,
                null);
        g.setColor(Color.white);
        g.setFont(DEF_FONT);
        g.drawString(title, rect_x + 4, rect_y + 22);

        if (number != null) {
                   g.drawString(number, rect_x + 240, rect_y + 22);

        }
    }

    protected void drawImageOfCenter(Image img,Graphics g){
        int imgW = img.getWidth(null);
        int imgH = img.getHeight(null);
        g.drawImage(img,this.x+(this.w - imgW>>1),this.y+(this.h - imgH>>1),null);
    }


}