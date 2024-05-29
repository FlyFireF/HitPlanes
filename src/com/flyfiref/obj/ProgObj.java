package com.flyfiref.obj;

import java.awt.*;

import static com.flyfiref.Main.enmC;
import static com.flyfiref.Main.enmLimit;

public class ProgObj extends GameObj{

    public ProgObj(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintSelf(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,1000,10);
        g.setColor(Color.GREEN);
        g.fillRect(x,y,(int)((((double)enmLimit-enmC)/enmLimit)*1000),10);
    }
}
