package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Action;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Constant.Constants;
import com.Neuedu.Util.GetImageUtil;

/**
 * 
* @ClassName: Prop

* @Description: 道具类
* @author QiChen

* @date 2019年8月21日 下午1:15:33

*/
public class Prop extends GameObj implements ActionAble {

	public Prop() {
		// TODO Auto-generated constructor stub
	}
	private int speed;
	public Prop(int x,int y,String imgName){
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 5;
	}
	private double theta = Math.PI/4;
	public void move() {
		x += speed*Math.cos(theta);
		y += speed*Math.sin(theta);
		if(x<0 || x>Constants.GAME_WIDTH-img.getWidth(null)) {
			theta = Math.PI-theta;
		}if(y<50 || y>Constants.GAME_HEIGHT-img.getHeight(null)) {
			theta = -theta;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
	}
	//拿到当前道具的形状
	public Rectangle getRect() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
		
	}

}
