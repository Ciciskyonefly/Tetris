package config;

import org.dom4j.Element;

import java.io.Serializable;

/**
 * Created by Order on 2016/6/30.
 */
public class DataConfig implements Serializable{

    /**
     * 获取最大数据行
     */
    private final int maxRow;
    /**
     * 数据访问配置接口A
     */
    private final DataInterfaceConfig dataA;
    /**
     * 数据访问配置接口B
     */
    private final DataInterfaceConfig dataB;



    /**
     *构造函数
     * 获取数据访问配置
     */
    public DataConfig(Element data){
        this.dataA = new DataInterfaceConfig(data.element("dataA"));
        this.dataB = new DataInterfaceConfig(data.element("dataB"));
        this.maxRow = Integer.parseInt(data.attributeValue("maxRow"));
    }

    public DataInterfaceConfig getDataA() {
        return dataA;
    }

    public DataInterfaceConfig getDataB() {
        return dataB;
    }

    public int getMaxRow() {
        return maxRow;
    }

}
