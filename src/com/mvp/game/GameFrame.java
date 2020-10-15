package com.mvp.game;

import com.mvp.Map.GameMap;
import com.mvp.Map.Wall;
import com.mvp.Tank.Enemy;
import com.mvp.Tank.MyTank;
import com.mvp.Tank.Tank;

//静态导入

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static com.mvp.util.Constant.*;

/**
 * 主窗口  使用 Jframe 集合不会报错，会闪，Frame 不会闪，子弹集合会报同步错误
 */
public class GameFrame extends JFrame {

    //游戏状态
    public static int GameState;
    //菜单光标位置
    private static int MenuIndex;
    //标题栏高度
    public static int TarbarH;
    //创建坦克
    private Tank myTank;
    //敌方坦克容器
    private List<Tank> enemys = new ArrayList<>();
    //地图
    private GameMap gameMap = new GameMap();;

    //双缓冲的画布
    Image image = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    //欢迎界面背景和游戏背景
    static Image welcomebg,gamebg,start_0,start_1,exit_0,exit_1;
    static {
        welcomebg = Toolkit.getDefaultToolkit().createImage("image/welcome.png");
        gamebg = Toolkit.getDefaultToolkit().createImage("image/gamebg.png");
        //开始游戏图标
        start_0 = Toolkit.getDefaultToolkit().createImage("image/start_0.png");
        start_1 = Toolkit.getDefaultToolkit().createImage("image/start_1.png");
        exit_0 = Toolkit.getDefaultToolkit().createImage("image/exit_0.png");
        exit_1 = Toolkit.getDefaultToolkit().createImage("image/exit_1.png");
    }
    public GameFrame(){
        initFrame();
        initEventListener();
        initGame();
        //使用线程刷新
//        new Thread(this).start();
        //使用定时器刷新
        refrash();

    }

    @Override
    public void repaint() {
        //调用父类会疯狂闪烁
//        super.repaint();
        update(getGraphics());
    }

    private void initGame(){
        //初始化
        GameState = START_MENU;

    }
    private void initFrame(){
        setTitle(GAME_TITLE);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setLocation(FRAME_X,FRAME_Y);
       // Jframe 特有 关闭窗口 ，Frame使用监听事件关闭
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //可见
        setVisible(true);
        //获取标题栏高度
        Insets insets = getInsets();
        TarbarH = insets.top;
    }

    /**
     * 该方法是 repaint的回调
     * @param
     */
    @Override
    public void update(Graphics g) {
//        双缓冲解决闪烁
        Graphics gg = image.getGraphics();
        gg.setFont(MENU_FONT);
        switch (GameState){
            case START_MENU:
                drawMenu(gg);
                break;
            case START_RUN:
                drawRun(gg);
                break;
            case START_OVER:
                drawOver(gg);
                break;
        }
        g.drawImage(image,0,0,null);
    }
   //绘制死亡页面
    private void drawOver(Graphics g) {
        //填充背景 后面可修改 TODO
        g.setColor(Color.black);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        //死亡提示
        int STR_W = 600;
        int x = (FRAME_WIDTH - STR_W) / 2;
        int y = FRAME_HEIGHT*1/4;
        g.setColor(Color.red);
        g.setFont(new Font("黑体",Font.BOLD,74));
        g.drawString("You Are Death!!!",x,y);
        //信息提示
        STR_W = 300;
        x = (FRAME_WIDTH - STR_W) / 2;
        y = FRAME_HEIGHT*3/4;
        g.setFont(new Font("黑体",Font.BOLD,24));
        g.setColor(Color.white);
        g.drawString("按 enter 键回到主菜单",x,y);
        //清除所有对象
        clearAll();

    }

    //游戏运行过程中绘制内容
    private void drawRun(Graphics g) {
        //填充背景
        g.drawImage(gamebg,0,0,null);
//        g.setColor(Color.black);
//        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

        //敌方坦克绘制
        drowEnemy(g);
        //我方坦克绘制
        drowMyTank(g);
        //地图
        gameMap.draw(g);
        //碰撞检测 坦克
        TankCollideBullet();
        //子弹和墙的碰撞
        BulletCollideWall();
        //坦克和墙
        TankCollideWall();
        //爆炸
        drawExplode(g);
    }
    private void drowMyTank(Graphics g){
        if(myTank.isVisible()){
            myTank.draw(g);
        }else {
            GameState = START_OVER;
        }

    }
    private void drowEnemy(Graphics g){
        for (int i = 0; i < enemys.size(); i++) {
            Tank tank = enemys.get(i);
            //判断生死标志位
            if(tank.isVisible()){
                //存活绘制
                tank.draw(g);
            }else {
                //死亡清除
                enemys.remove(tank);
            }

        }

    }
    //绘制菜单页面
    public void drawMenu(Graphics g){
        //设置画笔 填充背景
        g.drawImage(welcomebg,0,0,null);
//        g.setColor(Color.black);
//        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        //绘制菜单
        final int STR_WIDTH = 100;
        int x = FRAME_WIDTH -  STR_WIDTH >> 1;
        int y = FRAME_HEIGHT*3/4;
        final int DIS = 50;
//        for (int i = 0; i < MENUS.length ; i++) {
//            if(MenuIndex == i){
//                //选中
//                g.setColor(Color.red);
//            }else {
//
//                g.setColor(Color.white);
//            }
//            g.drawString(MENUS[i],x,y+DIS*i);
//        }
        if(MenuIndex == 0){
            //选中开始游戏
            g.drawImage(start_0,x,y,null);
            g.drawImage(exit_1,x,y+DIS,null);
        }else {
            //选中退出游戏
            g.drawImage(start_1,x,y,null);
            g.drawImage(exit_0,x,y+DIS,null);
        }
    }

