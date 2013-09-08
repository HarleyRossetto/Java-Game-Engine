package au.com.esm.eloi.src;

import org.lwjgl.opengl.GL11;

public class OpenGLHelper {
	public static int generateDisplayLists(int count){
		return GL11.glGenLists(count);
	}
	
	public static int[] generateDisplayListsAndFetchIDArray(int range){
		int[] oglIds = new int[range];
		int oglRangeStart = GL11.glGenLists(range);
		for (int i = 0; i < range; i++){
			oglIds[i] = oglRangeStart + i;
		}
		return oglIds;
	}
}
