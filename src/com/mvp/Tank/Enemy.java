package com.mvp.Tank;

import com.mvp.game.GameFrame;
import com.mvp.util.MyUtil;

import java.awt.*;

import static com.mvp.util.Constant.*;

public class Enemy extends Tank{

    private static long aiTime;
    private static Image[] image;
    private static double FIRE_P = 0;
//    静态代码块
    static {
        image = new Image[4];
        image[0] = Toolkit.getDefaultToolkit().createImage("image/en_u.png");
        image[1] = Toolkit.getDefaultToolkit().createImage("image/en_d.png");
        image[2] = Toolkit.getDefaultToolkit().createImage("image/en_l.png");
        image[3] = Toolkit.getDefaultToolkit().createImage("image/en_r.png");
    }

    public Enemy(int x, int y, int dir) {
        super(x, y, dir);
//        随机速度 血量
        setSpeed(MyUtil.getRandom(4,20));
        int hp = MyUtil.getRandom(50,600);
        setHP_MAX(hp);
        setHp(hp);
//        double orderAmount = RandomUtils.nextDouble(0.02, 0.09);
        FIRE_P = MyUtil.getRandomDouble(0.05,0.1);
    }
    //创建敌方坦克
    public static Tank creatEnemyTank() {
        int x = MyUtil.getRandom(0,2) == 0 ? IMG_SIZE/2 : FRAME_WIDTH - IMG_SIZE/2;
        int y = IMG_SIZE/2 + GameFrame.TarbarH;

        Tank enemyTank = new Enemy(x, y, DIR_DOWN);
        enemyTank.setState(STATE_MOVE);
        //记录时间
        aiTime = System.currentTimeMillis();
        return enemyTank;
    }
    //ai
    public void ai(){
        if (System.currentTimeMillis() - aiTime > CHANG_TIME){
            aiTime = System.currentTimeMillis();
            setDir(MyUtil.getRandom(0,4));
            setState(MyUtil.getRandom(0,2));
        }
        //随机一个0-1的数，小于约定的大小就开火 用于控制敌方坦克的开火频率
        if(Math.random() < FIRE_P){
            fire();
        }
    }
//    绘制敌方坦克
    @Override
    void drawImgTank(Graphics g) {
        ai();
        g.drawImage(image[getDir()],getX()- IMG_SIZE /2,getY()- IMG_SIZE /2,null);

    }

}
