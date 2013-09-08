package au.com.esm.eloi.src;

public class ModelPartBox {
	private int x;
	private int y;
	private int z;
	private int width;
	private int height;
	private int depth;
	
	public ModelPartBox(int xOrigin, int yOrigin, int zOrigin, int width, int height, int depth){
		this.x = xOrigin;
		this.y = yOrigin;
		this.z = zOrigin;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getDepth(){
		return depth;
	}

}
