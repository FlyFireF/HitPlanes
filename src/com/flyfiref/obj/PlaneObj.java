package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaneObj extends GameObj{

    public PlaneObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        super(img, x, y, width, height, speed, frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(1==Main.state){
                    PlaneObj.super.x=e.getX()-16;
                    PlaneObj.super.y=e.getY()-21;
                }
            }
        });
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        //Boss碰撞检测
        if(this.frame.BObj!=null&&this.getRec().intersects(this.frame.BObj.getRec())){
            GameUtils.gameObjList.add(new ExpObj(this.x, this.y));
            GameUtils.playSE(2);
            GameUtils.playSE(3);
            if (!Main.undead) Main.state=3;
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }

}
