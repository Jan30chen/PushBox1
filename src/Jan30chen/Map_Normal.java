package Jan30chen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Map_Normal implements Map{
	private int length,height,px,py;
	private char s = ' ';
	private String path;
	private char map[][],raw_map[][];
	private JLabel map_lab[][];
	
	public Map_Normal(String pathname) throws IOException{
		loadMap(pathname);
	}
	
	public void setPath(String path){
		this.path = path;
	}

	
	@SuppressWarnings("resource")
	public void loadMap(String pathname) throws IOException{
        File filename = new File(pathname); 
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
        String line = "";  
        line = br.readLine(); 
        String[] coordinate = line.split(",");
        height = Integer.parseInt(coordinate[0]);
        length = Integer.parseInt(coordinate[1]);
        map = new char[height][length];
        raw_map = new char[height][length];
        map_lab = new JLabel[height][length];
        for(int i = 0;i < height;i++){
			for(int j = 0;j < length;j++){
				raw_map[i][j] = 'F';
				map[i][j] = 'F';
				map_lab[i][j] = new JLabel(new ImageIcon("img/Box_floor.gif"));
			}
		}
        while (line != null) {  
            line = br.readLine(); // 一次读入一行数据  
            if(line == null){
            	break;
            }
            if(line.contains("Wall")){
            	s = 'W';
         
            }
            else if(line.contains("Player")){
            	s = 'P';
  
            }
            else if(line.contains("Box")){
            	s = 'B';

            }
            else if(line.contains("Target")){
            	s = 'T';

            }
            else{
            	coordinate = line.split(",");
            	int x = Integer.parseInt(coordinate[0]);
            	int y = Integer.parseInt(coordinate[1]);
            	map[x][y] = s;
            	raw_map[x][y] = s;
            	map_lab[x][y] = new JLabel(new ImageIcon(path));
            }
        }
	}
	
	public void setMap(char p[][]){
		if(p != null){
			for(int i = 0;i < p.length;i++){
				for(int j = 0;j < p[i].length;j++){
					map[i][j] = p[i][j];
				}
			}
		}
	}
	
	public void setRawMap(){
		for(int i = 0;i < height;i++){
			for(int j = 0;j < length;j++){
				map[i][j] = raw_map[i][j];
			}
		}
	}
	
	public void getXY(){
		for(int i = 0;i < map.length;i++){
			for(int j = 0;j < map[i].length;j++){
				if(map[i][j] == 'P'){
					px = i;py = j;
				}
			}
		}
	}
	
	public boolean moveBox(int x,int y,char c){
		boolean ifChange2 = false;
		switch(c){
		case 'W':
			if(x != 0 && map[x-1][y] != 'W'){
				map[x-1][y] = map[x][y];
				ifChange2 = true;
				recovery(x,y);
			}break;
		case 'S':
			if(x != getHeight()-1 && map[x+1][y] != 'W'){
				map[x+1][y] = map[x][y];
				ifChange2 = true;
				recovery(x,y);
			}break;
		case 'D':
			if(y != getLength()-1 && map[x][y+1] != 'W'){
				map[x][y+1] = map[x][y];
				ifChange2 = true;
				recovery(x,y);
			}break;
		case 'A':
			if(y != 0 && map[x][y-1] != 'W'){
				map[x][y-1] = map[x][y];
				ifChange2 = true;
				recovery(x,y);
			}break;
		}
		ifAtCorrect();
		return ifChange2;
	}
	
	public boolean ifCanMove(int x,int y,char c){
		if(map[x][y] != 'W'){
			if(map[x][y] == 'F'||map[x][y] == 'T'){//如果旁边是地板和目的地
				return true;
			}
			else if(((map[x][y] == 'B'||map[x][y] == 'C')&&moveBox(x,y,c))){//如果旁边有箱子且可以移动
				return true;
			}
		}
		return false;
	}
	
	
	public boolean move(char c){
		boolean ifChange = false;
		getXY();
		switch(c){
		case 'W':
			if(px != 0){
				if(ifCanMove(px-1,py,c)){
					map[px-1][py] = map[px][py];
					ifChange = true;
					recovery(px,py);
				}
			}break;
		case 'S':
			if(px != getHeight()-1){
				if(ifCanMove(px+1,py,c)){
					map[px+1][py] = map[px][py];
					ifChange = true;
					recovery(px,py);
				}
			}break;
		case 'D':
			if(py != getLength()-1){
				if(ifCanMove(px,py+1,c)){
					map[px][py+1] = map[px][py];
					ifChange = true;
					recovery(px,py);
				}
			}break;
		case 'A':
			if(py != 0){
				if(ifCanMove(px,py-1,c)){
					map[px][py-1] = map[px][py];
					ifChange = true;
					recovery(px,py);
				}
			}break;
		}
		return ifChange;
	}
	
	public void recovery(int x,int y){
		switch(raw_map[x][y]){
		case 'F':
			map[x][y] = 'F';break;
		case 'T':
			map[x][y] = 'T';break;
		case 'B':
			map[x][y] = 'F';break;
		case 'P':
			map[x][y] = 'F';break;
		case 'C':
			map[x][y] = 'T';break;
		}
	}
	
	public void ifAtCorrect(){
		for(int i = 0;i < height;i++){
			for(int j = 0;j < length;j++){
				if(map[i][j] == 'B' && raw_map[i][j] == 'T'){
					map[i][j] = 'C';
				}
				else if(map[i][j] == 'C' && raw_map[i][j] == 'F'){
					map[i][j] = 'B';
				}
			}
		}
	}
	
	public boolean ifWin(){
		for(int i = 0;i < height;i++){
			for(int j = 0;j < length;j++){
				if(map[i][j] == 'B'){
					return false;
				}
			}
		}
		return true;
	}
	
	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public char getS() {
		return s;
	}

	public char[][] getMap() {
		return map;
	}
	
	public char[][] getCloneMap() {
		char clone_map[][] = new char[height][length];
		for(int i = 0;i < map.length;i++){
			for(int j = 0;j < map[i].length;j++){
				clone_map[i][j] = map[i][j];
			}
		}
		return clone_map;
	}
	
	public char[][] getRawMap(){
		return raw_map;
	}
	
	public JLabel[][] getMap_lab(){
		return map_lab;
	}

	public void printMap(char map[][]){
		System.out.println("————————————————————————————");
    	for(int i = 0;i < map.length;i++){
			for(int j = 0;j < map[i].length;j++){
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
    }
	
}
