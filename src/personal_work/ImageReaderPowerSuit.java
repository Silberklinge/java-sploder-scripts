package personal_work;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageReaderPowerSuit {
	public static void main(String[] args) {
		final int LEG_WIDTH, LEG_HEIGHT, FOREARM_WIDTH, FOREARM_LENGTH;
		final int HEAD_SIZE = 36;
		final int UPPER_ARM_WIDTH = LEG_WIDTH = FOREARM_WIDTH = 24;
		final int UPPER_ARM_HEIGHT = LEG_HEIGHT = FOREARM_LENGTH = 30;
		
		
		Scanner keyboard = new Scanner(System.in);
		String filepath = System.getProperty("user.home") + "\\Desktop\\";
		
		System.out.println("What is the name of the image (w/ extension) you would like to open?");
		System.out.println("This image will be the head of the mech. Make sure the head faces to the left!");
		String filenameHead = keyboard.nextLine();
		
		System.out.println("What is the name of the image (w/ extension) you would like to open?");
		System.out.println("This image will be the upper arm of the mech. Make sure the head faces to the left!");
		String filenameUpperArm = keyboard.nextLine();
		
		System.out.println("What is the name of the image (w/ extension) you would like to open?");
		System.out.println("This image will be the forearm of the powersuit. Make sure the head faces to the left!");
		String filenameForeArm = keyboard.nextLine();
		
		System.out.println("What is the name of the image (w/ extension) you would like to open?");
		System.out.println("This image will be the legs of the powersuit. Make sure the head faces to the left!");
		String filenameLegs = keyboard.nextLine();
		
		BufferedImage imgHead = null;
		BufferedImage imgUpperArm = null;
		BufferedImage imgForeArm = null;
		BufferedImage imgLegs = null;
		
		try {
			imgHead = ImageIO.read(new File(filepath + filenameHead));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filenameHead + ".");
			System.exit(0);
		}
		
		System.out.printf("Loaded %s successfully.%n", filenameHead);
		System.out.printf("Copying image data from %s to byte array...%n", filenameHead);
		
		int num = imgHead.getHeight() * imgHead.getWidth();
		String[] byteArray = new String[num];
		
		try {
		for(int y = 0; y < imgHead.getHeight(); y++) {
			for(int x = 0; x < imgHead.getWidth(); x++) {
				int clr   = imgHead.getRGB(x, y);
				int arrayPos = (y * imgHead.getWidth()) + x;
				
				int alpha = (clr >> 24) & 0xff;
				int red   = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue  =  clr & 0x000000ff;
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
