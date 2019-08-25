package com.Neuedu.Entity;

import java.awt.Graphics;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Util.GetImageUtil;

/**

* @ClassName: BackGround

* @Description: ����

* @author QiChen

* @date 2019��8��20�� ����1:43:01

*/
public class BackGround extends GameObj implements ActionAble {

	private Integer speed;

	public BackGround() {
		
	}
	public BackGround(int x,int y,String imgName) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 1;
	}

	@Override
	public void move() {
        y +=speed;
	}

	@Override
	public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
	}

}
