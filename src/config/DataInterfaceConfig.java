package config;

import org.dom4j.Element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Order on 2016/6/30.
 */
public class DataInterfaceConfig implements Serializable {
    private final String className ;
    private final Map<String,String> param ;

    public DataInterfaceConfig(Element dataInterfaceConfig){

        this.className = dataInterfaceConfig.attributeValue("className");
        this.param =new HashMap<String, String>();
        List<Element> params = dataInterfaceConfig.elements("param");
        for (Element e:params){
           param.put(e.attributeValue("key"),e.attributeValue("value"));        }
    }

    public String getClassName() {
        return className;
    }

    public Map<String, String> getParam() {
        return param;
    }
}
