package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import java.awt.*;

public class BossObj extends GameObj{
    public double MAX_HP=(Main.bossHP);
    public double HP = MAX_HP;
    double speedx;
    public BossObj(Image img, int x, int y, int width, int height, double speed,double speed2, Main frame) {
        super(img, x, y, width, height, speed, frame);
        super.speed2=speed2;
        speedx = speed;
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        //Boss移动
        if(x>900||x<-10){
            speedx=-speedx;
        }
        x+=speedx;
        if(y>(Main.difficulty==4?400:200)||y<0){
            speed2=-speed2;
        }
        y+=speed2;
        //击中检测
        for(ShellObj slObj: GameUtils.shellObjList){
            if(this.getRec().intersects(slObj.getRec())){
                GameUtils.playSE(1);
                HP--;
                slObj.setX(-100); slObj.setY(-100);
                GameUtils.removeList.add(slObj);
                Main.score++;
            }
            if(HP<=0){
                GameUtils.bgm.stop();
                GameUtils.playSE(2);
                GameUtils.gameObjList.add(new ExpObj(this.x, this.y));
                slObj.setX(-200); this.x=-400;
                slObj.setY(-200); this.y=-400;
                GameUtils.removeList.add(slObj);
                GameUtils.removeList.add(this);
                GameUtils.gameObjList.remove(this);
                GameUtils.gameObjList.removeAll(GameUtils.shellObjList);
                GameUtils.shellObjList.removeAll(GameUtils.shellObjList);
                if(Main.difficulty!=5){
                    Main.state=4;
                    GameUtils.playSE(4);
                }
                if(Main.difficulty==5){
                    Main.enmC=0;
                    GameUtils.playBGM(1);
                }
            }
        }
        //血条
        g.setColor(Color.WHITE);
        g.fillRect(12,40,1000,10);
        g.setColor(Color.RED);
        g.fillRect(12,40,(int)((HP/MAX_HP)*1000),10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}

