package com.mvp.Map;

import com.mvp.Tank.Tank;
import com.mvp.game.Bullet;
import com.mvp.game.Explode;
import com.mvp.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Wall {
    private int x,y;
    public static int wallw = 50;
    public static int radio = wallw/2;
    private static Image image;

    private boolean isDestroy = false;
//   静态代码块
    static {
         image = Toolkit.getDefaultToolkit().createImage("image/wall0.png");
    }

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //绘制
    public void draw(Graphics g){
        if(isDestroy){
           return;
        }
        g.drawImage(image,x,y,null);
    }
    //子弹和墙的碰撞
    public boolean isCollideBullet(List<Bullet> bullets){
        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collision = MyUtil.isCollision(x + radio, y + radio, bulletX, bulletY, radio);
            //子弹消失
            if(collision){
                bullet.setVisible(false);
                //地图块消失
                isDestroy = true;
            }
            return collision;
        }
        return false;
    }
    public boolean isDestroy() {
        return isDestroy;
    }
    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
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
}
