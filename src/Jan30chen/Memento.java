package Jan30chen;

import java.util.Stack;

public class Memento {
	private Stack<char[][]> mapStack;
	
	Memento(){
		mapStack = new Stack<char[][]>();
	}
	
	public void setMap(char[][] map){
		mapStack.push(map);
	}
	
	public char[][] getMap(){
		if(!mapStack.isEmpty()){
			return mapStack.pop();
		}
		return null;
	}
	
	public void emptyMap(){
		while(!mapStack.isEmpty()){
			   mapStack.pop();
			}
	}
}
