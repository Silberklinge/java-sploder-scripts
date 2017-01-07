package plat_rotator;

import java.util.Arrays;
import java.util.StringJoiner;

public class Item {

	private Integer ID;
	private Integer x;
	private Integer y;
	private Integer rotation = 0;
	
	public Item(Integer ID, Integer x, Integer y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
	}
	
	public Item(Integer ID, Integer x, Integer y, Integer rotation) {
		this(ID, x, y);
		this.rotation = rotation;
	}
	
	public Item(String s) {
		String[] numbers = s.split(",");
		ID = Integer.parseInt(numbers[0]);
		x = Integer.parseInt(numbers[1]);
		y = Integer.parseInt(numbers[2]);
		if(numbers.length == 4)
			rotation = Integer.parseInt(numbers[3]);
	}
	
	private static Integer round(Double i) {
		return 60 * (int) Math.round(i/60);
	}
	
	public String parse() {
		StringJoiner s = new StringJoiner(",");
		s.add(ID.toString())
		.add(x.toString())
		.add(y.toString())
		.add(rotation.toString());
		return s.toString();
	}
	
	public Item rotate(int degrees, int xPivot, int yPivot) { // in game, positive is clockwise, negative is anticlockwise
		rotation += degrees;
		
		if(rotation >= 360)
			rotation %= 360;
		if(rotation <= 0)
			while(rotation < 0)
				rotation += 360;
		
		if(xPivot != x && yPivot != y) {
			double pivotLength = Math.hypot(x - xPivot, y - yPivot);
			double referenceAngle = -Math.atan((y - yPivot)/(x - xPivot));
			double newAngle = referenceAngle + Math.toRadians(degrees);
			y = round(y - (pivotLength * Math.sin(newAngle)));
			x = round(x + (pivotLength * Math.cos(newAngle)));
		}
		
		return this;
	}
	
	public Item rotate(int degrees) {
		return rotate(degrees, x, y);
	}
	
	public Item translate(int xRel, int yRel) {
		x += xRel;
		y += yRel;
		return this;
	}
	
	public Item translateAbsolute(int xAbs, int yAbs) {
		x = xAbs;
		y = yAbs;
		return this;
	}
}
