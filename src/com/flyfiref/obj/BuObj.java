package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import javax.swing.*;
import java.awt.*;

import static com.flyfiref.Main.score;

public class BuObj extends GameObj{

    public BuObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        y+=speed;
        //敌我碰撞检测
        if(this.getRec().intersects(this.frame.plObj.getRec())){
            GameUtils.gameObjList.add(new ExpObj(this.x, this.y));
            GameUtils.playSE(2);
            GameUtils.playSE(3);
            this.x=-400;this.y=-400;
            GameUtils.removeList.add(this);
            if (!Main.undead) Main.state=3;
        }
        //越界判断
        if(y>768){
            this.x=-300; this.y=-300;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
