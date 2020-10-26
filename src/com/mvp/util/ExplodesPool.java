package com.mvp.util;

import com.mvp.game.Bullet;
import com.mvp.game.Explode;

import java.util.ArrayList;
import java.util.List;

/**
 * 爆炸效果池
 */
public class ExplodesPool {
    public static final int DEFAULT_POOL_SIZE = 20;
    //爆炸容器
    public static List<Explode> pool = new ArrayList<>();
    //爆炸数量的限值
    private static int POOL_MAX = 30;

    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }
    public static Explode get(){
        Explode explode = null;
        if(pool.size() == 0){
            explode = new Explode();
        }else {
            //删除第一个位置的对象并返回该对象
            explode = pool.remove(0);
        }
        return explode;

    }
    public static void theRuturn(Explode explode){
        if(pool.size() == POOL_MAX){
            return;
        }
        pool.add(explode);
    }

}
