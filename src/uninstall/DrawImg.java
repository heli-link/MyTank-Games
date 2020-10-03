package uninstall;

import javax.swing.*;
import java.awt.*;

public class DrawImg extends JPanel {

    private String path;
    private JFrame jFrame;
    //绘制背景
    public DrawImg(String path, JFrame jFrame){
        this.path = path;
        this.jFrame = jFrame;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //使用工具类获得图片对象
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        g.drawImage(img, 0, 0, icon.getIconWidth(),
                icon.getIconHeight(), icon.getImageObserver());
        jFrame.setSize(icon.getIconWidth(), icon.getIconHeight() + 45);
        //1156 649 45
    }


}
