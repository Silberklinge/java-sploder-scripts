package personal_work;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageReaderAvatar {
	public static final int AVATAR_SIZE = 24;
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String filepath = System.getProperty("user.home") + "\\Desktop\\Avatars\\";
		System.out.println("What is the name of the avatar (w/ extension) you would like to open?");
		String filename = keyboard.nextLine();
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(filepath + filename));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filename + ".");
			System.exit(0);
		}
		
		if(img.getWidth() != AVATAR_SIZE || img.getHeight() != AVATAR_SIZE) {
			System.out.println("Image is not 24x24");
			System.exit(0);
		}
		
		System.out.printf("Loaded %s successfully.%n", filename);
		System.out.printf("Copying image data from %s to byte array...%n", filename);
		
		String[] byteArray = new String[AVATAR_SIZE*AVATAR_SIZE];
		
		try {
		for(int y = 0; y < AVATAR_SIZE; y++) {
			for(int x = 0; x < AVATAR_SIZE; x++) {
				int clr = img.getRGB(x, y);
				int arrayPos = (y * AVATAR_SIZE) + x;
				
				int alpha = (clr >> 24) & 0xff;
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				String hex = String.format("%02x%02x%02x%02x", alpha, red, green, blue).toUpperCase();
				byteArray[arrayPos] = hex;
		    }
		}
		} catch(Exception e) {
			System.out.println("Failed to copy image data to byte array.");
			System.exit(0);
		}
		
		System.out.println("Copied image data successfully.");
		System.out.println("Copying byte array to clipboard...");
		
		StringBuilder bytes = new StringBuilder();
		for(int counter = 0; counter < 320; counter++)
			bytes.append("00000000");
		
		for(int counter = 0; counter < AVATAR_SIZE; counter++) {
			for(int a = 0; a < 8; a++)
				bytes.append("00000000");
			for(int a = 0; a < AVATAR_SIZE; a++)
				bytes.append(byteArray[a+(counter*AVATAR_SIZE)]);
			for(int a = 0; a < 8; a++)
				bytes.append("00000000");
		}
		
		for(int counter = 0; counter < 320; counter++)
			bytes.append("00000000");
		
		bytes.append("00");
		
		StringSelection selection = new StringSelection(bytes.toString());
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	    
	    keyboard.close();
	    System.out.println("Copied byte array to clipboard!");
	    System.out.println(bytes.length());
		
	}
}
