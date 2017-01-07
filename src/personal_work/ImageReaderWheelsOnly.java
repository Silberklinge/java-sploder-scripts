package personal_work;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageReaderWheelsOnly {
	public static final int WHEEL_DIMENSIONS = 30;
	public static final int BODY_HEIGHT = 30;
	public static final int BODY_LENGTH = 60;
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String filepath = System.getProperty("user.home") + "\\Desktop\\";
		
		System.out.println("What is the name of the first image (w/ extension) you would like to open?");
		System.out.println("This image will be the left wheel.");
		String filenameLeftWheel = keyboard.nextLine();
		
		System.out.println("What is the name of the second image (w/ extension) you would like to open?");
		System.out.println("This image will be the right wheel.");
		String filenameRightWheel = keyboard.nextLine();
		
		System.out.println("What is the name of the third image (w/ extension) you would like to open?");
		System.out.println("This image will be the main body.");
		String filenameBody = keyboard.nextLine();
		
		BufferedImage imgLeftWheel = null;
		BufferedImage imgRightWheel = null;
		BufferedImage imgBody = null;
		
		try {
			imgLeftWheel = ImageIO.read(new File(filepath + filenameLeftWheel));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filenameLeftWheel + ".");
			System.exit(0);
		}
		
		try {
			imgRightWheel = ImageIO.read(new File(filepath + filenameRightWheel));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filenameRightWheel + ".");
			System.exit(0);
		}
		
		try {
			imgBody = ImageIO.read(new File(filepath + filenameBody));
		} catch(IOException e) {
			System.out.println("Failed to load - " + filepath + filenameBody + ".");
			System.exit(0);
		}
		
		if(imgLeftWheel.getWidth() != WHEEL_DIMENSIONS || imgLeftWheel.getHeight() != WHEEL_DIMENSIONS || imgRightWheel.getWidth() != WHEEL_DIMENSIONS || imgRightWheel.getHeight() != WHEEL_DIMENSIONS) {
			System.out.println("At least one wheel image is not 30x30");
			System.exit(0);
		}
		
		if(imgBody.getWidth() != BODY_LENGTH || imgBody.getHeight() != BODY_HEIGHT) {
			System.out.println("The main image is not 60x30");
			System.exit(0);
		}
		
		System.out.printf("Loaded %s and %s successfully.%n", filenameLeftWheel, filenameRightWheel);
		System.out.printf("Copying image data from %s and %s to byte array...%n", filenameLeftWheel, filenameRightWheel);
		
		String[][] byteArray = new String[60][60];
		
		try {
			for(int y = 0; y < BODY_HEIGHT; y++) {
				for(int x = 0; x < BODY_LENGTH; x++) {
					int clr = imgBody.getRGB(x, y);
					
					int alpha = (clr >> 24) & 0xff;
					int red = (clr & 0x00ff0000) >> 16;
					int green = (clr & 0x0000ff00) >> 8;
					int blue = clr & 0x000000ff;
					String hex = String.format("%02x%02x%02x%02x", alpha, red, green, blue).toUpperCase();
					byteArray[y][x] = hex;
			    }
			}
			
			for(int y = 0; y < WHEEL_DIMENSIONS; y++) {
				for(int x = 0; x < WHEEL_DIMENSIONS; x++) {
					int clr = imgLeftWheel.getRGB(x, y);
					
					int alpha = (clr >> 24) & 0xff;
					int red = (clr & 0x00ff0000) >> 16;
					int green = (clr & 0x0000ff00) >> 8;
					int blue = clr & 0x000000ff;
					String hex = String.format("%02x%02x%02x%02x", alpha, red, green, blue).toUpperCase();
					byteArray[WHEEL_DIMENSIONS + y][x] = hex;
			    }
			}
			
			for(int y = 0; y < WHEEL_DIMENSIONS; y++) {
				for(int x = 0; x < WHEEL_DIMENSIONS; x++) {
					int clr = imgRightWheel.getRGB(x, y);
					
					int alpha = (clr >> 24) & 0xff;
					int red = (clr & 0x00ff0000) >> 16;
					int green = (clr & 0x0000ff00) >> 8;
					int blue = clr & 0x000000ff;
					String hex = String.format("%02x%02x%02x%02x", alpha, red, green, blue).toUpperCase();
					byteArray[WHEEL_DIMENSIONS + y][WHEEL_DIMENSIONS + x] = hex;
			    }
			}
		} catch(Exception e) {
			System.out.println("Failed to copy image data to byte array.");
			System.exit(0);
		}
		
		System.out.println("Copied image data successfully.");
		System.out.println("Copying byte array to clipboard...");
		
		StringBuilder bytes = new StringBuilder();
		
		for(String[] sArr : byteArray) {
			for(String s : sArr) {
				bytes.append(s);
			}
		}
		
		bytes.append("00");

		StringSelection selection = new StringSelection(bytes.toString());
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);

	    keyboard.close();
	    System.out.println("Copied byte array to clipboard!");
		
	}
}
