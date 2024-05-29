package com.flyfiref;
import com.flyfiref.obj.*;
import com.flyfiref.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Main extends JFrame {
    //状态 0未开始 1正在游戏 2暂停 3失败 4成功
    //无敌模式
    public static boolean undead;
    public boolean win;
    public static boolean end;
    public static int state = 0;
    public static Image offScreenImage=null;
    //宽高
    public final int WIDTH=1024,HEIGHT=768;
    //得分
    public static int score=0;
    //敌人数量
    public static int enmC=0;
    //难度
    public static int difficulty;
    //重绘次数
    int repaintCount=1;
    //背景对象
    GameObj bgObj = new BgObj(GameUtils.bgImg,0,0,0);
    //我方飞机对象
    public GameObj plObj = new PlaneObj(GameUtils.planeImg,500,700,30,40,0,this);
    //Boss对象
    public BossObj BObj = null;
    //进度条
    public ProgObj progObj=new ProgObj(12,40);
    //敌人移动速度
    public static double enmMoveSpeed;
    //BOSS子弹移动速度
    public static double buMoveSpeed;
    //BOSS子弹发射间隔
    public static double buInterval;
    //小怪子弹发射间隔
    public static double enmInterval;
    //生成BOSS的小怪数量
    public static int enmLimit;
    //BOSS血量
    public static int bossHP;
    public void launch(){
        final Main This = this;
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setTitle("Hit Planes!");
        //添加游戏元素
        GameUtils.gameObjList.add(bgObj);
        GameUtils.gameObjList.add(plObj);
        GameUtils.gameObjList.add(progObj);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(1==e.getButton()&&(0==state||5==state)){
                    new DifficultyDialog(This);
                    This.setEnabled(false);
                    repaint();
                }
            }
        });
        //暂停
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if('p'==e.getKeyChar() || 'P'==e.getKeyChar()){
                    switch (state) {
                        case 1 -> state = 2;
                        case 2 -> state = 1;
                    }
                }
            }
        });
        this.setVisible(true);
        GameUtils.playBGM(5);
        while (true){
            if(1==state){
                createObj();
            }
            repaint();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void paint(Graphics g){
        if(null==offScreenImage){
            offScreenImage=createImage(WIDTH,HEIGHT);
        }
        Graphics gImg=offScreenImage.getGraphics();
        gImg.fillRect(0,0,WIDTH,HEIGHT);
        switch (state) {
            case 0 -> {
                //图片
                gImg.drawImage(GameUtils.bgImg, 0, 0, null);
                //文字
                GameUtils.drawWord(gImg, "Hit Planes!", Color.WHITE, 60, 360, 335);
                GameUtils.drawWord(gImg, "点击开始", Color.YELLOW, 60, 360, 435);
            }
            case 1 -> {
                gImg.drawImage(GameUtils.bgImg, 0, 0, null);
                for (int i = 0; i < GameUtils.gameObjList.size(); i++) {
                    GameUtils.gameObjList.get(i).paintSelf(gImg);
                }
                GameUtils.gameObjList.removeAll(GameUtils.removeList);
                System.gc();
            }
            case 2 -> {
                gImg.drawImage(GameUtils.bgImg, 0, 0, null);
                GameUtils.drawWord(gImg, "暂停中，按P继续", Color.YELLOW, 60, 300, 400);
                GameUtils.drawProg(gImg,12,40,1000,10);
            }
            case 3 -> {
                //失败
                GameUtils.gameObjList.removeAll(GameUtils.gameObjList);
                GameUtils.shellObjList.removeAll(GameUtils.shellObjList);
                GameUtils.enmObjList.removeAll(GameUtils.enmObjList);
                GameUtils.buObjList.removeAll(GameUtils.buObjList);
                enmC = 0;
                repaintCount = 0;
                BObj = null;
                GameUtils.gameObjList.add(plObj);
                GameUtils.gameObjList.add(progObj);
                win = false;
                GameUtils.bgm.stop();
                gImg.drawImage(GameUtils.bgImg,0,0,null);
                gImg.drawImage(GameUtils.expImg,plObj.getX(),plObj.getY(),null);
                if(!end){
                    end=true;
                    UIManager.put("OptionPane.okButtonText","重新开始");
                    JOptionPane.showConfirmDialog(this,"游戏失败，得分："+score,"游戏失败！", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                    state=0;
                    score=0;
                    undead=false;
                    GameUtils.playBGM(5);
                    end=false;
                }
            }
            case 4 -> {
                //胜利
                GameUtils.gameObjList.removeAll(GameUtils.gameObjList);
                GameUtils.shellObjList.removeAll(GameUtils.shellObjList);
                GameUtils.enmObjList.removeAll(GameUtils.enmObjList);
                GameUtils.buObjList.removeAll(GameUtils.buObjList);
                enmC = 0;
                repaintCount = 0;
                BObj = null;
                GameUtils.gameObjList.add(plObj);
                GameUtils.gameObjList.add(progObj);
                win = true;
                GameUtils.bgm.stop();
                gImg.drawImage(GameUtils.bgImg,0,0,null);
                if(!end){
                    end=true;
                    UIManager.put("OptionPane.okButtonText","重新开始");
                    JOptionPane.showConfirmDialog(this,"游戏胜利，得分："+score,"游戏胜利！", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
                    state=0;
                    score=0;
                    undead=false;
                    GameUtils.playBGM(5);
                    end=false;
                }
            }
        }
        setTitle("Hit Planes! - " + score + "分");
        g.drawImage(offScreenImage,0,0,null);
        repaintCount++;
    }
    //批量创建子弹和敌人
    void createObj(){
        switch (difficulty){
            case 0->{
                enmLimit=5;
                bossHP=10;
                enmInterval=20-score/10;
                enmMoveSpeed=5;
                buInterval=20;
                buMoveSpeed=5;
            }
            case 1->{
                enmLimit=50;
                bossHP=500;
                enmInterval=20-score/10;
                enmMoveSpeed=5;
                buInterval=20;
                buMoveSpeed=5;
            }
            case 2->{
                enmLimit=100;
                bossHP=500;
                enmInterval=20-score/10;
                enmMoveSpeed=5;
                if(BObj!=null) buInterval=20+(int)(BObj.HP/100);
                buMoveSpeed=(double)score/100+1;
            }
            case 3->{
                enmLimit=100;
                bossHP=1000;
                enmInterval=20-score/10;
                enmMoveSpeed=(double) score /10+1;
                if(BObj!=null) buInterval=15+(int)(BObj.HP/100);
                buMoveSpeed=(double)score/100+1;
            }
            case 4->{
                Random random=new Random();
                enmLimit=120;
                bossHP=1000;
                double ems=(double) score /10+1;
                enmInterval=20-score/10;
                enmMoveSpeed=random.nextInt(6)+5;
                if(BObj!=null) buInterval=15+(int)(BObj.HP/100);
                buMoveSpeed=(double)score/100+1;
            }
            case 5->{
                Random random=new Random();
                enmLimit=100;
                bossHP=200+score/2;
                enmInterval=16+random.nextInt(5);
                enmMoveSpeed=random.nextInt(5)+6;
                if(BObj!=null) buInterval=10+(int)((BObj.HP/bossHP)*10);
                if(BObj!=null) BObj.speed=4+(double)score/250;
                buMoveSpeed=4+(double)score/250+random.nextInt(3);
            }
        }
        //敌人
        if(BObj==null&&0==repaintCount%enmInterval){
            GameUtils.enmObjList.add(new EnmObj(GameUtils.enmImg,(int)(Math.random()*20)*50,0,49,36,enmMoveSpeed,this));
            GameUtils.gameObjList.add(GameUtils.enmObjList.get(GameUtils.enmObjList.size()-1));
            enmC++;
        }else if(BObj!=null&&0==repaintCount%(11+new Random().nextInt(10))){
            GameUtils.enmObjList.add(new EnmObj(GameUtils.enmImg,(int)(Math.random()*20)*50,0,49,36,(difficulty==4?6:5),this));
            GameUtils.gameObjList.add(GameUtils.enmObjList.get(GameUtils.enmObjList.size()-1));
            enmC++;
        }
        //敌方子弹
        if(BObj!=null&&0==repaintCount%(buInterval)){
            GameUtils.buObjList.add(new BuObj(GameUtils.buImg,BObj.getX()+76,BObj.getY()+85,15,25,buMoveSpeed,this));
            GameUtils.gameObjList.add(GameUtils.buObjList.get(GameUtils.buObjList.size()-1));
        }
        //生成Boss
        if(difficulty!=5&&enmC>enmLimit&&BObj==null){
            BObj=new BossObj(GameUtils.bossImg,426,20,155,100,(double)score/100+5,(difficulty==4?2:1),this);
            GameUtils.gameObjList.add(BObj);
            GameUtils.bgm.stop();
            GameUtils.playBGM(6);
        }else if(difficulty==5&&0==(enmC+1)%100&&!GameUtils.gameObjList.contains(BObj)){
            BObj=new BossObj(GameUtils.bossImg,426,20,155,100,4+(double)score/500, 1,this);
            GameUtils.gameObjList.add(BObj);
            GameUtils.bgm.stop();
            GameUtils.playBGM(6);
        }
        //自动发射子弹
        if(GameUtils.gameObjList.contains(BObj)){
            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,plObj.getX()+3,plObj.getY()-1,14,29,5,this));
            GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
        }
        else if(!GameUtils.gameObjList.contains(BObj)&&0==repaintCount%5){
            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,plObj.getX()+3,plObj.getY()-1,14,29,5,this));
            GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
        }
        //生成道具
        if(0==repaintCount%500){
            GameUtils.effObjList.add(new EffObj(GameUtils.effImg,(int)(Math.random()*20)*50,768,20,20,10,this));
            GameUtils.gameObjList.add(GameUtils.effObjList.get(GameUtils.effObjList.size()-1));
        }
    }
    public static void main(String[] args) {
        Main gw = new Main();
        gw.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gw.setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(gw,"通过鼠标移动控制飞机位置，按P键暂停游戏，绿色方块是道具；\n飞机自动发射子弹，子弹击中敌人后获得一分，击败boss后游戏胜利。\n\n简单难度：击败50个敌人后出现boss，boss 500点血；\n中等难度：击败100个敌人后出现boss，boss 500点血；\n困难难度：击中100个敌人后出现boss，boss 1000点血。\n\n不同难度下，敌人和boss的移动方式、boss发射子弹的方式会有所不同。\n","Hit Planes! 游戏提示",JOptionPane.INFORMATION_MESSAGE);
        gw.launch();
    }
}