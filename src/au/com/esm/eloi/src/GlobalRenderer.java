package au.com.esm.eloi.src;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class GlobalRenderer {
	protected RenderEngine renderEngine;
	
	private ArrayList<IRenderable> renderers = new ArrayList<IRenderable>();
	
	public GlobalRenderer(RenderEngine engine){
		this.renderEngine = engine;
	}
	
	public void prepareForRender(Camera camera){
		renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
		GL11.glRotatef(camera.getRollRotation(), 0, 0, 1);
		GL11.glRotatef(camera.getYawRotation(), 0, 1, 0);
		GL11.glRotatef(camera.getPitchRotation(), 1, 0, 0);
		GL11.glTranslatef(-camera.getX(), -camera.getY(), camera.getZ());
		GL11.glScalef(camera.getZoom(), camera.getZoom(), camera.getZoom());
	}
	
	public void registerRenderer(IRenderable renderer){
		if (!renderers.contains(renderer)){
			renderers.add(renderer);
		}
	}
}
