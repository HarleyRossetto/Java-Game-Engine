package au.com.esm.eloi.src;

import java.util.Map;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.opengl.PNGDecoder.Format;

public class RenderEngine {
	private final Map<String, Integer> textureMap = new HashMap<String, Integer>();
	private static TextureMap groundTexture;
	private static TextureMap fontTexture;
	private static Texture genericGuiControlTexture;
	
	public RenderEngine() {
		
	}
	
	public void loadTextures(){
		loadTexture("terrabasics", "TerrainBasics", EnumResourceType.Terrain, true, true);
		loadTexture("font", "Font", EnumResourceType.Font, true, false);
		loadTexture("genericControls", "GenericGUIControls", EnumResourceType.Interface, true, false);
	}
	
	public void bindTexture(String name){
		if (textureMap.containsKey(name)){
			bindTexture(textureMap.get(name));
		} else {			
			bindTexture(textureMap.get("TerrainBasics"));
		}
	}
	
	public void bindTexture(int id){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbindTexture(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static TextureMap getTextureMap(int id){
		switch (id){
			case 0:
				return groundTexture;
			case 1: 
				return fontTexture;
			default:
				return null;
		}
	}
	
	private boolean loadTexture(String fileName, String internalName, EnumResourceType type, boolean useInternalResources, boolean asMainPack){
		String processedFileName;
		String suffix;
		if (useInternalResources){
			if (fileName.endsWith(".png")){
				suffix = "";
			} else {
				suffix = ".png";
			}
			processedFileName = "resources/" + type.resourceDirectory + fileName + suffix;
		} else {
			if (fileName.endsWith(".png")){
				suffix = "";
			} else {
				suffix = ".png";
			}
			processedFileName = fileName + suffix;
		}
		if (asMainPack){
			groundTexture = new TextureMap(loadTextureMap(processedFileName, internalName));
			return true;
		}
		if (type == EnumResourceType.Font){
			fontTexture = new TextureMap(loadTextureMap(processedFileName, internalName));
		}
		if (type == EnumResourceType.Interface){
			genericGuiControlTexture = loadTextureMap(processedFileName, internalName);
		}
		return loadTexture(processedFileName, internalName);
	}
	
	private boolean loadTexture(String fileName, String internalName) {
		boolean success = false;
		int textureHandle = GL11.glGenTextures();
		try {
			InputStream stream = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(stream);
			ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
			buffer.flip();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			success = true;
		} catch (FileNotFoundException ex){
			success = false;
			System.out.println("FileNotFoundException: Unable to load texture: " + fileName + ". " + ex.getStackTrace());
		} catch (IOException ex){
			success = false;
			System.out.println("IOException: Unable to load texture: " + fileName + ". " + ex.getStackTrace());
		} finally {
			if (success){
				textureMap.put(internalName, textureHandle);
			} 
		}
		return success;
	}
	
	private Texture loadTextureMap(String fileName, String internalName) {
		Texture loadedTexture = null;
		boolean success = false;
		PNGDecoder decoder = null;
		int textureHandle = GL11.glGenTextures();
		try {
			InputStream stream = new FileInputStream(fileName);
			decoder = new PNGDecoder(stream);
			ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
			buffer.flip();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			success = true;
		} catch (FileNotFoundException ex){
			success = false;
			System.out.println("FileNotFoundException: Unable to load texture: " + fileName + ". " + ex.getStackTrace());
		} catch (IOException ex){
			success = false;
			System.out.println("IOException: Unable to load texture: " + fileName + ". " + ex.getStackTrace());
		} finally {
			if (success){
				textureMap.put(internalName, textureHandle);
				loadedTexture = new Texture(decoder.getWidth(), decoder.getHeight(), textureHandle);
			} 
		}
		return loadedTexture;
	}
	
	public void setRenderMode(EnumRenderMode mode){
		if (mode == EnumRenderMode.ORTHOGRAPHICAL){
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			//Need to switch bottom right and bottom top.
			GL11.glOrtho(0.0D, Eloi.screenWidth, Eloi.screenHeight, 0.0D, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, 0F);
		} else if (mode == EnumRenderMode.PERSPECTIVE){
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GLU.gluPerspective(Eloi.fieldOfView, (float)Eloi.screenWidth / (float)Eloi.screenHeight, Eloi.zNear, Eloi.zFar);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
		}
	}
}
