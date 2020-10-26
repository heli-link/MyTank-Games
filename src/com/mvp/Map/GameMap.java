package com.mvp.Map;

import com.mvp.game.Bullet;
import com.mvp.game.GameFrame;
import com.mvp.util.Constant;
import com.mvp.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMap {
    private List<Wall> walls = new CopyOnWriteArrayList<>();
    private int x,y;

    public GameMap() {
        initMap();
    }
    //初始化地图
    private void initMap(){
//        默认生成 20 个墙壁
        int count = 20;
        for (int i = 0; i < count; i++) {
            x = MyUtil.getRandom(60,Constant.FRAME_WIDTH - 60);
            y = MyUtil.getRandom(0,Constant.FRAME_HEIGHT - GameFrame.TarbarH - 60);
            Wall wall = new Wall(x,y);
            walls.add(wall);
        }
    }
    //绘制
    public void draw(Graphics g){
        for (Wall wall : walls) {
            wall.draw(g);
        }

    }
//    private boolean isCollide(List<Wall> walls,int x,int y){
//        for (Wall wall : walls) {
//            int X = wall.getX();
//            int Y = wall.getY();
//            if(Math.abs(X - x) < wall.wallw && Math.abs(Y - y) < wall.wallw){
//                    return true;
//            }
//            return false;
//        }
//    }
    //对象不可见时（死亡）清除
    public void clear(){
        for (int i = 0; i < walls.size(); i++) {
            if(walls.get(i).isDestroy()){
                walls.remove(i);
            }
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }
}
