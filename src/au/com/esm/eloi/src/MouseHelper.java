package au.com.esm.eloi.src;

import org.lwjgl.input.Mouse;

public class MouseHelper {
	private static boolean isGrabbed = false;
	
	public static void grab(){
		Mouse.setGrabbed(true);
		isGrabbed = true;
	}
	
	public static void unGrab(){
		Mouse.setGrabbed(false);
		isGrabbed = false;
	}
	
	public static boolean isGrabbed(){
		return isGrabbed;
	}
}
