package au.com.esm.eloi.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class FontRenderer {
	private TextureMap fontMap;
	private Eloi theEloi;
	
	public FontRenderer(){
		this.theEloi = Eloi.getEloi();
		this.fontMap = RenderEngine.getTextureMap(1);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float zPosition){
		theEloi.renderEngine.setRenderMode(EnumRenderMode.ORTHOGRAPHICAL);
		char[] characters = string.toCharArray();
		int mapValue;
		TextureUV uv;
		theEloi.renderEngine.bindTexture("Font");
		Constructor var1 = Constructor.instance;
		for (int i = 0; i < characters.length; i++){
			mapValue = (int)(characters[i]) - 65;
			if (mapValue == -33){
			} else if (mapValue > -31 && mapValue < 0){
				mapValue += 58;
			}
			uv = RenderEngine.getTextureMap(1).getUVCoordinates(mapValue);
			drawCharacter(var1, xPosition, yPosition, zPosition, uv);
			xPosition += 16f;
		}	
		theEloi.renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
	}
	
	private void drawCharacter(Constructor constructor, float x, float y, float z, TextureUV uv){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0, 0, 0);
		GL11.glTexCoord2f(uv.uMin, uv.vMax);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(uv.uMax, uv.vMax);
		GL11.glVertex2f(x + 25, y);
		GL11.glTexCoord2f(uv.uMax, uv.vMin);
		GL11.glVertex2f(x + 25, y + 25);
		GL11.glTexCoord2f(uv.uMin, uv.vMin);
		GL11.glVertex2f(x, y + 25);
		GL11.glEnd();
		/*
		constructor.addVertexAndUV(x, y, z, uv.uMin, uv.vMin);
		constructor.addVertexAndUV(x + 0.2f, y, z, uv.uMax, uv.vMin);
		constructor.addVertexAndUV(x + 0.2f, y + 0.2f, z, uv.uMax, uv.vMax);
		constructor.addVertexAndUV(x, y + 0.2f, z, uv.uMin, uv.vMax);
		*/
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public int getStringLength(String str){
		return 25 * str.length();
	}
}
