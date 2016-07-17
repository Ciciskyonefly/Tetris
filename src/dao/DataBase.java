package dao;

import dto.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Order on 2016/6/22.
 */
public class DataBase implements Data {


    //数据库驱动，固定的，但根据SQL Server版本也有不同，自己百度找符合自己SQL Server版本的，这个是2014
    private final String driver;
    //服务器URL
    private final String dbUrl;
    //用户名
    private final String dbUser;
    //用户密码
    private final String dbPwd;

    private static String LOAD_SQL = "SELECT TOP 5 user_name,point FROM user_point ORDER BY point DESC";

    private static String SAVE_SQL = "INSERT INTO user_point(user_name,point,type_id) VALUES(?,?,?)";


    public DataBase(HashMap<String, String> param){

        this.dbUrl = param.get("dbUrl");
        this.dbUser = param.get("dbUser");
        this.dbPwd = param.get("dbPwd");
        this.driver = param.get("driver");
        try {
            Class.forName(this.driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Player> loadData() {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Player> players = new ArrayList<Player>();
        try {
            //连接数据库
            conn  = DriverManager.getConnection(dbUrl,dbUser,dbPwd);
            stmt = conn.prepareStatement(LOAD_SQL);
            rs = stmt.executeQuery();

            while (rs.next()){
                players.add(new Player(rs.getString(1), rs.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) conn.close();
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return players;
    }

    @Override
    public void saveData(Player player) {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn  = DriverManager.getConnection(dbUrl,dbUser,dbPwd);
            stmt = conn.prepareStatement(SAVE_SQL);
            stmt.setObject(1,player.getName());
            stmt.setObject(2,player.getPoint());
            stmt.setObject(3,1);
             stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) conn.close();
                if(stmt != null) stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) throws Exception {

    }


}
