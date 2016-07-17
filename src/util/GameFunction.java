package util;

/**
 * Created by Order on 2016/7/11.
 */
public class GameFunction {

    /**
     * 计算线程睡眠时间
     */
    public static long getSleepTimeByLevel(int level){

        long sleep = (long)(-40*(level+1)+780);
        sleep = sleep<100?100:sleep;
        return sleep;
    }
}
