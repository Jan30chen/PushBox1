package Jan30chen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PushBox extends JFrame implements ActionListener,KeyListener{
	public JPanel panel = new JPanel(),btns = new JPanel(),btns2 = new JPanel();
	public int count = 0;
	public JButton btn1 = new JButton("��ʼ"),
			        btn2 = new JButton("����"),
			        btn3 = new JButton("���ص�ͼ"),
			        btn_w = new JButton("��"),
			        btn_s = new JButton("��"),
			        btn_a = new JButton("��"),
			        btn_d = new JButton("��");  
	String name,path = "img/normal/",str_end = "��ϲͨ�أ�";
	public JLabel score;
	
	Map_Normal m1 = new Map_Normal("map/map-001.txt");
	Map_Plugin m2 = new Map_Plugin("map/map-001.txt");
	Game game = new Game("map/map-001.txt");
	Memento memento = new Memento();
	
	public PushBox() throws IOException{
		name = JOptionPane.showInputDialog("�����������");
		setPath();
		addMapLab();
		addBtns2();
		score = new JLabel(name+"Ŀǰ�Ĳ�����0");
		add(score,BorderLayout.NORTH);
		btn2.addActionListener(this);
		add(panel,BorderLayout.CENTER);
		add(btns2,BorderLayout.SOUTH);
		btn1.addKeyListener(this);
		
		this.setVisible(true);
		this.setTitle("������");
		this.setSize(45*game.getmap().getLength(),45*game.getmap().getHeight()+80);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public void addBtns(){
		btns2.setLayout(new BorderLayout());
		btns2.add(btn_w,BorderLayout.NORTH);btn_w.addActionListener(this);
		btns2.add(btn_s,BorderLayout.SOUTH);btn_s.addActionListener(this);
		btns2.add(btn_a,BorderLayout.WEST);btn_a.addActionListener(this);
		btns2.add(btn_d,BorderLayout.EAST);btn_d.addActionListener(this);
		btns2.add(btn1,BorderLayout.CENTER);btn1.addActionListener(this);
	}
	
	public void addBtns2(){
		btns2.setLayout(new BorderLayout());
		btns2.add(btn1,BorderLayout.WEST);btn2.addActionListener(this);
		btns2.add(btn2,BorderLayout.EAST);btn3.addActionListener(this);
		btns2.add(btn3,BorderLayout.CENTER);btn1.addActionListener(this);
	}
	
	public void addMapLab(){
		panel.removeAll();
		panel.repaint();
		for(int x = 0;x<game.getmap().getHeight();x++){
			for(int y = 0;y<game.getmap().getLength();y++){
				switch(game.getmap().getMap()[x][y]){
				case 'W':
					panel.add(new JLabel(new ImageIcon(path+"Box_wall.png")));break;
				case 'F':
					panel.add(new JLabel(new ImageIcon(path+"Box_floor.png")));break;
				case 'T':
					panel.add(new JLabel(new ImageIcon(path+"Box_trg.png")));break;
				case 'B':
					panel.add(new JLabel(new ImageIcon(path+"Box_box1.png")));break;
				case 'P':
					panel.add(new JLabel(new ImageIcon(path+"Box_player.png")));break;	
				case 'C':
					panel.add(new JLabel(new ImageIcon(path+"Box_box2.png")));break;	
				}
			}
		}
		emmm();
	}
	
	public void emmm(){
		if((count+1)%2 != 0){
			this.setSize(45*game.getmap().getLength(),45*game.getmap().getHeight()+80);
		}
		else{
			this.setSize(45*game.getmap().getLength(),45*game.getmap().getHeight()+81);
		}
		count++;
	}
	
	public void setPath(){
		if(name == null){
			name = "��";
		}
		switch(name){
		case "������":
			path = "img/hja/";break;
		case "���ı�":
			game.changeMap();break;
		case "Java":
			path = "img/Java/";str_end = "ף��ҿ��Բ��ҿƣ�";break;
		default:
		}
		
	}
	
	public void loadMap(String path) throws IOException{
		game.loadPath(path);
		addMapLab();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		score.setText(name+"Ŀǰ�Ĳ�����"+count);
		switch(e.getActionCommand()){
		case "���ص�ͼ":
			String path = JOptionPane.showInputDialog("������");
			try {
				game.loadPath("map/"+path+".txt");
			} 
			catch(FileNotFoundException error1){
			    error1.printStackTrace(); 
			}catch (IOException  error2) {
			    error2.printStackTrace(); 
			}
			
			memento.emptyMap();addMapLab();count = 0;break;
		case "��ʼ":
			game.getmap().setRawMap();memento.emptyMap();addMapLab();count = 0;btn1.setText("����");break;
		case "����":
			game.getmap().setRawMap();memento.emptyMap();addMapLab();count = 0;break;
		case "����":
			game.getmap().setMap(memento.getMap());addMapLab();break;
		case "��":
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('W');addMapLab();break;
		case "��":
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('S');addMapLab();break;
		case "��":
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('A');addMapLab();break;
		case "��":
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('D');addMapLab();break;
		}
		if(game.getmap().ifWin()){
			count = 0;
			game.getmap().setRawMap();addMapLab();
			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
					str_end, "���", JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		score.setText(name+"Ŀǰ�Ĳ�����"+count);
		switch(e.getKeyCode()){
		case KeyEvent.VK_F:
			game.changeMap();memento.emptyMap();addMapLab();break;
		case KeyEvent.VK_R:
			game.getmap().setRawMap();memento.emptyMap();addMapLab();count = 0;break;
		case KeyEvent.VK_W:
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('W');addMapLab();break;
		case KeyEvent.VK_S:
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('S');addMapLab();break;
		case KeyEvent.VK_A:
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('A');addMapLab();break;
		case KeyEvent.VK_D:
			memento.setMap(game.getmap().getCloneMap());game.getmap().move('D');addMapLab();break;
		case KeyEvent.VK_E:
			game.getmap().setMap(memento.getMap());addMapLab();break;
		case KeyEvent.VK_Q:
			System.exit(0);
		}
		if(game.getmap().ifWin()){
			count = 0;
			game.getmap().setRawMap();addMapLab();
			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
					str_end, "���", JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	
	public static void main(String[] args) throws IOException{
		new PushBox();
	}
}
