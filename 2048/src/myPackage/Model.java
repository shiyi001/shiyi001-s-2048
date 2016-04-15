package myPackage;

import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

public class Model extends Observable implements Runnable{
	int[][] matrix;
	int maxX, maxY;
	Node newnode;
	
	int timeInterval = 200;
	
	int score = 0;
	int countMove = 0;
	
	boolean changed = true;
	boolean flag = true;
	boolean checked =false;
	
	public Model(int maxX, int maxY){
		this.maxX = maxX;
		this.maxY = maxY;
		
		reset();
	}
	
	public void reset(){
		matrix = new int[maxX][];
		for (int i = 0; i < maxX; i++){
			matrix[i] = new int[maxY];
			Arrays.fill(matrix[i],0);
		}
		makeNewNode();
	}
	
	private void makeNewNode(){
		newnode = createNode();
		matrix[newnode.x][newnode.y] = getNodeNum();
	}
	
	private Node createNode(){
		int x = 0, y = 0;
		do{
			Random r = new Random();
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
		}while (matrix[x][y] != 0);
		
		return new Node(x,y);
	}
	
	private int getNodeNum(){
		Random r = new Random();
		int x = r.nextInt(100);
		if (x%4 == 0){
			return 4;
		}else{
			return 2;
		}
	}
	
	public void run(){
		flag = true;
		while (flag){
			if (changed){
				setChanged();
				notifyObservers();
				countMove++;
				changed = false;
			}else{
				try{
					Thread.sleep(timeInterval);
				}catch(Exception e){
					break;
				}
			}
		}
		flag = false;
	}
	
	public void printForDebug(){
		System.out.println(checked);
		System.out.println(changed);
		for (int i=0;i<maxX;i++){
			for (int j=0;j<maxY;j++){
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	
	public void upward(){
		checked = false;
		for (int j = 0; j < maxY; j++){
			int[] tmp = new int[maxX];
			int len = 0;
			for (int i = 0; i < maxX; i++){
				if (i+1 < maxX && matrix[i][j] == 0 && matrix[i+1][j] != 0) checked = true;
				if (matrix[i][j] != 0) tmp[len++] = matrix[i][j];
			}
			for (int i = len; i < maxX; i++) tmp[i] = 0;
			for (int i = 0; i < maxX-1;i++){
				if (tmp[i] == tmp[i+1] && tmp[i] != 0){
					checked = true;
					score += tmp[i];
					tmp[i] *= 2;
					for (int k = i+1; k < maxX-1; k++){
						tmp[k] = tmp[k+1];
					}
					tmp[maxX-1] = 0;
				}
			}
			for (int i = 0; i < maxX; i++){
				matrix[i][j] = tmp[i];
			}
		}
		if (checked){
			changed = true; makeNewNode();
		}else{
			JOptionPane.showMessageDialog(null,
					"You have given a wrong order",
					"WARNING!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void downward(){
		checked = false;
		for (int j = 0; j < maxY; j++){
			int[] tmp = new int[maxX];
			int len = 0;
			for (int i = maxX-1; i >= 0; i--){
				if (i-1 >= 0 && matrix[i][j] == 0 && matrix[i-1][j] !=0) checked = true;
				if (matrix[i][j] != 0) tmp[len++] = matrix[i][j];
			}
			for (int i = len; i < maxX; i++) tmp[i] = 0;
			for (int i = 0; i < maxX-1;i++){
				if (tmp[i] == tmp[i+1] && tmp[i] != 0){
					checked = true;
					score += tmp[i];
					tmp[i] *= 2;
					for (int k = i+1; k < maxX-1; k++){
						tmp[k] = tmp[k+1];
					}
					tmp[maxX-1] = 0;
				}
			}
			for (int i = maxX-1; i >= 0; i--){
				matrix[i][j] = tmp[maxX-1-i];
			}
		}
		if (checked){
			changed = true; makeNewNode();
		}else{
			JOptionPane.showMessageDialog(null,
					"You have given a wrong order",
					"WARNING!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void leftward(){
		checked = false;
		for (int i = 0; i < maxX; i++){
			int[] tmp = new int[maxY];
			int len = 0;
			for (int j = 0; j < maxY; j++){
				if (matrix[i][j] != 0) tmp[len++] = matrix[i][j];
				if (j+1 < maxY && matrix[i][j] == 0 && matrix[i][j+1] != 0) checked = true;
			}
			for (int j = len; j < maxY; j++) tmp[j] = 0;
			for (int j = 0; j < maxY-1;j++){
				if (tmp[j] == tmp[j+1] && tmp[j] != 0){
					checked = true;
					score += tmp[j];
					tmp[j] *= 2;
					for (int k = j+1; k < maxY-1; k++){
						tmp[k] = tmp[k+1];
					}
					tmp[maxY-1] = 0;
				}
			}
			for (int j = 0; j < maxY; j++){
				matrix[i][j] = tmp[j];
			}
		}
		if (checked){
			changed = true; makeNewNode();
		}else{
			JOptionPane.showMessageDialog(null,
					"You have given a wrong order",
					"WARNING!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void rightward(){
		checked = false;
		for (int i = 0; i < maxX; i++){
			int[] tmp = new int[maxY];
			int len = 0;
			for (int j = maxY-1; j >= 0; j--){
				if (matrix[i][j] != 0) tmp[len++] = matrix[i][j];
				if (j-1 >= 0 && matrix[i][j] == 0 && matrix[i][j-1] != 0) checked = true;
			}
			for (int j = len; j < maxY; j++) tmp[j] = 0;
			for (int j = 0; j < maxY-1;j++){
				if (tmp[j] == tmp[j+1] && tmp[j] != 0){
					checked = true;
					score += tmp[j];
					tmp[j] *= 2;
					for (int k = j+1; k < maxY-1; k++){
						tmp[k] = tmp[k+1];
					}
					tmp[maxY-1] = 0;
				}
			}	
			for (int j = 0; j < maxY; j++){
				matrix[i][j] = tmp[maxY-1-j];
			}
		}
		if (checked) {
			changed = true; makeNewNode();
		}else{
			JOptionPane.showMessageDialog(null,
					"You have given a wrong order",
					"WARNING!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

class Node{
	int x, y;
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}