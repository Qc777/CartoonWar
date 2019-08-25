package com.Neuedu.Client;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.Neuedu.Constant.Constants;
import com.Neuedu.Entity.BackGround;
import com.Neuedu.Entity.Boom;
import com.Neuedu.Entity.Boss;
import com.Neuedu.Entity.Bullet;
import com.Neuedu.Entity.EnemyPlane;
import com.Neuedu.Entity.OurPlane;
import com.Neuedu.Entity.Prop;
import com.Neuedu.Util.GetImageUtil;

/**	

* @ClassName: GameClient

* @Description: 游戏客户端

* @author QiChen

* @date 2019年8月17日 下午3:54:03

*


*/
public class GameClient extends Frame {
    
	//创建我方飞机
	//OurPlane ourplane = new OurPlane(330, 850, "com/Neuedu/Img/plane/OurPlanes.png",this,true);
	//创建我方自身集合
	public List<OurPlane> ourplanes = new ArrayList<OurPlane>();
	//创建我方子弹集合
	public List<Bullet> bullets = new ArrayList<Bullet>();
	//创建道具集合
	public List<Prop> props = new ArrayList<Prop>();
	//创建背景
	BackGround backImg = new BackGround(0,-2200,"com/Neuedu/Img/backgroud.png");
	//创建敌方飞机集合
	public List<OurPlane> enemys = new ArrayList();
	//创建一个爆炸集合
	public List<Boom> booms = new ArrayList<Boom>();
	//创建一个Boss集合
	public List<OurPlane> bosss = new ArrayList<OurPlane>();
	
	//解决图片闪烁问题
	@Override
	public void update(Graphics g) {
		Image backImg = createImage(Constants.GAME_WIDTH,Constants.GAME_HEIGHT);
		Graphics backg = backImg.getGraphics();
		Color color = backg.getColor();
		backg.setColor(Color.WHITE);
		backg.fillRect(0,0,Constants.GAME_WIDTH,Constants.GAME_HEIGHT);
		backg.setColor(color);
		paint(backg);
		g.drawImage(backImg,0,0,null);
	}
	OurPlane ourplane = null;
	//生成客户端窗口
	public void launchFrame() {
		this.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		//窗口名字
		this.setTitle("飞机大战");
		//是否显示窗口
		this.setVisible(true);
		//禁止最大化
		this.setResizable(false);
		//窗口居中
		this.setLocationRelativeTo(null);
		//插入窗口图标
		Image icon = GetImageUtil.getImg("com/Neuedu/Img/icon.png");
		this.setIconImage(icon);
		//实现关闭窗口 添加关闭监听器
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			    System.exit(0);
			}
		});
		ourplane = new OurPlane(330, 850, "com/Neuedu/Img/plane/OurPlanes.png",this,true);
		//给我方集合中添加自己
		ourplanes.add(ourplane);
		//添加鼠标监听
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				System.out.println("你点了一下鼠标");
			}	
		});
		//添加键盘监听
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				ourplane.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				ourplane.keyReleased(e);
				
			}
		});	
		//启动线程
		new paintThread().start();

		
		//创建一个敌方飞机
		EnemyPlane enemy1 = new EnemyPlane(360,-150,1,this,false);
		EnemyPlane enemy2 = new EnemyPlane(570,-150,2,this,false);
		EnemyPlane enemy3 = new EnemyPlane(780,-150,3,this,false);
		EnemyPlane enemy4 = new EnemyPlane(990,-150,4,this,false);
		//给敌方飞机集合中添加敌人
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		//添加Boss
		bosss.add(new Boss(700,-150,this,false));
	}
	    
	//重写paint方法
	@Override
	public void paint(Graphics g) {
		backImg.draw(g);
		//g.drawLine(0, 150, 850, 150);
		for(int i=0;i<ourplanes.size();i++) {
			OurPlane ourplane2 = ourplanes.get(i);
			ourplane2.draw(g);
			ourplane2.containItems(props);
			
		}
		//循环画出每个子弹
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(ourplanes);
			bullet.hitMonsters(bosss);
		}
		//数据显示
		g.drawString("发射子弹总数："+bullets.size(), 15, 45);
		g.drawString("当前敌方飞机："+enemys.size(), 15, 70);
		g.drawString("当前爆炸数量："+booms.size(), 15, 95);
		g.drawString("当前我方数量："+ourplanes.size(), 15, 120);
		g.drawString("当前道具数量："+props.size(), 15, 145);
		//循环画出敌方飞机1-4
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		//循环画爆炸
		for(int i=0;i<booms.size();i++) {
			if(booms.get(i).isLive()==true) {
			booms.get(i).draw(g);
		    }
		}
		//循环画道具
		for(int i=0;i<props.size();i++) {
				props.get(i).draw(g);
		}
		if(enemys.size()==0) {
			//循环Boss集合
			for(int i=0;i<bosss.size();i++) {
				bosss.get(i).draw(g);
		    }    

		}
    }
	
	//内部类
	class paintThread extends Thread{
		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
