package au.com.esm.eloi.src;

public class Texture {
	private int width;
	private int height;
	private int oglId;
	
	public Texture(int width, int height, int oglId){
		this.width = width;
		this.height = height;
		this.oglId = oglId;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getOglId(){
		return oglId;
	}
}
