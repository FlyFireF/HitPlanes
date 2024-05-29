package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import java.awt.*;
import java.util.Random;

public class EffObj extends GameObj{
    public EffObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        y-=speed;
        Random r =new Random();
        //吃到道具检测
        if(this.getRec().intersects(this.frame.plObj.getRec())){
            GameUtils.playSE(5);
            for(int i = 0;i<=this.frame.WIDTH;i+=15){
                GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,i-5+r.nextInt(10),768-r.nextInt(50),14,29,5+r.nextInt(5),this.frame));
                GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
            }
            for(GameObj obj : GameUtils.enmObjList){
                obj.speed=1;
            }
            GameUtils.removeList.addAll(GameUtils.buObjList);
            this.x=-400;
            this.y=-400;
            GameUtils.removeList.add(this);

        }

        //越界判断
        if(y<0){
            this.x=-300; this.y=-300;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
