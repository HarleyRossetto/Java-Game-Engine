package au.com.esm.eloi.src;

public enum EnumResourceType {
	Terrain("terrain/"), Skin("skins/"), Interface("interface/"), Font("fonts/"); 
	
	public final String resourceDirectory;
	
	EnumResourceType(String resourceDir){
		this.resourceDirectory = resourceDir;
	}
	
}
