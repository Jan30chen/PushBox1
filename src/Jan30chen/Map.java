package Jan30chen;

import java.io.IOException;

public interface Map {
	public void setPath(String path);
	
	public void loadMap(String pathname) throws IOException; 
	
	public void setMap(char p[][]);
	
	public void setRawMap();
	
	public void getXY();
	
	public boolean moveBox(int x,int y,char c);
	
	public boolean ifCanMove(int x,int y,char c);
	
	
	public boolean move(char c);
	
	public void recovery(int x,int y);
	
	public void ifAtCorrect();
	
	public boolean ifWin();
	
	public int getLength();

	public int getHeight();

	public char getS();

	public char[][] getMap();
	
	public char[][] getCloneMap();
	
	public char[][] getRawMap();
	

	public void printMap(char map[][]);
}
