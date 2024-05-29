package com.flyfiref.obj;
import com.flyfiref.Main;

import java.awt.*;

public class GameObj {
    Image img;
    int x,y,width,height;
    public double speed,speed2;
    Main frame;

    public GameObj(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public GameObj(Image img, int x, int y, int width, int height, double speed, Main frame) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }

    public GameObj(int x, int y) {
        this.x=x;this.y=y;
    }

    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
