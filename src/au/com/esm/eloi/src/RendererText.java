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
		this.drawString(string, xPosition, yPosition, 0F, 0F, 0F, 0.5F);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float alpha){
		this.drawString(string, xPosition, yPosition, 0F, 0F, 0F, alpha);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float red, float green, float blue){
		this.drawStringInternal2D(string, xPosition, yPosition, red, green, blue, 1.0F);
	}
	
	public void drawString(String string, float xPosition, float yPosition, float red, float green, float blue, float alpha){
		this.drawStringInternal2D(string, xPosition, yPosition, red, green, blue, alpha);
	}
	
	public void drawString3D(String string, float xPosition, float yPosition, float zPosition){
		this.drawStringInternal3D(string, xPosition, yPosition, zPosition, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F);
	}
	
	public void drawString3D(String string, float xPosition, float yPosition, float zPosition, float red, float green, float blue){
		this.drawStringInternal3D(string, xPosition, yPosition, zPosition, 0, 0, 0, red, green, blue, 1.0F);
	}
	
	public void drawString3D(String string, float xPosition, float yPosition, float zPosition, float red, float green, float blue, float alpha){
		this.drawStringInternal3D(string, xPosition, yPosition, zPosition, 0F, 0F, 0F, red, green, blue, alpha);
	}
	
	public void drawString3D(String string, float xPosition, float yPosition, float zPosition, float pitch, float yaw, float roll, float red, float green, float blue, float alpha){
		this.drawStringInternal3D(string, xPosition, yPosition, zPosition, yaw, pitch, roll, red, green, blue, alpha);
	}	
	
	private void drawStringInternal2D(String string, float xPosition, float yPosition, float red, float green, float blue, float alpha){
		theEloi.renderEngine.setRenderMode(EnumRenderMode.ORTHOGRAPHICAL);
		char[] characters = string.toCharArray();
		int mapValue;
		TextureUV uv;
		theEloi.renderEngine.bindTexture("Font");
		Constructor var1 = Constructor.instance;
		for (int i = 0; i < characters.length; i++){
			mapValue = getFontMapValue(characters[i]);
			uv = RenderEngine.getTextureMap(1).getUVCoordinates(mapValue);
			drawCharacter2D(var1, xPosition, yPosition, uv, red, green, blue, alpha);
			xPosition += 16f;
		}	
		theEloi.renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
	}
	
	private void drawStringInternal3D(String string, float xPosition, float yPosition, float zPosition, float yaw, float pitch, float roll, float red, float green, float blue, float alpha){
		//theEloi.renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
		char[] characters = string.toCharArray();
		int mapValue;
		TextureUV uv;
		theEloi.renderEngine.bindTexture("Font");
		Constructor var1 = Constructor.instance;
		GL11.glPushMatrix();
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		for (int i = 0; i < characters.length; i++){
			mapValue = getFontMapValue(characters[i]);
			uv = RenderEngine.getTextureMap(1).getUVCoordinates(mapValue);
			drawCharacter3D(var1, xPosition, yPosition, zPosition, uv, red, green, blue, alpha);
			xPosition += 16f;
		}
		GL11.glPopMatrix();
	}
	
	private int getFontMapValue(char character){
		int mapValue = (int)character - 65;
		if (mapValue == -33){
		} else if (mapValue > -31 && mapValue < 0){
			mapValue += 58;
		}
		return mapValue;
	}
	
	private void drawCharacter2D(Constructor constructor, float x, float y, TextureUV uv, float red, float green, float blue, float alpha){
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
	
	private void drawCharacter3D(Constructor constructor, float x, float y, float z, TextureUV uv, float red, float green, float blue, float alpha){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		constructor.addVertexAndUV(x, y, z, uv.uMin, uv.vMax);
		constructor.addVertexAndUV(x + squareSize, y, z, uv.uMax, uv.vMax);
		constructor.addVertexAndUV(x + squareSize, y + squareSize, z, uv.uMax, uv.vMin);
		constructor.addVertexAndUV(x, y + squareSize, z, uv.uMin, uv.vMin);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public int getStringLength(String str){
		return 25 * str.length();
	}
}
