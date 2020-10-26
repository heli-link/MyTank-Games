package com.mvp.Tank;

import java.awt.*;

public class MyTank extends Tank{

    private static Image[] image;
    static {
        image = new Image[4];
        image[0] = Toolkit.getDefaultToolkit().createImage("image/tank_up.png");
        image[1] = Toolkit.getDefaultToolkit().createImage("image/tank_down.png");
        image[2] = Toolkit.getDefaultToolkit().createImage("image/tank_left.png");
        image[3] = Toolkit.getDefaultToolkit().createImage("image/tank_right.png");
    }

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
        setSpeed(15);
        setEnemy(false);
    }

    //重写父类方法，绘制我方坦克
    @Override
    void drawImgTank(Graphics g) {
        if(isVisible()){
            g.drawImage(image[getDir()],getX()- IMG_SIZE /2,getY()- IMG_SIZE /2,null);
        }
         }
}
