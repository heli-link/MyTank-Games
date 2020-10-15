package com.mvp.Tank;

import com.mvp.Map.Wall;
import com.mvp.game.Bullet;
import com.mvp.game.Explode;
import com.mvp.game.GameFrame;
import com.mvp.util.BulletPool;
import com.mvp.util.Constant;
import com.mvp.util.ExplodesPool;
import com.mvp.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TransferQueue;

import static com.mvp.util.MyUtil.*;

/**
 * 坦克
 */
abstract public class Tank {
    //四个方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LIFT = 2;
    public static final int DIR_RIGHT = 3;

    //状态
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIE = 2;

    public static final int HP_MAX = 100;
    //图片的尺寸
    public static final int IMG_SIZE = 58;
    //默认速度 每帧移动的像素
    public static final int DEFAULT_SPEED = 4;

    private int x,y;
    private int hp = 100;
    private int htk;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state;
    private Color color;
    private boolean isVisible = true;

    //  TODO 子弹  使用 arrlist 会报错-遍历时存在并发问题
    private List<Bullet> bullets = Collections.synchronizedList(new ArrayList<>());
//    private List<Bullet> bullets = new ArrayList<>();
    //爆炸容器
    private List<Explode> explodes = new CopyOnWriteArrayList<>();

    private HpBar hpBar = new HpBar();

    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        color = getRandomColor();
        //随机攻击力
        htk = getRandom(10,30);
    }

    //状态处理
    private void logic(){
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
                if(y < IMG_SIZE /2 + GameFrame.TarbarH){
                    y = IMG_SIZE /2 + GameFrame.TarbarH;
                }
                break;
            case DIR_DOWN:
                 y += speed;
                if(y > Constant.FRAME_HEIGHT - IMG_SIZE /2)
                    y = Constant.FRAME_HEIGHT - IMG_SIZE /2;
                break;
            case DIR_LIFT:
                x -= speed;
                if(x < IMG_SIZE /2)
                    x = IMG_SIZE /2;
                break;
            case DIR_RIGHT:
                x += speed;
                if(x > Constant.FRAME_WIDTH - IMG_SIZE /2)
                    x = Constant.FRAME_WIDTH - IMG_SIZE /2;
                break;

        }
    }

    public void draw(Graphics g){
        logic();

        //绘制坦克
        drawImgTank(g);

        drawBullets(g);

        hpBar.draw(g);

    }

    abstract void drawImgTank(Graphics g);

    public void fire(){
        int bulletX = x;
        int bulletY = y;

        switch (dir){
            case DIR_UP:
                // 4 是偏差值 修正图片宽高不一致导致的子弹偏移
                bulletY -= IMG_SIZE/2;
                bulletX -= 4;
                break;
            case DIR_DOWN:
                bulletY += IMG_SIZE/2  - 4;
                bulletX -= 4;
                break;
            case DIR_LIFT:
                bulletX -= IMG_SIZE/2  - 4;
                bulletY -= 4;
                break;
            case DIR_RIGHT:
                bulletY -= 4;
                bulletX += IMG_SIZE/2  - 4;
                break;
        }
        //使用线程池创建子弹对象

        addBullets(bulletX,bulletY);
    }
    //添加子弹
    private void addBullets(int bulletX,int bulletY){
        Bullet bullet = BulletPool.get();
        //一定要全部设置一下，使用了子弹池，以前的属性会遗留
        bullet.setVisible(true);
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setDir(dir);
        bullet.setColor(color);
        bullets.add(bullet);


    }
    //绘制所有的子弹
    public void drawBullets(Graphics g){
//        for (Bullet bullet : bullets){
//            bullet.drow(g);
//        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.drow(g);

        }
        System.out.println("容器大小"+bullets.size());
        //遍历所有子弹，回收超出边界的
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            System.out.println("绘制一个");
//            bullet.drow(g);
            //回收
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                BulletPool.theRuturn(remove);
                //删除对象，长度减一，否则会越界
                i--;
            }
        }
    }
    //坦克和子弹的碰撞检测
    public void collideBullet(List<Bullet> bullets){
       for(Bullet bullet : bullets){
           int bulletX = bullet.getX();
           int bulletY = bullet.getY();
           if(MyUtil.isCollision(x,y,bullet.getX(),bullet.getY(),IMG_SIZE/2)){
               //子弹消失
               bullet.setVisible(false);
               //爆炸效果
               Explode explode = ExplodesPool.get();
               explode.setX(bulletX);
               explode.setY(bulletY);
//               explode.setIndex(0);
               explodes.add(explode);
               //坦克减血
               hurt(bullet);
               //标记死亡坦克
               if(hp <= 0){
                    isVisible = false;
               }
           }
       }
        
    }

    //子弹和墙的碰撞
    public void BulletCollideWall(List<Wall> walls){
        for (Wall wall : walls) {
            //子弹和墙
            boolean collide = wall.isCollideBullet(bullets);
            if(collide){
                //爆炸效果
                Explode explode = ExplodesPool.get();
                explode.setX(wall.getX());
                explode.setY(wall.getY());
//               explode.setIndex(0);
                explodes.add(explode);
            }
        }
    }
    //坦克和墙的碰撞
    public void CollideWall(List<Wall> walls){
        for (Wall wall : walls) {
           boolean result = MyUtil.isCollision(wall.getX() + Wall.radio, wall.getY()
                   + wall.radio,x,y,IMG_SIZE/2+wall.radio);
           if(result){
                back();
           }
        }
    }
    //回退
    private void back(){
        switch (dir){
            case DIR_UP:
                y += speed;
                break;
            case DIR_DOWN:
                y -= speed;
                break;
            case DIR_LIFT:
                x += speed;
                break;
            case DIR_RIGHT:
                x -= speed;
                break;
        }
    }
    //绘制所有爆炸效果
    public void drowExplpode(Graphics g){
        for (Explode explode : explodes){
            explode.draw(g);
        }
        //删除不可见的爆炸效果
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if (!explode.isVisible()){
                ExplodesPool.theRuturn(explodes.remove(i));
                i--;
            }
        }
    }
    //坦克受到伤害
    private void hurt(Bullet bullet){
        hp -= htk;
        if(hp < 0){
            hp = 0;
        }
    }
    //血条
    class HpBar {
        private static final int BAR_LENGTH = 50;
        private static final int BAR_HEIGHT = 5;
        public void draw(Graphics g){
            //绘制底色
            g.setColor(Color.yellow);
            //TODO 先以半径代替，后面改成图片
            g.fillRect(x - IMG_SIZE/2,y - IMG_SIZE/2 - BAR_HEIGHT * 2,BAR_LENGTH,BAR_HEIGHT);
            //绘制血条
            g.setColor(Color.red);
            g.fillRect(x - IMG_SIZE/2,y - IMG_SIZE/2 - BAR_HEIGHT * 2,hp*BAR_LENGTH/HP_MAX,BAR_HEIGHT);
//            //绘制边框
//            g.setColor(Color.green);
//            g.drawRect(x - IMG_SIZE,y - IMG_SIZE - BAR_HEIGHT * 2,BAR_LENGTH,BAR_HEIGHT);
        }

    }
    //销毁所有的子弹
    public void clearButtles(){
        if(bullets.size() > 0){
            for (Bullet bullet : bullets) {
                BulletPool.theRuturn(bullet);
            }bullets.clear();
        }

    }
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

}
