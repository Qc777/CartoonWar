package com.Neuedu.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.stream.events.Comment;

/**

* @ClassName: Constant

* @Description: ������

* @author QiChen

* @date 2019��8��17�� ����3:59:21

*


*/
public class Constants {
	
	//��ȡ�����ļ�
	public static Properties prop = new Properties();
	static Integer Game_Width = null;
	static Integer Game_Height = null;

	static {
		InputStream inStream = Comment.class.getResourceAsStream("/gameConfiguration.properties");
		try {
			prop.load(inStream);
			Game_Width = Integer.parseInt(prop.getProperty("Game_Width"));
			Game_Height = Integer.parseInt(prop.getProperty("Game_Height"));	

		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	//������
	public static final int GAME_WIDTH = Game_Width;
	public static final int GAME_HEIGHT = Game_Height;
	
	public static void main(String[] args) {
		System.out.println(Constants.GAME_HEIGHT);
	}
}
