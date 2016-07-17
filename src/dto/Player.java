package dto;

import java.io.Serializable;

/**
 * Created by Order on 2016/6/18.
 */
public class Player implements Comparable<Player>,Serializable{

    private  String name;
    private  int point;


    public Player(String name, int point) {
        super();
        this.name = name;
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 比较大小
     * 1.返回 1 那么当前的值会排在 比较者前面。
     2.返回 0 那么当前的值【不会被加入到 TreeSet 中】，因为当前的值【被认为是跟现有的某一个值相等】。
     3.返回 -1 会被添加到 比较者 的后边。
     *player 当前值
     *this.point 比较者
     */
    @Override
    public int compareTo(Player player) {
        return player.point - this.point;//由大到小
    }
}
