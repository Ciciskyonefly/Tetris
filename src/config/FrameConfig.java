package config;

import org.dom4j.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Order on 2016/6/30.
 */
public class FrameConfig implements Serializable{

    /**
     * 窗口标题
     */
    private  final String title;
    /**
     * 窗口拔高
     */
    private  final int windowUP;
    /**
     * 窗口宽度
     */
    private  final int width;
    /**
     * 窗口高度
     */
    private  final int height;
    /**
     * 窗口内边距
     */
    private  final int padding;
    /**
     * 边框粗细
     */
    private  final int border;
    /**
     * 左位移偏移量
     */
    private final int sizeRol;
    /**
     * 游戏失败图片索引
     */
    private final int loseIdx;

    /**
     * 图层属性
     */
    public List<LayerConfig> layerConfig;

    /**
     * 按钮属性
     */
    private final ButtonConfig buttonConfig;
    /**
     * 构造函数
     * 读取XML文件,获取窗口游戏配置
     * @throws Exception
     */
    public FrameConfig(Element frame) {
        //获取窗口宽度
        this.width = Integer.parseInt(frame.attributeValue("width"));
        //获取窗口高度
        this.height = Integer.parseInt(frame.attributeValue("height"));
        //获取边框粗细
        this.border = Integer.parseInt(frame.attributeValue("border"));
        //获取边内边框
        this.padding = Integer.parseInt(frame.attributeValue("padding"));
        //获取标题
        this.title = frame.attributeValue("title");
        //获取窗口拔高
        this.windowUP = Integer.parseInt(frame.attributeValue("windowUP"));
        //获取左位移便宜量
        this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
        //游戏失败图片索引
        this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));

        //获取窗体属性
        List<Element> layers = frame.elements("layer");
        layerConfig = new ArrayList<LayerConfig>();
        //获取所有窗体熟悉
        for(Element layer:layers){
            LayerConfig lc = new LayerConfig(
                    layer.attributeValue("className"),
                    Integer.parseInt(layer.attributeValue("x")),
                    Integer.parseInt(layer.attributeValue("y")),
                    Integer.parseInt(layer.attributeValue("w")),
                    Integer.parseInt(layer.attributeValue("h"))
            );
            layerConfig.add(lc);
        }

        buttonConfig = new ButtonConfig(frame.element("button"));
    }

    public String getTitle() {
        return title;
    }

    public int getWindowUP() {
        return windowUP;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPadding() {
        return padding;
    }

    public int getBorder() {
        return border;
    }

    public List<LayerConfig> getLayerConfig() {
        return layerConfig;
    }

    public int getSizeRol() {
        return sizeRol;
    }

    public int getLoseIdx() {
        return loseIdx;
    }

    public ButtonConfig getButtonConfig() {
        return buttonConfig;
    }
}
