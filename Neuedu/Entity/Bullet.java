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

* @Description: �ӵ���

* @author QiChen

* @date 2019��8��20�� ����5:30:02

*/
public class Bullet extends GameObj implements ActionAble{
	//�����ٶ�����
	private Integer speed;
	//�ÿͻ���
	public GameClient gc;
	//�ӵ�����
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
	//�ӵ�Խ������
	public void outOfBounds() {
		if(y<0||y>Constants.GAME_HEIGHT) {
			gc.bullets.remove(this);
		}
	}
	//������ɵ���
	Random random = new Random();
	//��ȡ�ӵ���״
	public Rectangle getRec() {
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	//�ҷ��ӵ���ײһ���з��ɻ���ʧ
	public boolean hitMonster(OurPlane ourplane) {
		Boom boom = new Boom(ourplane.x, ourplane.y, gc);
		if(this.getRec().intersects(ourplane.getRec())&&this.isGood!=ourplane.isGood) {

			if(ourplane.isGood) {
				ourplane.blood -=10;
				if(ourplane.blood == 0) {
					//��������
					gc.ourplanes.remove(ourplane);
				}
				//�����ҷ����Ƴ��ӵ�
				gc.bullets.remove(this);
			}else {
				//Boss
				if(ourplane instanceof Boss) {
					ourplane.blood -= 100;
					if(ourplane.blood <= 0) {
						//�Ƴ�Boss
						gc.bosss.remove(ourplane);
						//�Ƴ��ӵ�
						gc.bullets.remove(this);
					}
					
				}else {
					//�Ƴ����еĵ���
					gc.enemys.remove(ourplane);
					//���е��˺��Ƴ��ӵ�
					gc.bullets.remove(this);
					if(random.nextInt(500)>200) {
					//����һ������
					Prop prop = new Prop(ourplane.x,ourplane.y,"com/Neuedu/Img/Prop/prop.png");
					gc.props.add(prop);
				}
				}

		}
			//��ӱ�ըЧ��	
			gc.booms.add(boom);
			return true;
	}


			return false;
	}
	//�ҷ��ӵ���ײ����з��ɻ���ʧ
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
