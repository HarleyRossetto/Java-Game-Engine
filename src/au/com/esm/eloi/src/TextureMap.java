package au.com.esm.eloi.src;

public class TextureMap { 
	private int width;
	private int height;
	private int componentWidth;
	private int componentHeight;
	private int componentsInWidth = 16;
	private int componentsInHeight = 16;
	private float componentUVSixteenth = 0.0625f;
	
	public TextureMap(Texture texture){
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		componentWidth = width / componentsInWidth;
		componentHeight = height / componentsInHeight;
		componentUVSixteenth = 1.0f / componentsInWidth;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getComponentWidth(){
		return componentWidth;
	}
	
	public int getComponentHeight(){
		return componentHeight;
	}
	
	public TextureUV getUVCoordinates(int textureId){		
		
		int row = (textureId % componentsInWidth);
		int column = (textureId / componentsInHeight);
		
		float u0 = (row * componentUVSixteenth);
		float u1 = u0 + componentUVSixteenth;
		float v1 = (column * componentUVSixteenth);
		float v0 = v1 + componentUVSixteenth;
		
		TextureUV vertex = new TextureUV(u0, u1, v0, v1);
		return vertex;
	}
}
