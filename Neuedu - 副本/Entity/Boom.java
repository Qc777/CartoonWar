package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Image;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Client.GameClient;
import com.Neuedu.Util.GetImageUtil;

/**

* @ClassName: Boom

* @Description: 爆炸类

* @author QiChen

* @date 2019年8月20日 下午9:20:54

*/
public class Boom extends GameObj implements ActionAble{
	
	private boolean isLive;
	private GameClient gc;
	
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public boolean isLive() {
		return isLive;
	}
	//动态初始化爆炸图
	private static Image[] boomImgs = new Image[6];
	static {
		for(int i=0;i<6;i++) {
			boomImgs[i] = GetImageUtil.getImg("com/Neuedu/Img/Boom/"+(i+1)+".png");
		}
	}
	public Boom() {
		// TODO Auto-generated constructor stub
	}
	public Boom(int x,int y,GameClient gc) {	
		this.x = x;
		this.y = y;
		this.isLive = true;
		this.gc = gc;
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
	}
    int count = 0;
	@Override
	public void draw(Graphics g) {
		if(isLive) {
			 if(count > 5) {
				    
		        	gc.booms.remove(this);
		        	this.setLive(false);
		        	return;
		        }
				g.drawImage(boomImgs[count++], x, y, null);
		}
       
	}
	

}
