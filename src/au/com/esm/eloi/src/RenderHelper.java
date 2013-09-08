package au.com.esm.eloi.src;

public class RenderHelper {
	public static float[] renderOffsets = {0f, 0.01f, 0.02f, 0.03f};
	
	public static float getRenderOffset(int renderLevel){
		return renderOffsets[renderLevel];
	}
}
