package uninstall;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame {
    JFrame jframe = new JFrame();
    JPanel jpanel = null;
     //初始页面背景
    String bgPath = "image/welcome_bg.jpg";


    WelcomeFrame(){
        setJframe();
        //绘制背景
        jpanel = new DrawImg(bgPath, jframe);
        jpanel.setLayout(null);
        jpanel.add(Exit());
        jpanel.add(Start());

        jframe.add(jpanel);
        //自动调整窗口大小 自动刷新一次，避免bug
        jframe.pack();
    }
    public void setJframe(){
        //定位到屏幕中间
        jframe.setLocation(460,200);
        jframe.setTitle("坦克世界");
//        jframe.setLayout(null);
////        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        //固定窗口大小 ，图片不能完全填充
//        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void PlayGame() {
//        Container pane = jpanel.getRootPane().getContentPane();
//        pane.remove(jpanel);
//        pane.add(new GameJpanel());
//        pane.validate();//重构界面
//        pane.repaint();//重新绘制
        new GameJframe();

    }
    //开始
    private JButton Start(){
        JButton startGame = new JButton("开始游戏");
        startGame.setContentAreaFilled(false);
        // 当容器的布局管理器为null时setSize和setBounds才能起作用，
        startGame.setBounds(370,150,300,50);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayGame();
            }
        });
        return startGame;
    }
    //退出游戏
    private JButton Exit(){
        JButton exitGame = new JButton("退出游戏");
        exitGame.setBounds(370,230,300,50);
        //显示/隐藏
        exitGame.setContentAreaFilled(false);
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return exitGame;
    }

    public static void main(String[] args) {
       new WelcomeFrame();
    }
}
