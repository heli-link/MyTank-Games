package com.mvp.util;
import org.apache.commons.lang3.RandomUtils;
import java.awt.*;


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

    /**
     * 获取指定范围随机小数
     * @param max
     * @param min
     * @return
     */
    public static final double getRandomDouble(double min,double max){
        return RandomUtils.nextDouble(min,max);

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
    public static void PlayBgMusic(){
        //音乐
        MusicPlayer bgMusic = new MusicPlayer("music/bg.mp3");
        bgMusic.setLoop(true);
        bgMusic.play_mp3();
    }
    public static void PlayFireMusic(){
        //音乐
        MusicPlayer bgMusic = new MusicPlayer("music/fire.mp3");
        bgMusic.play_mp3();
    }
    public static void PlayDeathMusic(){
        //音乐
        MusicPlayer bgMusic = new MusicPlayer("music/death.wav");
        bgMusic.play_wav();
    }
    public static void PlayExplodMusic(){
        //音乐
        MusicPlayer bgMusic = new MusicPlayer("music/explode.wav");
        bgMusic.play_wav();
    }
}
