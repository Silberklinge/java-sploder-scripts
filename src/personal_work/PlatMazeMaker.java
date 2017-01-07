package personal_work;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class PlatMazeMaker {
	
	public static final int PIECE_SIZE = 16;
	public static BufferedImage img = null;
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String filepath = System.getProperty("user.home") + "\\Desktop\\";
		System.out.println("What is the name of the maze (w/ extension) you would like to open?");
		String filename = keyboard.nextLine();
		
		
		
		try {
			img = ImageIO.read(new File(filepath + filename));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filename + ".");
			System.exit(0);
		}
		
		System.out.printf("Loaded %s successfully.%n", filename);
		
		int numOfPiecesInRow = (img.getWidth() - 2)/PIECE_SIZE;
		int numOfPiecesInCol = (img.getHeight() - 2)/PIECE_SIZE;
		StringBuilder code = new StringBuilder();
		
		for(int col = 0; col < numOfPiecesInCol; col++) {
			for(int row = 0; row < numOfPiecesInRow; row++) {
				int piecePosX = 150+(300*row);
				int piecePosY = -150-(300*col);
				code.append(String.format(getPieceType(row, col), piecePosX, piecePosY));
			}
		}
		
		System.out.print(code.length());
//		System.out.print(code.toString());
		
		StringSelection selection = new StringSelection(code.toString());
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	    
	    keyboard.close();
		
	}
	
	public static String getPieceType(int pieceX, int pieceY) {
		// pieceX and pieceY numbers start at zero
		int xOfTop = (PIECE_SIZE*pieceX) + 8;
		int yOfTop = (PIECE_SIZE*pieceY) + 1;
		int xOfBottom = (PIECE_SIZE*pieceX) + 8;
		int yOfBottom = PIECE_SIZE*(pieceY + 1);
		int xOfRight = PIECE_SIZE*(pieceX + 1);
		int yOfRight = (PIECE_SIZE*pieceY) + 8;
		int xOfLeft = (PIECE_SIZE*pieceX) + 1;
		int yOfLeft = (PIECE_SIZE*pieceY) + 8;
		
		String clrTop = String.format("%02x", (img.getRGB(xOfTop, yOfTop) & 0x000000ff)).toUpperCase();
		String clrBottom = String.format("%02x", (img.getRGB(xOfBottom, yOfBottom) & 0x000000ff)).toUpperCase();
		String clrRight = String.format("%02x", (img.getRGB(xOfRight, yOfRight) & 0x000000ff)).toUpperCase();
		String clrLeft = String.format("%02x", (img.getRGB(xOfLeft, yOfLeft) & 0x000000ff)).toUpperCase();
		
		String c = clrTop + clrRight + clrBottom + clrLeft; 
		String result = "null";
		
		switch(c) {
			case "FF000000": result = "84,%d,%d|"; break;
			case "00FF0000": result = "84,%d,%d,90|"; break;
			case "0000FF00": result = "84,%d,%d,180|"; break;
			case "000000FF": result = "84,%d,%d,270|"; break;
			case "00FF00FF": result = "80,%d,%d|"; break;
			case "FF00FF00": result = "80,%d,%d,90|"; break;
			case "FFFF0000": result = "81,%d,%d|"; break;
			case "00FFFF00": result = "81,%d,%d,90|"; break;
			case "0000FFFF": result = "81,%d,%d,180|"; break;
			case "FF0000FF": result = "81,%d,%d,270|"; break;
			case "00FFFFFF": result = "82,%d,%d|"; break;
			case "FF00FFFF": result = "82,%d,%d,90|"; break;
			case "FFFF00FF": result = "82,%d,%d,180|"; break;
			case "FFFFFF00": result = "82,%d,%d,270|"; break;
			case "FFFFFFFF": result = "83,%d,%d|"; break;
		}
		
		return result;
	}
}
