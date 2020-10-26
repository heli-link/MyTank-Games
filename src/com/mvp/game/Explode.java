package com.mvp.game;

import java.awt.*;

/**
 * 爆炸效果
 */
public class Explode {
    private int x,y;
    private boolean isVisible = true;

    private static Image[] image;
    static {
        image = new Image[4];
        image[0] = Toolkit.getDefaultToolkit().createImage("image/bom1.png");
        image[1] = Toolkit.getDefaultToolkit().createImage("image/bom2.png");
        image[2] = Toolkit.getDefaultToolkit().createImage("image/bom3.png");
        image[3] = Toolkit.getDefaultToolkit().createImage("image/bom4.png");
    }
    private int index;

    //给 爆炸池使用
    public Explode() {
        index = 0;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }
//绘制爆炸效果
    public void draw(Graphics g){
        if(isVisible){
            g.drawImage(image[index],x,y,null);
            index++;
            if(index > 3){
                isVisible = false;
            }
        }else {
            return;
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

