package com.Neuedu.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.Neuedu.Action.ActionAble;
import com.Neuedu.Client.GameClient;
import com.Neuedu.Constant.Constants;
import com.Neuedu.Util.GetImageUtil;

/**

* @ClassName: FristPlane

* @Description: ������ɫ�ɻ�

* @author QiChen

* @date 2019��8��20�� ����10:26:33

*


*/
public class OurPlane extends GameObj implements ActionAble{
	//�ٶ�
	private int speed;	
	//����boolean����
	private boolean left,up,right,down;
	//�ÿͻ���
	public GameClient gc;
	//�ж��ҷ����ǵз�
	public boolean isGood;
	//��ӷɻ��ӵ��ȼ�����
	public int BulletLevel = 1;
	//���Ѫ��	ֵ
	public int blood;
	public OurPlane() {
		
	}
	public OurPlane(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 12;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100;
	}
	//�ƶ��ķ���
	@Override
	public void move() {
		if(left) {
			x -=speed;
		}if(right){
			x +=speed;
		}if(up) {
			y -=speed;
		}if(down) {
			y +=speed;
		}
		outOfBound();
	}
	//��һ���ҷ��ɻ�
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("��ǰѪ��ֵ��"+blood, 10, 170);
	}
	public void outOfBound() {
		if(y<=30) {
			y = 30;
		}if(x<=5) {
			x = 5;
		}if(x>Constants.GAME_WIDTH-img.getWidth(null)) {
			x = Constants.GAME_WIDTH-img.getWidth(null);
		}if(y>Constants.GAME_HEIGHT-img.getHeight(null)) {
			y = Constants.GAME_HEIGHT-img.getHeight(null);
		}
	}
	//���̰���
	public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
        	left = true;
        	break;
        case KeyEvent.VK_D:
        	right = true;
        	break;
        case KeyEvent.VK_W:
        	up = true;
        	break;
        case KeyEvent.VK_S:
        	down = true;
        	break;
        default:
        	break;
        }
	}
	//�����ͷ�
	public void keyReleased(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case KeyEvent.VK_A:
			 left = false;
			 break;
		 case KeyEvent.VK_D:
			 right = false;
			 break;
		 case KeyEvent.VK_W:
			 up = false;
			 break;
		 case KeyEvent.VK_S:
			 down = false;
			 break;
	        case KeyEvent.VK_J:
	        	fire();
	        default:
	     	break;
		 }
	}
	//�ҷ��ɻ�����
	public void fire() {
		//�����ҷ��ӵ�
		Bullet b = new Bullet(x+this.img.getWidth(null)/2-63,y+this.img.getHeight(null)/2-120,
				"com/Neuedu/Img/Bullet/OurBullet"+BulletLevel+".png",gc,true);
		gc.bullets.add(b);
	}
	//��ȡ��ǰ����״
		public Rectangle getRec() {
			return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
		}
	//����ҷ��ɻ���������	
	public void containItem(Prop prop) {
		if(this.getRec().intersects(prop.getRect())) {
			//�Ƴ�����
			gc.props.remove(prop);
			if(BulletLevel>2) {
				BulletLevel = 3;
			}
			//�����ӵ��ȼ�
			this.BulletLevel +=1;
			
		}
	}
	//����ҷ��ɻ���ײ���������
	public void containItems(List<Prop> props) {
		if(props==null) {
			return;
		}else {
			for(int i=0;i<props.size();i++) {
				containItem(props.get(i));
			}
		}
	}
}
