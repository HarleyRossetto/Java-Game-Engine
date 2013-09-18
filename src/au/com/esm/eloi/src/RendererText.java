package au.com.esm.eloi.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RendererText {
	private TextureMap fontMap;
	private Eloi theEloi;
	public static final int squareSize = 16;
	
	public RendererText(){
		this.theEloi = Eloi.getEloi();
		this.fontMap = RenderEngine.getTextureMap(1);
	}
	
	public void drawString(String string, float xPosition, float yPosition){
		this.drawString(string, xPosition, yPosition, 0f, 0f, 0f, 0.5f);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float alpha){
		this.drawString(string, xPosition, yPosition, 0f, 0f, 0f, alpha);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float red, float green, float blue){
		this.drawStringInternal(string, xPosition, yPosition, red, green, blue, 1.0F);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float red, float green, float blue, float alpha){
		this.drawStringInternal(string, xPosition, yPosition, red, green, blue, alpha);
	}
	
	private void drawStringInternal(String string, float xPosition, float yPosition, float red, float green, float blue, float alpha){
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
			drawCharacter(var1, xPosition, yPosition, 0, uv, red, green, blue, alpha);
			xPosition += 16f;
		}	
		theEloi.renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
	}
	
	private void drawCharacter(Constructor constructor, float x, float y, float z, TextureUV uv, float red, float green, float blue, float alpha){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glTexCoord2f(uv.uMin, uv.vMax);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(uv.uMax, uv.vMax);
		GL11.glVertex2f(x + squareSize, y);
		GL11.glTexCoord2f(uv.uMax, uv.vMin);
		GL11.glVertex2f(x + squareSize, y + squareSize);
		GL11.glTexCoord2f(uv.uMin, uv.vMin);
		GL11.glVertex2f(x, y + squareSize);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public int getStringLength(String str){
		return 25 * str.length();
	}
}
