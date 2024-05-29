package com.flyfiref.obj;

import com.flyfiref.utils.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ExpObj extends GameObj{
    static Image pic;
    int expC=0;
    long begTime;
    static {
        //添加爆炸图
        try {
            pic= ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/exp.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void paintSelf(Graphics g) {
        if (System.currentTimeMillis() - begTime > 200){
            GameUtils.removeList.add(this);
            return;
        }
        if(expC<16){
            img=pic;
            super.paintSelf(g);
            expC++;
        }
    }

    public ExpObj(int x, int y) {
        super(x, y);
        begTime = System.currentTimeMillis();
    }

}
