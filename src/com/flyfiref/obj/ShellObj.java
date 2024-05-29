package com.flyfiref.obj;

import com.flyfiref.Main;
import com.flyfiref.utils.GameUtils;

import java.awt.*;

public class ShellObj extends GameObj{

    public ShellObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        y-=speed;
        //越界判断
        if(y<0){
            this.x=-500; this.y=-500;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }

}
