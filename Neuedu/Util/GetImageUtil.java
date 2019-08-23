package com.Neuedu.Util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**

* @ClassName: GetImgUtil

* @Description: ͼƬ������

* @author QiChen

* @date 2019��8��17�� ����4:48:44

*


*/
public class GetImageUtil {

	//��ȡͼƬ�ķ���
	public static Image getImg(String imgName) {
	    URL resource = GetImageUtil.class.getClassLoader().getResource(imgName);
	    BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}
}