package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import java.awt.*;

public class EnmObj extends GameObj {

    public EnmObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        y+=speed;
        //击中飞机检测
        for(ShellObj slObj: GameUtils.shellObjList){
            if(this.getRec().intersects(slObj.getRec())){
                GameUtils.playSE(2);
                GameUtils.gameObjList.add(new ExpObj(this.x, this.y));
                slObj.setX(-200); this.x=-400;
                slObj.setY(-200); this.y=-400;
                GameUtils.removeList.add(slObj);
                GameUtils.removeList.add(this);
                Main.score++;
            }
        }
        //敌我碰撞检测
        if(this.getRec().intersects(this.frame.plObj.getRec())){
            GameUtils.gameObjList.add(new ExpObj(this.x, this.y));
            this.x=-400;this.y=-400;
            GameUtils.removeList.add(this);
            GameUtils.playSE(2);
            GameUtils.playSE(3);
            if (!Main.undead) Main.state=3;
        }
        //越界判断
        if(y>768){
            this.x=-400;this.y=-400;
            GameUtils.removeList.add(this);
            GameUtils.playSE(3);
            if (!Main.undead) Main.state=3;
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
