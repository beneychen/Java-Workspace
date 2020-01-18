package ini_DRAWPLATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

//���ʼ����� ��ӿ�
public class DrawerListener implements ActionListener,MouseListener {
    int x1,y1,x2,y2;
    Graphics graph; //��������,��������JPanel
    Color curColor;
    String type = "Line";
    public Shape[] sharr = new Shape[1000];
    int index = 0;
    public void setArr(Shape[] arr){
    	sharr = arr;
    }
    //��ť�ļ���
    public void actionPerformed(ActionEvent e){
    	String order = e.getActionCommand();//��ȡ��ť�ϵĶ�������
    	if(order.equals("")){//������ɫ
    		Object obj = e.getSource();
    		JButton button = (JButton)obj; //�¼�Դ�������ǰ�ť������ǿ��ת��
    		//��ȡ��ɫ
    		curColor = button.getBackground();//�õ�������ɫ
    		graph.setColor(curColor);
    	}
    	else{
    		type = e.getActionCommand();
    	}
    }
    //���ļ���
	public void mousePressed(MouseEvent e){
//		System.out.println("Pressed");
		x1 = e.getX();  //ע���ǵõ��¼�Դ�����꣬�����Ϊ�¼�Դ
		y1 = e.getY();
    }
    public void mouseReleased(MouseEvent e){
//    	System.out.println("Released");
    	x2 = e.getX();
    	y2 = e.getY();
    	//�ɿ�ʱ����ͼ
    	//"Line", "Oval", "RoundRec", "Rect","fillOval"
    	if(type.equals("Line")){
//    		graph.drawLine(x1, y1, x2, y2);
    		Shape line = new Shape(type,curColor,x1,y1,x2,y2);
    		line.DrawShape(graph);
    		sharr[index++] = line;
    	}
    		
    	else if(type.equals("Oval")){
//    		graph.drawOval(x1, y1, 120, 100);
    		Shape oval = new Shape(type,curColor,x1,y1,x2,y2,120,100);
    		oval.DrawShape(graph);
    		sharr[index++] = oval;
    	}
    		
    	else if(type.equals("Magic")){
    		MagicPen mpen = new MagicPen();
    		mpen.color = curColor;
    		mpen.curX = x1;
    		mpen.curY = y1;
    		mpen.magicPen1(graph);
    		sharr[index++] = new Shape("Image",0,0,mpen.returnImage());
    	}
    	else if(type.equals("Cantor")){
    		Cantor cpen = new Cantor();
    		cpen.color = curColor;
    		cpen.draw(graph, x1, x2, y2, 4);
    		sharr[index++] = new Shape("Image",0,0,cpen.returnImage());
    	}
    	else if(type.equals("Blanket")){
    		Blanket bpen = new Blanket();
    		bpen.color = curColor;
    		bpen.draw(graph, x1, y1, 50, 6);
    		sharr[index++] = new Shape("Image",0,0,bpen.returnImage());
    	}
    	else if(type.equals("Pythagoras")){
    		Pythagoras ppen = new Pythagoras();
    		ppen.curColor = curColor;
    		ppen.draw(graph,(double)x1,(double)y1,(double)x2,(double)y2,8);
    		sharr[index++] = new Shape("Image",0,0,ppen.returnImage());
    	}
    	else if(type.equals("Stereography")){//������
    		Stereography spen = new Stereography();
//    		spen.Draw0(graph, x1, y1);
    		spen.DrawSponge(graph,x1,y1,3);
//    		spen.DrawPlate(graph, x1, y1, 3);
    	}
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}