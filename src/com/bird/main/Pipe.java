package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 管道类
 * 
 * @author Kingyu
 *
 */
public class Pipe {
	private static BufferedImage[] imgs; // 管道的图片，static保证图片只加载一次
	static {// 静态块，类加载的时候，初始化图片
		final int PIPE_IMAGE_COUNT = 3;
		imgs = new BufferedImage[PIPE_IMAGE_COUNT];
		for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
			imgs[i] = GameUtil.loadBUfferedImage(Constant.PIPE_IMG_PATH[i]);
		}
	}

	// 所有碰撞元素的宽高
	public static final int PIPE_WIDTH = imgs[0].getWidth();
	public static final int PIPE_HEIGHT = imgs[0].getHeight();
	public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
	public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

	private int x, y; // 管道的坐标，相对于元素层
	private int height; // 管道的宽，高

	private boolean visible; // 管道可见状态，true为可见，false表示可归还至对象池
	private int type; // 管道的类型
	public static final int TYPE_TOP_NORMAL = 0;
	public static final int TYPE_TOP_HARD = 1;
	public static final int TYPE_BOTTOM_NORMAL = 2;
	public static final int TYPE_BOTTOM_HARD = 3;
	public static final int TYPE_HOVER_NORMAL = 4;
	public static final int TYPE_HOVER_HARD = 5;

	// 管道的速度
	public static final int MIN_SPEED = 1;
	public static final int MAX_SPEED = 2;
	private int speed;

	public Pipe() {
		this.speed = MIN_SPEED;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void draw(Graphics g) {
		switch (type) {
		case TYPE_TOP_NORMAL:
			drawTopNormal(g);
			break;
		case TYPE_BOTTOM_NORMAL:
			drawBottomNormal(g);
			break;
		case TYPE_HOVER_NORMAL:
			drawHoverNormal(g);
			break;
		}

		pipeLogic();
	}

	// 绘制从上往下的普通管道
	private void drawTopNormal(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
		}
		// 绘制管道的顶部
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), height - PIPE_HEAD_HEIGHT, null); // 管道头部与管道主体的宽度不同，x坐标需要处理
	}

	// 绘制从下往上的普通管道
	private void drawBottomNormal(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT, null);
		}
		// 绘制管道的顶部
		g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), Constant.FRAME_HEIGHT - height, null);
	}

	// 绘制悬浮的普通管道
	private void drawHoverNormal(Graphics g) {
		// 拼接的个数
		int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制管道的上顶部
		g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), y, null);
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
		}
		// 绘制管道的下底部
		int y = this.y + height - PIPE_HEAD_HEIGHT;
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), y, null);
	}

	/**
	 * 管道的逻辑
	 */
	private void pipeLogic() {
		x -= speed;
		if (x < -1 * PIPE_HEAD_WIDTH) {// 管道完全离开了窗口
			visible = false;
		}
	}

	/**
	 * 判断当前管道是否完全出现在窗口中
	 * 
	 * @return 若完全出现则返回true，否则返回false
	 */
	public boolean isInFrame() {
		return x + PIPE_WIDTH < Constant.FRAME_WIDTH;
	}

	public int getX() {
		return x;
	}
}
