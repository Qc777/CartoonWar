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

* @Description: ��Ϸ�ͻ���

* @author QiChen

* @date 2019��8��17�� ����3:54:03

*


*/
public class GameClient extends Frame {
    
	//�����ҷ��ɻ�
	//OurPlane ourplane = new OurPlane(330, 850, "com/Neuedu/Img/plane/OurPlanes.png",this,true);
	//�����ҷ�������
	public List<OurPlane> ourplanes = new ArrayList<OurPlane>();
	//�����ҷ��ӵ�����
	public List<Bullet> bullets = new ArrayList<Bullet>();
	//�������߼���
	public List<Prop> props = new ArrayList<Prop>();
	//��������
	BackGround backImg = new BackGround(0,-2200,"com/Neuedu/Img/backgroud.png");
	//�����з��ɻ�����
	public List<OurPlane> enemys = new ArrayList();
	//����һ����ը����
	public List<Boom> booms = new ArrayList<Boom>();
	//����һ��Boss����
	public List<OurPlane> bosss = new ArrayList<OurPlane>();
	
	//���ͼƬ��˸����
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
	//���ɿͻ��˴���
	public void launchFrame() {
		this.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		//��������
		this.setTitle("�ɻ���ս");
		//�Ƿ���ʾ����
		this.setVisible(true);
		//��ֹ���
		this.setResizable(false);
		//���ھ���
		this.setLocationRelativeTo(null);
		//���봰��ͼ��
		Image icon = GetImageUtil.getImg("com/Neuedu/Img/icon.png");
		this.setIconImage(icon);
		//ʵ�ֹرմ��� ��ӹرռ�����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			    System.exit(0);
			}
		});
		ourplane = new OurPlane(330, 850, "com/Neuedu/Img/plane/OurPlanes.png",this,true);
		//���ҷ�����������Լ�
		ourplanes.add(ourplane);
		//���������
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				System.out.println("�����һ�����");
			}	
		});
		//��Ӽ��̼���
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
		//�����߳�
		new paintThread().start();

		
		//����һ���з��ɻ�
		EnemyPlane enemy1 = new EnemyPlane(360,-150,1,this,false);
		EnemyPlane enemy2 = new EnemyPlane(570,-150,2,this,false);
		EnemyPlane enemy3 = new EnemyPlane(780,-150,3,this,false);
		EnemyPlane enemy4 = new EnemyPlane(990,-150,4,this,false);
		//���з��ɻ���������ӵ���
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		//���Boss
		bosss.add(new Boss(700,-150,this,false));
	}
	    
	//��дpaint����
	@Override
	public void paint(Graphics g) {
		backImg.draw(g);
		//g.drawLine(0, 150, 850, 150);
		for(int i=0;i<ourplanes.size();i++) {
			OurPlane ourplane2 = ourplanes.get(i);
			ourplane2.draw(g);
			ourplane2.containItems(props);
			
		}
		//ѭ������ÿ���ӵ�
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(ourplanes);
			bullet.hitMonsters(bosss);
		}
		//������ʾ
		g.drawString("�����ӵ�������"+bullets.size(), 15, 45);
		g.drawString("��ǰ�з��ɻ���"+enemys.size(), 15, 70);
		g.drawString("��ǰ��ը������"+booms.size(), 15, 95);
		g.drawString("��ǰ�ҷ�������"+ourplanes.size(), 15, 120);
		g.drawString("��ǰ����������"+props.size(), 15, 145);
		//ѭ�������з��ɻ�1-4
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		//ѭ������ը
		for(int i=0;i<booms.size();i++) {
			if(booms.get(i).isLive()==true) {
			booms.get(i).draw(g);
		    }
		}
		//ѭ��������
		for(int i=0;i<props.size();i++) {
				props.get(i).draw(g);
		}
		if(enemys.size()==0) {
			//ѭ��Boss����
			for(int i=0;i<bosss.size();i++) {
				bosss.get(i).draw(g);
		    }    

		}
    }
	
	//�ڲ���
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