    /**
     * 管理监听事件
     */
    private void initEventListener(){
        //注册监听事件
        addWindowListener(new WindowAdapter() {
            //关闭按钮 系统调用改该法
            @Override
            public void windowClosing(WindowEvent e) {
                //关闭 jvm
                System.exit(0);
            }
        });
        //注册键盘监听事件
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
//                System.out.println("sta:"+GameState);
                switch (GameState){
                    case START_MENU:
                        keyEventMenu(keyCode);
                        break;
                    case START_RUN:
                        keyEventRun(keyCode);
                        break;
                    case START_OVER:
                        keyEventOver(keyCode);
                        break;
                }
            }

            //按键抬起事件
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (GameState){
                    case START_RUN:
                        keyReleasedRun(keyCode);
                        break;
                }
            }
        });
    }

    //游戏结束页面监听事件
    private void keyEventOver(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER){
            GameState = START_MENU;
        }
    }

    /**
     * 运行时按键抬起处理
     * @param keyCode
     */

    private void keyReleasedRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
                myTank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_DOWN:
                myTank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_LEFT:
                myTank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_RIGHT:
                myTank.setState(Tank.STATE_STAND);
                break;

        }
    }

    /**
     * 运行时按键按下处理
     * @param keyCode
     */
    private void keyEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
                myTank.setDir(Tank.DIR_UP);
                myTank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_DOWN:
                myTank.setDir(Tank.DIR_DOWN);
                myTank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_LEFT:
                myTank.setDir(Tank.DIR_LIFT);
                myTank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_RIGHT:
                myTank.setDir(Tank.DIR_RIGHT);
                myTank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_SPACE:
                myTank.fire();
                break;
        }
    }

    //menu 按键处理
    private void keyEventMenu(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
                if(--MenuIndex < 0){
                    MenuIndex = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                if(++MenuIndex > MENUS.length -1){
                    MenuIndex = MENUS.length - 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                if(MenuIndex == 0){
                    //开始游戏
                    newGame();
                } else if(MenuIndex == 1){
                //退出游戏
                System.exit(0);
            }
                break;
        }
    }

    /**
     * 开始游戏
     */
    private void newGame() {
        myTank = new MyTank(200,300,Tank.DIR_DOWN);
        GameState = START_RUN;
        //创建敌方坦克
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if(GameState != START_RUN){
                        return;
                    }
                    //没有超过限制 就不停的创建
                    if(enemys.size() < ENEMY_MAX){
                        Tank enemy = Enemy.creatEnemyTank();
                        enemys.add(enemy);
                    }
                    try {
                        sleep(PRODUCK_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
    //清除所有对象，返回主菜单
    private void clearAll(){
        MenuIndex = 0;
        // TODO 销毁自己的坦克和子弹 空指针异常 未解决
//        myTank.clearButtles();
        myTank = null;
        //销毁敌方坦克和对象
        for (Tank enemy : enemys) {
            enemy.clearButtles();
        }
        enemys.clear();

    }
    //坦克和子弹的碰撞检测
    private void TankCollideBullet(){
        //我的坦克和敌人的子弹
        for (Tank enemy : enemys){
            enemy.collideBullet(myTank.getBullets());
        }
        //我的子弹和敌人的坦克
        for (Tank enemy : enemys){
            myTank.collideBullet(enemy.getBullets());
        }
    }
    //所有子弹和墙的碰撞
    private void BulletCollideWall(){
        List<Wall> walls = gameMap.getWalls();
        myTank.BulletCollideWall(walls);
        for (Tank enemy : enemys) {
            enemy.BulletCollideWall(walls);
        }
        //清理销毁对象
        gameMap.clear();
    }
    //坦克和墙的碰撞
    private void TankCollideWall(){
        List<Wall> walls = gameMap.getWalls();
        myTank.CollideWall(walls);
        for (Tank enemy : enemys) {
            enemy.CollideWall(walls);
        }
    }
    //爆炸
    private void drawExplode(Graphics g){
        for (Tank enemy : enemys){
            enemy.drowExplpode(g);
        }
        myTank.drowExplpode(g);
    }
    //线程刷新界面
//    @Override
//    public void run() {
//        while (true){
//            repaint();
//            try {
//                Thread.sleep(REPAINT_INTERVAL);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
    public void refrash(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },100,REPAINT_INTERVAL);
    }



}
