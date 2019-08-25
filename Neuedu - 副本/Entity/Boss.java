package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Client.GameClient;
import com.Neuedu.Constant.Constants;
import com.Neuedu.Util.GetImageUtil;

public class Boss extends OurPlane implements ActionAble {

	private boolean left = true;
	//创建血条
	//private 
	//动态初始化一个Boos图片数组
	private	static Image[] imgs = new Image[2];
		static {
			for(int i=0;i<imgs.length;i++) {
				imgs[i] = GetImageUtil.getImg("com/Neuedu/Img/Boss/Boss"+(i+1)+".png");
			}
		}
		private int speed = 2;
		
	public Boss() {
	}
	public Boss(int x,int y,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 50000;
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
        if(count>1) {
        	count = 0;
        }
		g.drawImage(imgs[count++], x, y, null);
		move();
		g.drawString("Boss当前血量	"+blood, x, y);
	}
	@Override
	public void move() {

		y += speed;
		if(y > 150) {
			
			if(left) {
				x -= speed;
			}if(!left) {
				x += speed;
			}
			y = 150;
			if(x>Constants.GAME_WIDTH-imgs[0].getWidth(null)-300) {
				left = true;
			}if(x<300) {
				left = false;
			}
		}
		if(random.nextInt(500)>460) {
			fire();
		}
	}
	//生成随机数
	Random random = new Random();
	//获取Boss的形状
	public Rectangle getRec() {
		return new Rectangle(x,y,imgs[0].getWidth(null),imgs[0].getHeight(null));
	}
	@Override
	public void fire() {
		Bullet b = new Bullet(x+this.imgs[0].getWidth(null)/2-63,y+this.imgs[0].getHeight(null)/2,
				"com/Neuedu/Img/Bullet/OurBullet"+BulletLevel+".png",gc,false);
		gc.bullets.add(b);
	}
}
