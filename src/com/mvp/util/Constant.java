package com.mvp.util;

import com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_NR_UDS;

import java.awt.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * 常量类
 */
public class Constant {
    /****************** 窗口相关  ************************/
    public static final String GAME_TITLE = "坦克对战";
//    1156 649 45
    public static final int FRAME_WIDTH = 1156;
    public static final int FRAME_HEIGHT = 694;
//  居中显示 左上角坐标  右移表示除以2  不要自己算
    public static final int FRAME_X = 1920 - FRAME_WIDTH >> 1;
    public static final int FRAME_Y = 1080 - FRAME_HEIGHT >> 1;

    /********************* 游戏状态  *****************************/
    public static final int START_MENU = 0;
    public static final int START_OVER = 1;

    public static final int START_RUN = 2;

    public static final String[] MENUS = {
        "开始游戏",
            "退出游戏"
    };
    //字体设置
    public static final Font MENU_FONT = new Font("黑体",Font.BOLD,24);
    //刷新时间
    public static final int REPAINT_INTERVAL = 30;

    /********************  敌方坦克 ************************/
    public static final int ENEMY_MAX = 10;
    public static final int PRODUCK_SPEED = 3000;
    //ai属性   改变状态和发射子弹间隔
    public static final int CHANG_TIME = 2000;
    public static final double FIRE_P = 0.05;


}
