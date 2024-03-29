package ini_DRAWPLATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

//封装一个绘制复杂图形的类
public class MagicPen {
	Color color;
	int curX, curY;
	BufferedImage buffer = new BufferedImage(1000,740,BufferedImage.TYPE_INT_ARGB); //Buffered 是Image的一个子类
	double a=-1.7, b=1.3, c=-0.1, d=-1.2;
	
	public void magicPen1(Graphics g) 
	{
		Graphics tempG = buffer.getGraphics(); //先将画布画在buffer上
		
		double x1=0 , y1=0, x2, y2;
		int sx,sy;
		for(;d<-0.5;d+=0.01,a+=0.01){
			tempG.setColor(Color.BLACK);
			tempG.fillRect(curX-200,curY-200, 500, 500);
			for(int i=0; i<220500;++i){
				x2 = Math.sin(a*y1) + c*Math.cos(a*x1);
				y2 = Math.sin(b*x1) + d*Math.cos(b*y1);
				sx = (int)(x1*50+curX); 
				sy = (int)(y1*50+curY);
				tempG.setColor(color);
				tempG.drawLine(sx, sy, sx, sy);
				x1 = x2;
				y1 = y2;
			}
			//将存在buffer中的画布显示
			//buffer的最左上是背景的坐标，要往上挪
			g.drawImage(buffer, 0, 0, null);
		}
	}
	public Image returnImage(){
		return buffer;
	}
}
