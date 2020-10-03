package com.mvp.util;

import java.awt.*;
import java.util.Random;

/**
 *  工具类
 */
public class MyUtil {
    /**
     * 返回区间随机数
     * @param max
     * @param min 最小值，包含
     * @return
     */
    public static final int getRandom(int min,int max){
        return (int) (Math.random()*(max-min) + min);
    }
    public static final Color getRandomColor(){
        int red = getRandom(0, 256);
        int green = getRandom(0, 256);
        int blue = getRandom(0, 256);
        return new Color(red,green,blue);
    }

}
