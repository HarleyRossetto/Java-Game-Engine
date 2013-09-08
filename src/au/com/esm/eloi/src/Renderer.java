package au.com.esm.eloi.src;

public abstract class Renderer {
	private EnumRenderMode requiredRenderMode;
	
	public Renderer(EnumRenderMode renderMode){
		requiredRenderMode = renderMode;
	}
	
	public EnumRenderMode getRenderMode(){
		return requiredRenderMode;
	}
}
