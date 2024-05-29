package com.flyfiref.utils;

import com.flyfiref.obj.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.flyfiref.Main.enmC;
import static com.flyfiref.Main.enmLimit;

public class GameUtils {
    public static Image bgImg, bossImg, expImg, shellImg, planeImg, enmImg, buImg, effImg;

    static {
        try {
            bgImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/bg.jpg"));
            bossImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/boss.png"));
            expImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/exp.jpg"));
            shellImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/shell.png"));
            planeImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/plane.png"));
            enmImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/enemy.png"));
            buImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/bullet.png"));
            effImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("com/flyfiref/utils/imgs/effect.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //我方子弹集合
    public static java.util.List<ShellObj> shellObjList = new ArrayList<>();
    //敌人集合
    public static java.util.List<EnmObj> enmObjList = new ArrayList<>();
    //敌人子弹集合
    public static java.util.List<BuObj> buObjList = new ArrayList<>();
    //子弹击中消失集合
    public static java.util.List<GameObj> removeList = new ArrayList<>();
    //道具集合
    public static java.util.List<EffObj> effObjList = new ArrayList<>();
    //All game obj coll
    public static java.util.List<GameObj> gameObjList = new ArrayList<>();

    //绘制文字
    public static void drawWord(Graphics g, String s, Color color, int size, int x, int y) {
        g.setColor(color);
        g.setFont(new Font("华文新魏", Font.PLAIN, size));
        g.drawString(s, x, y);
    }
    public static void drawProg(Graphics g, int x, int y,int w,int h) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,w,h);
        g.setColor(Color.GREEN);
        g.fillRect(x,y,(int)((((double)enmLimit-enmC)/enmLimit)*w),h);
    }

    public static Clip bgm;
    public static Clip se;

    public static void playBGM(int section) {
        AudioInputStream audioInputStream;
        String sound = null;
        //BGM
        switch (section) {
            case 1 -> sound = "com/flyfiref/utils/bgm/easy.wav";
            case 2 -> sound = "com/flyfiref/utils/bgm/medium.wav";
            case 3 -> sound = "com/flyfiref/utils/bgm/hard.wav";
            case 4 -> sound = "com/flyfiref/utils/bgm/hc.wav";
            case 5 -> sound = "com/flyfiref/utils/bgm/title.wav";
            case 6 -> sound = "com/flyfiref/utils/bgm/boss.wav";
            case 7 -> sound = "com/flyfiref/utils/bgm/star4.wav";
        }
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Thread.currentThread().getContextClassLoader().getResource(sound));
            bgm = AudioSystem.getClip();
            bgm.open(audioInputStream);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSE(int section) {
        AudioInputStream audioInputStream;
        String sound = null;
        //SE
        switch (section) {
            case 1 -> sound = "com/flyfiref/utils/se/hitb.wav";
            case 2 -> sound = "com/flyfiref/utils/se/hit.wav";
            case 3 -> sound = "com/flyfiref/utils/se/death.wav";
            case 4 -> sound = "com/flyfiref/utils/se/win.wav";
            case 5 -> sound = "com/flyfiref/utils/se/up.wav";
        }
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Thread.currentThread().getContextClassLoader().getResource(sound));
            se = AudioSystem.getClip();
            se.open(audioInputStream);
            se.loop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

