package au.com.esm.eloi.src;

public abstract class Render {
	private RenderManager manager;
	
	public abstract void render();
	
	public void setManager(RenderManager renderManager){
		manager = renderManager;
	}
}
