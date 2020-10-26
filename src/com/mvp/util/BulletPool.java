package com.mvp.util;

import com.mvp.game.Bullet;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

/**
 * 子弹池
 */
public class BulletPool {
    public static final int DEFAULT_POOL_SIZE = 200;
    //子弹容器
    public static List<Bullet> pool = new ArrayList<>();
    //子弹池的限值
    private static int POOL_MAX = 300;

    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Bullet());
        }
    }
//    获取一个子弹对象
    public static Bullet get(){
        Bullet bullet = null;
        if(pool.size() == 0){
            bullet = new Bullet();
        }else {
            //删除第一个位置的对象并返回该对象
            bullet = pool.remove(0);
        }
//        System.out.println("发出一个子弹，还剩下"+pool.size());
        return bullet;

    }
//    回收一个子弹对象
    public static void theRuturn(Bullet bullet){
        if(pool.size() == POOL_MAX){
            return;
        }
//        System.out.println("回收一个子弹,一共有"+pool.size());
        pool.add(bullet);
    }

}
