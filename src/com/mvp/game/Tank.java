package com.mvp.game;

import com.mvp.util.Constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.mvp.util.Constant.FRAME_HEIGHT;
import static com.mvp.util.Constant.FRAME_WIDTH;
import static com.mvp.util.MyUtil.*;

/**
 * 坦克
 */
public class Tank {
    //四个方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LIFT = 2;
    public static final int DIR_RIGHT = 3;

    //状态
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIE = 2;

    //n半径
    public static final int RADIUS = 16;
    //默认速度 每帧移动的像素
    public static final int DEFAULT_SPEED = 4;

    private int x,y;
    private int hp;
    private int htk;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state;
    private Color color;

    //  TODO 子弹
    private List bullet = new ArrayList();

    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        color = getRandomColor();
    }
    //状态处理
    public void logic(){
        switch (state){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }
    }

    private void move() {
        switch (dir){
            case DIR_UP:
                y -= speed;
                if(y < RADIUS + 45)
                    y = RADIUS + 45;
                break;
            case DIR_DOWN:
                 y += speed;
                if(y > Constant.FRAME_HEIGHT - RADIUS)
                    y = Constant.FRAME_HEIGHT - RADIUS;
                break;
            case DIR_LIFT:
                x -= speed;
                if(x < RADIUS)
                    x = RADIUS;
                break;
            case DIR_RIGHT:
                x += speed;
                if(y > Constant.FRAME_WIDTH - RADIUS)
                    y = Constant.FRAME_WIDTH - RADIUS;
                break;

        }
    }

    public void draw(Graphics g){
        //填充背景
        g.setColor(Color.black);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        logic();
        g.setColor(color);
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS << 1,RADIUS << 1);
        //绘制炮筒
        int endx = x;
        int endy = y;
        switch (dir){
            case DIR_UP:
                endy = y - RADIUS*2;
                break;
            case DIR_DOWN:
                endy = y + RADIUS*2;
                break;
            case DIR_LIFT:
                endx = x - RADIUS*2;
                break;
            case DIR_RIGHT:
                endx = x + RADIUS*2;
                break;
        }
        g.drawLine(x,y,endx,endy);
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHtk() {
        return htk;
    }

    public void setHtk(int htk) {
        this.htk = htk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullet() {
        return bullet;
    }

    public void setBullet(List bullet) {
        this.bullet = bullet;
    }
}
