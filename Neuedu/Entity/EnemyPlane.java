package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Client.GameClient;
import com.Neuedu.Util.GetImageUtil;

public class EnemyPlane extends OurPlane implements ActionAble{

	private Integer enemyType;
	private Integer speed;
	private GameClient gc;
	
	private static Image[] imgs1 = {
		GetImageUtil.getImg("com/Neuedu/Img/Enemy/enemy01.png"),
		GetImageUtil.getImg("com/Neuedu/Img/Enemy/enemy02.png")
	};
	
	public EnemyPlane() {
	}
	public EnemyPlane(int x,int y,int enemyType,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.enemyType = enemyType;
		this.speed = 2;
		this.gc = gc;
		this.isGood =isGood;
	}
	@Override
	public void move() {
        y += speed;
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count>1){
			count = 0; 
		}
	    g.drawImage(imgs1[count++],x,y,null);
	    move();
	    
	    if(random.nextInt(500)>490) {
	    fire();
	    }
	}
	//随机数
	Random random = new Random();
	//敌方飞机发射子弹
	public void fire() {
		Bullet bullet = new Bullet(x+imgs1[0].getHeight(null)/2-20, y+100, "com/Neuedu/Img/Bullet/EnemyBullet.png", gc,false);
		gc.bullets.add(bullet);
	}
	//获得到子弹的形状
	public Rectangle getRec() {
		return new Rectangle(x,y,this.imgs1[0].getWidth(null),this.imgs1[0].getHeight(null));
	}
}
