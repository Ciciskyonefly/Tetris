package dao;

import dto.Player;

import java.util.List;

/**
 * Created by Order on 2016/6/22.
 */
public interface Data {

     List<Player> loadData();

    void saveData(Player player);


}
