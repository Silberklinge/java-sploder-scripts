package personal_work;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RotateGame {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.printf("What is the name of the .txt file with the data?%n");
		String filename = keyboard.nextLine();
		String filepath = System.getProperty("user.home") + "\\Desktop\\" + filename + ".txt";
		
		File objDataFile = new File(filepath);
		Scanner objData = null;
		try {
			objData = new Scanner(objDataFile);
			objData.useDelimiter("\\|");
		} catch(Exception e) {
			System.out.printf("Failed to open %s.%n", filepath);
			System.exit(0);
		}
		
		System.out.println("How many degrees do you want to rotate the game playfield?");
		System.out.println("(Positive degrees is anticlockwise, negative degrees is clockwise.)");
		int deg = keyboard.nextInt();
		
		System.out.println("What are the X-coordinates of the center of rotation?");
		int xCenter = keyboard.nextInt();
		System.out.println("What are the Y-coordinates of the center of rotation?");
		int yCenter = keyboard.nextInt();
		
		ArrayList<String> objDataArray = new ArrayList<String>();
		StringBuilder result = new StringBuilder();
		
		while(objData.hasNext()) {
			objDataArray.add(objData.next());
		}
		
		for(String s : objDataArray) {
			String[] attributes = s.split("[,]");
			int originalX = Integer.parseInt(attributes[1]);
			int originalY = Integer.parseInt(attributes[2]);
			int originalRotate = 0;
			if(attributes.length == 4)
				originalRotate = Integer.parseInt(attributes[3]);
			int newX = rotateX(originalX, originalY, xCenter, yCenter, deg);
			int newY = rotateY(originalX, originalY, xCenter, yCenter, deg);
			int newDeg = originalRotate + deg;
			if(newDeg == 0 || newDeg%360 == 0)
				s = attributes[0] + "," + String.format("%d,%d", newX, newY);
			else
				s = attributes[0] + "," + String.format("%d,%d,%d", newX, newY, newDeg);
			
			result.append(String.format("%s|", s));
		}
		
		result.setLength(result.length() - 1);
		System.out.printf("%s%n%d", result, result.length());
	}
	
	public static int rotateX(int originalX, int originalY, int rotatePtX, int rotatePtY, int deg) {
		double length = Math.sqrt(Math.pow(originalY - rotatePtY, 2)+Math.pow(originalX - rotatePtX, 2));
		double theta = Math.toRadians(deg) + Math.atan((originalY - rotatePtY)/(originalX - rotatePtX));
		int result = (int)(originalX + (length*Math.cos(theta)));
		return result;
	}
	
	public static int rotateY(int originalX, int originalY, int rotatePtX, int rotatePtY, int deg) {
		double length = Math.sqrt(Math.pow(originalY - rotatePtY, 2)+Math.pow(originalX - rotatePtX, 2));
		double theta = Math.toRadians(deg) + Math.atan((originalY - rotatePtY)/(originalX - rotatePtX));
		int result = (int)(originalY + (length*Math.sin(theta)));
		return result;
	}
}
