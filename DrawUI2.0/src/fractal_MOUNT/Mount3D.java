package fractal_MOUNT;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//
public class Mount3D {
	public Color color;
	Random ran = new Random();
	public int animatingTime = 0;
	public double roughness = 0.6, len = 0, tmpX,tmpY;
	//every 2 room store a line,each room's last space store new_X\new_Y respectively
	public double[][] store = new double[80000][3];//store the middle point
	public int cnt = 0;
	
	/**@note
	 * @param x1,y1 x2,y2 x3,y3  the main layout of the mountain, which is a triangle.
	 * @param depth is the recursion times. 7 is recommended.
	 */
	public void draw(Graphics g,double x1,double y1,double x2,double y2,double x3,double y3,int depth){
		g.setColor(color);
		if(depth==1){
			g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 	
			g.drawLine((int)x1, (int)y1, (int)x3, (int)y3);
			g.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
			return;
		}
		double x4,y4,x5,y5,x6,y6;
		len = get_Len(x1,y1,x2,y2,x3,y3);
//		System.out.println("len=" + len);
		//judge if the line have generated midpoint
		if(have_Generated(x1,y1,x2,y2)){
			//used the existed value
			x4 = tmpX;	y4 = tmpY;
		}else{//haven't generated
			x4 = get_X(x1,x2); y4 = get_Y(y1,y2,len);
			store[cnt][0] = x1;	store[cnt][1] = y1;	
			store[cnt][2] = x4; // store the new pointX
			store[++cnt][0] = x2;	store[cnt][1] = y2;
			store[cnt][2] = y4;	//store the new pointY
			cnt += 1;
		}
		if(have_Generated(x1,y1,x3,y3)){
			x5 = tmpX;	y5 = tmpY;
		}else{
			x5 = get_X(x1,x3);	y5 = get_Y(y1,y3,len);
			store[cnt][0] = x1;	store[cnt][1] = y1;
			store[cnt][2] = x5;
			store[++cnt][0] = x3;	store[cnt][1] = y3;
			store[cnt][2] = y5;
			cnt += 1;
		}
		if(have_Generated(x2,y2,x3,y3)){
			x6 = tmpX;	y6 = tmpY;
		}else{
			x6 = get_X(x2,x3);	y6 = get_Y(y2,y3,len);
			store[cnt][0] = x2;	store[cnt][1] = y2;	
			store[cnt][2] = x6;
			store[++cnt][0] = x3;	store[cnt][1] = y3;
			store[cnt][2] = y6;
			cnt += 1;
		}
		Sleep();
		draw(g,x4,y4,x5,y5,x1,y1,depth-1); 	draw(g,x4,y4,x5,y5,x6,y6,depth-1);
		draw(g,x5,y5,x6,y6,x3,y3,depth-1);	draw(g,x4,y4,x6,y6,x2,y2,depth-1);
	}
	//get the average len of the edges
	public double get_Len(double x1,double y1,double x2,double y2,double x3,double y3){
		double len1 = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		double len2 = Math.sqrt((x2-x3)*(x2-x3)+(y2-y3)*(y2-y3));
		double len3 = Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
//		System.out.println("len1="+len1+" len2="+len2+" len3="+len3);
		return (len1+len2+len3)/3;
	}
	public double get_X(double x1,double x2){
		return (x1+x2)/2;
	}
	public double get_Y(double y1,double y2,double len){
		double ny = (y1+y2)/2 + get_Vary(len);
//		System.out.println("len="+len);
		return ny;
	}
	public double get_Vary(double len){
		len = Math.pow(len, roughness);
		if(ran.nextInt(100)%2==0) len *= -1.0;
		return len;
	}
	//judge if the edge have generated midpoint
	public boolean have_Generated(double x1,double y1,double x2,double y2){
		for(int i=0;i<cnt;i+=2){
			if(((store[i][0]==x1&&store[i][1]==y1) && (store[i+1][0]==x2&&store[i+1][1]==y2))
				|| (store[i][0]==x2&&store[i][1]==y2) && (store[i+1][0]==x1&&store[i+1][1]==y1))
			{
				tmpX = store[i][2]; tmpY = store[i+1][2];
				return true;
			}		
		}return false;
	}
	public void Sleep(){
		try {
			Thread.sleep(animatingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
