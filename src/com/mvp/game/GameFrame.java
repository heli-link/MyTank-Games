package com.mvp.game;

import javax.swing.*;
//静态导入
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.mvp.util.Constant.*;

/**
 * 主窗口
 */
public class GameFrame extends JFrame implements Runnable{

    //游戏状态
    public static int GameState;
    //菜单光标位置
    private static int MenuIndex;
    //创建坦克
    private Tank tank;
    Image image;
    public GameFrame(){
        initFrame();
        initEventListener();
        initGame();
        new Thread(this).start();
    }

    @Override
    public void repaint() {
        super.repaint();
        update(getGraphics());
    }

    private void initGame(){
        GameState = START_MENU;

    }
    private void initFrame(){
        setTitle(GAME_TITLE);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setLocation(FRAME_X,FRAME_Y);
        setVisible(true);
    }

    /**
     * 该方法是 repaint的回调
     * @param g
     */
    @Override
    public void update(Graphics g) {
//        双缓冲解决闪烁
        image = createImage(FRAME_WIDTH, FRAME_HEIGHT);
        Graphics bg = image.getGraphics();
        bg.setFont(MENU_FONT);
        switch (GameState){
            case START_MENU:
                drawMenu(bg);
                break;
            case START_ABOUT:
                drawAbout(bg);
                break;
            case START_HELP:
                drawHelp(bg);
                break;
            case START_RUN:
                drawRun(bg);
                break;
            case START_OVER:
                drawOver(bg);
                break;
        }
        g.drawImage(image,0,0,null);
    }

    private void drawOver(Graphics g) {

    }

    private void drawRun(Graphics g) {
        tank.draw(g);
    }

    private void drawHelp(Graphics g) {

    }

    private void drawAbout(Graphics g) {

    }
    public void drawMenu(Graphics g){
        //设置画笔 填充背景
        g.setColor(Color.black);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        //绘制菜单
        final int STR_WIDTH = 50;
        int x = FRAME_WIDTH - STR_WIDTH >> 1;
        int y = FRAME_HEIGHT/3;
        final int DIS = 50;
        for (int i = 0; i < MENUS.length ; i++) {
            if(MenuIndex == i){
                g.setColor(Color.red);
            }else {
                g.setColor(Color.white);
            }
            g.drawString(MENUS[i],x,y+DIS*i);
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
                switch (GameState){
                    case START_MENU:
                        keyEventMenu(keyCode);
                        break;
                    case START_ABOUT:
                        break;
                    case START_HELP:
                        break;
                    case START_RUN:
                        keyEventRun(keyCode);
                        break;
                    case START_OVER:
                        break;
                }
            }

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

    private void keyReleasedRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
                tank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_DOWN:
                tank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_LEFT:
                tank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_RIGHT:
                tank.setState(Tank.STATE_STAND);
                break;

        }
    }

    private void keyEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
                tank.setDir(Tank.DIR_UP);
                tank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_DOWN:
                tank.setDir(Tank.DIR_DOWN);
                tank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_LEFT:
                tank.setDir(Tank.DIR_LIFT);
                tank.setState(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_RIGHT:
                tank.setDir(Tank.DIR_RIGHT);
                tank.setState(Tank.STATE_MOVE);
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
                newGame();
        }
    }

    /**
     * 开始游戏
     */
    private void newGame() {
        tank = new Tank(200,300,Tank.DIR_DOWN);
        GameState = START_RUN;
    }

    //线程刷新界面
    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(REPAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
