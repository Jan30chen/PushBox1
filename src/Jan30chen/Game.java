package Jan30chen;

import java.io.IOException;

public class Game {
	Map map,map1,map2;
	
	Game(String path) throws IOException{
		map1 = new Map_Normal(path);
		map2 = new Map_Plugin(path);
		map = map1;
	}
	
	public void up(){
		map.move('W');
	}
	
	public void down(){
		map.move('S');
	}
	
	public void left(){
		map.move('A');
	}
	
	public void right(){
		map.move('D');
	}
	
	public void loadPath(String path) throws IOException{
		map1.loadMap(path);
		map2.loadMap(path);
	}
	
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public void changeMap(){
		if(map != map2){
			setMap(map2);
		}
		else{
			setMap(map1);
		}
	}
	
	public Map getmap(){
		return map;
	}
	
	public Map getmap1(){
		return map1;
	}
	
	public Map getMap2(){
		return map2;
	}

}
