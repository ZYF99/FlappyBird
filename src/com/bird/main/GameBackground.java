package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 游戏背景类，绘制游戏背景的内容都在此类
 * 
 * @author Kingyu
 *
 */
public class GameBackground {

	private static BufferedImage BackgroundImg;// 背景图片

	private int speed; // 背景层的速度
	private int layerX; // 背景层的坐标

	// 在构造器中对资源初始化
	public GameBackground() {
		this.speed = 1;
		this.layerX = 0;
	}
	
	static { //读取背景图片
		BackgroundImg = GameUtil.loadBUfferedImage(Constant.BG_IMG_PATH);
	}

	public static final int BG_IMAGE_HEIGHT = BackgroundImg.getHeight();

	// 定义绘制方法,用系统提供的画笔将图片绘制到指定位置
	public void draw(Graphics g, Bird bird) {
		// 绘制背景色
		g.setColor(Constant.BG_COLOR);
		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		// 获得背景图片的尺寸
		int imgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		int count = Constant.FRAME_WIDTH / imgWidth + 2; // 绘制次数
		for (int i = 0; i < count; i++) {
			g.drawImage(BackgroundImg, imgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
		}
		
		if(bird.isDead()) {
			return;
		}
		moveLogic();
	}

	// 背景层的运动逻辑
	private void moveLogic() {
		layerX += speed;
		if (layerX > BackgroundImg.getWidth())
			layerX = 0;
	}
}
