package com.mvp.game;

import com.mvp.Tank.Tank;
import com.mvp.util.Constant;
import com.mvp.util.MyUtil;

import java.awt.*;

/**
 * 子弹类
 */
public class Bullet {
    private static int DEFAULE_SPEED = Tank.DEFAULT_SPEED * 2;
    //n半径
    public  int RADIUS = 4;

    private int dir;

    private int x,y;
    private int speed= DEFAULE_SPEED;
//    是否存活
    private boolean isVisible = true;
    private Color color;

    public Bullet(int x, int y,int dir,Color color) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    //给对象池使用的
    public Bullet() {
    }
    public void drow(Graphics g){
        System.out.println("该子弹的状态"+isVisible);
        if(!isVisible){
            return;
        }
        logic();
        g.setColor(color);
        g.fillOval(x - RADIUS,y - RADIUS,RADIUS << 1,RADIUS << 1);

    }
    private void logic(){
        move();
    }
//    移动
    private void move(){
        switch (dir){
            case Tank.DIR_UP:
                y -= speed;
                if(y < 0)
                    isVisible = false;
                break;
            case Tank.DIR_DOWN:
                y += speed;
                if(y > Constant.FRAME_HEIGHT){
                    isVisible = false;
                }
                break;
            case Tank.DIR_LIFT:
                x -= speed;
                if(x < 0)
                    isVisible = false;
                break;
            case Tank.DIR_RIGHT:
                x += speed;
                if(x > Constant.FRAME_WIDTH){
                    isVisible = false;
                }
                break;
        }
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public void setRADIUS(int RADIUS) {
        this.RADIUS = RADIUS;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

