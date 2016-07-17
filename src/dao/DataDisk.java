package dao;

import dto.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Order on 2016/6/23.
 */
public class DataDisk implements Data {


    private final String filePath ;
    public DataDisk(HashMap<String,String> param){
        this.filePath = param.get("path");
    }
    @Override
    public List<Player> loadData() {
        // Creates an ObjectInputStream that reads from the specified InputStream.布吉岛怎么翻译...
        ObjectInputStream ois = null;
        //存取读取的文件
        List<Player> players = null;
        //获取文件内容的字节数据
        FileInputStream fs = null;
        try {
            players = new ArrayList<Player>();
            fs =  new FileInputStream(filePath);
            //判断文件是否为空，如果文件不为空
            if(fs.read() != -1){
                //则关闭重新读，fs.read读的是一个字符，已经读了一个字节进来,后面不是完整的文件，所以在序列化的时候会报错
                fs.close();
                //读取本地FILE_PATH里的所有内容
                ois = new ObjectInputStream(new FileInputStream(filePath));
                players = (List<Player>)ois.readObject();
            }
            for (int i = players.size(); i<5;i++){
                players.add(new Player("NO DATA",-1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ois != null)
                     ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    @Override
    public void saveData(Player player) {



        //先取出数据
        List<Player> players = this.loadData();
        //追加新数据
        players.add(player);

        //从大到小排序
       Collections.sort(players);

       for (int i = players.size()-1; i > 4; i--) players.remove(i);

        //对象流重新写入
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(players);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
