package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import javax.swing.Action;
import com.Neuedu.Action.ActionAble;
import com.Neuedu.Client.GameClient;
import com.Neuedu.Constant.Constants;
import com.Neuedu.Util.GetImageUtil;

/**

* @ClassName: Bullet

* @Description: 子弹类

* @author QiChen

* @date 2019年8月20日 下午5:30:02

*/
public class Bullet extends GameObj implements ActionAble{
	//创建速度属性
	private Integer speed;
	//拿客户端
	public GameClient gc;
	//子弹类型
	public boolean isGood;
	
	public Bullet() {
		
	}
    public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
    	this.x = x;
    	this.y = y;
    	this.img = GetImageUtil.getImg(imgName);
    	this.speed = 10;
    	this.gc = gc;
    	this.isGood = isGood;
    }
	@Override
	public void move() {
		if(isGood) {	
			y -= speed;
		}else {
			y += speed;
		}
        
        outOfBounds();
	}
	@Override
	public void draw(Graphics g) {
        g.drawImage(img, x, y, null);	
        move();
	}
	//子弹越界销毁
	public void outOfBounds() {
		if(y<0||y>Constants.GAME_HEIGHT) {
			gc.bullets.remove(this);
		}
	}
	//随机生成道具
	Random random = new Random();
	//获取子弹形状
	public Rectangle getRec() {
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	//我方子弹碰撞一个敌方飞机消失
	public boolean hitMonster(OurPlane ourplane) {
		Boom boom = new Boom(ourplane.x, ourplane.y, gc);
		if(this.getRec().intersects(ourplane.getRec())&&this.isGood!=ourplane.isGood) {

			if(ourplane.isGood) {
				ourplane.blood -=10;
				if(ourplane.blood == 0) {
					//销毁自身
					gc.ourplanes.remove(ourplane);
				}
				//打中我方后移除子弹
				gc.bullets.remove(this);
			}else {
				//Boss
				if(ourplane instanceof Boss) {
					ourplane.blood -= 100;
					if(ourplane.blood <= 0) {
						//移除Boss
						gc.bosss.remove(ourplane);
						//移除子弹
						gc.bullets.remove(this);
					}
					
				}else {
					//移除打中的敌人
					gc.enemys.remove(ourplane);
					//打中敌人后移除子弹
					gc.bullets.remove(this);
					if(random.nextInt(500)>200) {
					//生成一个道具
					Prop prop = new Prop(ourplane.x,ourplane.y,"com/Neuedu/Img/Prop/prop.png");
					gc.props.add(prop);
				}
				}

		}
			//添加爆炸效果	
			gc.booms.add(boom);
			return true;
	}


			return false;
	}
	//我方子弹碰撞多个敌方飞机消失
	public boolean hitMonsters(List<OurPlane> Planes){
		if(Planes==null) {
			return false;
		}
		for(int i=0;i<Planes.size();i++) {
			if(hitMonster(Planes.get(i))) {
				return true;
			}
		}
		return false;
	}
	
}
