package au.com.esm.eloi.src;

public class Tile {
	public static final Tile[] tileList = new Tile[256];
	
	public static final Tile grass = new Tile(0).setDisplayName("Grass").setInternalName("grass");
	public static final Tile stone = new Tile(33).setDisplayName("Stone").setInternalName("stone");
	
	private int id;
	private String internalName;
	private String displayName;
	private int x;
	private int y;
	private int z;
	
	protected int renderType = 0;
	
	public Tile(int tileId){
		this.id = tileId;
		tileList[id] = this;
	}
	
	public Tile setDisplayName(String displayName){
		this.displayName = displayName;
		return this;
	}
	
	public Tile setInternalName(String name){
		this.internalName = name;
		return this;
	}
	
	public Tile setCoordinates(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;	
	}
	
	public void update() {	};
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.internalName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public int getRenderType(){
		return this.renderType;
	}
}
