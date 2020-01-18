package ini_DRAWPLATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @note
 * 记住要重新设置burreredimage的大小来适应不同的画面*/
public class Pythagoras {
	double orderA = Math.PI/3;
	int finalX,finalY;
	public Color curColor;
	public int WIDTH = 1000, HEIGHT = 7400;
	public BufferedImage buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
	long Cnt = 0;
	//用BufferedImage存图方便重绘
	public void draw(Graphics g, double x1,double y1,double x2,double y2,int depth){
		if(depth>0){
			Cnt++;
			Graphics tempG = buffer.getGraphics(); //得到buffer上的画布
			tempG.setColor(curColor); //要在buffer上的画布设置颜色
			if(depth%3==0) orderA = Math.PI/3;
			else if(depth%3==1) orderA = Math.PI/4;
			else if(depth%3==2) orderA = Math.PI/3;
			double len = getLen(x1,y1,x2,y2);
			double angle = getAngle(x1,y1,x2,y2); //角度
			double x3 = x2 - len*Math.sin(angle);	double y3 = y2 - len*Math.cos(angle);
			double x4 = x1 - len*Math.sin(angle);  	double y4 = y1 - len*Math.cos(angle);
			int[] x = {(int)x1,(int)x2,(int)x3,(int)x4}; //(x1,y1) (x2,y2) (x3,y3) (x4,y4)
			int[] y = {(int)y1,(int)y2,(int)y3,(int)y4};
			//绘制~
			tempG.fillPolygon(x, y, 4);
			//g.fillPolygon(x, y, 4);
			if(Cnt%80000==0)
				g.drawImage(buffer, 0, 0, null);//在JPanel的(0,0)处展开图
			if(depth%6==0) len = len*2/3;
			double x5 = x4 + Math.cos(angle+orderA)*Math.cos(orderA)*len;
			double y5 = y4 - Math.sin(angle+orderA)*Math.cos(orderA)*len;
			draw(g, x4,y4, x5,y5, depth-1);
			draw(g, x5,y5, x3,y3, depth-1);
		}
		else {
			g.drawImage(buffer, 0, 0, null);
			return;
		}
	}
	/**
	 * @note:每个三角形的角度固定不变版本
	 * @param orderA 其中一个非直角的角度
	 * @param depth 深度8-9推荐
	 * @param x1,y1 x2,y2 根部的正方形的底部的两端
	 */
	public void draw_fixA(Graphics g, double x1,double y1,double x2,double y2,int depth, double orderA){
		if(depth>0){
			Cnt++;
			Graphics tempG = buffer.getGraphics(); //得到buffer上的画布
			tempG.setColor(curColor); //要在buffer上的画布设置颜色
			double len = getLen(x1,y1,x2,y2);
			double angle = getAngle(x1,y1,x2,y2); //角度
			double x3 = x2 - len*Math.sin(angle);	double y3 = y2 - len*Math.cos(angle);
			double x4 = x1 - len*Math.sin(angle);  	double y4 = y1 - len*Math.cos(angle);
			int[] x = {(int)x1,(int)x2,(int)x3,(int)x4}; //(x1,y1) (x2,y2) (x3,y3) (x4,y4)
			int[] y = {(int)y1,(int)y2,(int)y3,(int)y4};
			//绘制~
			tempG.fillPolygon(x, y, 4);
			//g.fillPolygon(x, y, 4);
			if(Cnt%800000==0)
				g.drawImage(buffer, 0, 0,null);//在JPanel的(0,0)处展开图
			if(depth%6==0) len = len*2/3;
			double x5 = x4 + Math.cos(angle+orderA)*Math.cos(orderA)*len;
			double y5 = y4 - Math.sin(angle+orderA)*Math.cos(orderA)*len;
			draw(g, x4,y4, x5,y5, depth-1);
			draw(g, x5,y5, x3,y3, depth-1);
		}
		else {
			g.drawImage(buffer, 0, 0, null);
			return;
		}
	}
	//传回画布用于存储
	public Image returnImage(){
		return buffer;
	}
	private double getLen(double x1,double y1,double x2,double y2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	private double getAngle(double x1,double y1,double x2,double y2){
//		return Math.atan((y2-y1)/(x2-x1)); //零不能作分母，要分情况
		//atan的返回值为 -pi/2-->pi/2
		double angle = 0.0;
		if(x1 == x2){//两点在同一竖直线上
			if(y1 > y2){
				angle = Math.PI/2;
			}else if(y2 > y1){
				angle = Math.PI*3/2;
			}
		}
		else{//两点不在同一直线
			double tempAngle = Math.atan((y2-y1)/(x2-x1));
			if(x2 > x1){
				angle = -tempAngle;
			}else if(x2 < x1){ //atan的返回值在 -pi/2到pi/2之间
				angle = -tempAngle + Math.PI;
			}
		}
		return angle;
	}
}
