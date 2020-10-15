package com.mvp.util;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

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

    /**
     * 判断点和矩形的碰撞
     * @param rectX
     * @param rectY
     * @param rodX
     * @param rodY
     */
    public static boolean isCollision(int rectX,int rectY,int rodX,int rodY,int range){
        int disX = Math.abs(rectX -rodX);
        int disY = Math.abs(rectY - rodY);
        if(disX < range && disY < range){
            return true;
        }
        return false;
    }

    /**
     * 加载图片
     * @param filename
     * @return
     */
    public static Image getImg(String filename){
       return Toolkit.getDefaultToolkit().createImage(filename);
    }

}
