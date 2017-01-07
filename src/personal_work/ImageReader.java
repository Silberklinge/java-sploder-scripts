package personal_work;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageReader {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String filepath = System.getProperty("user.home") + "\\Desktop\\sploderscreen\\forums\\pieces\\previewtopic\\"; // sploderscreen\\graphicscreator\\leftbuttons\\
		System.out.println("What is the name of the image (w/ extension) you would like to open?");
		String filename = keyboard.nextLine();
		System.out.println("What transparency level would you like? 1-100:");
		int transparencyLvl = 100;
		boolean bError = true;
		while (bError) {
	        if (keyboard.hasNextInt())
	            transparencyLvl = keyboard.nextInt();
	        else {
	        	System.out.println("Invalid number value. Please re-enter:");
	            keyboard.next();
	            continue;
	        }
	        bError = false;
	    }
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(filepath + filename));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filename + ".");
			System.exit(0);
		}
		
		System.out.printf("Loaded %s successfully.%n", filename);
		System.out.printf("Copying image data from %s to byte array...%n", filename);
		
		int num = img.getHeight() * img.getWidth();
		String[] byteArray = new String[num];
		
		try {
		for(int y = 0; y < img.getHeight(); y++) {
			for(int x = 0; x < img.getWidth(); x++) {
				int clr   = img.getRGB(x, y);
				int arrayPos = (y * img.getWidth()) + x;
				
				int alpha = (clr >> 24) & 0xff;
				int red   = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue  =  clr & 0x000000ff;
				int opacityLvl = (int)(((double) alpha)*((double) transparencyLvl)/100);
				String hex = String.format("%02x%02x%02x%02x", opacityLvl, red, green, blue).toUpperCase();
				byteArray[arrayPos] = hex;
		    }
		}
		} catch(Exception e) {
			System.out.println("Failed to copy image data to byte array.");
			System.exit(0);
		}
		
		System.out.println("Copied image data successfully.");
		System.out.println("Copying byte array to clipboard...");
		
		StringBuilder builder = new StringBuilder();
		
		for(String s : byteArray)
		    builder.append(s);
		
		String collectionOfBytes = builder.toString() + "00";
		
		StringSelection selection = new StringSelection(collectionOfBytes);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	    
	    keyboard.close();
	    System.out.println("Copied byte array to clipboard!");
		
	}
}
