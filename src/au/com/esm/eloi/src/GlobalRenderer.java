package au.com.esm.eloi.src;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GlobalRenderer {
	protected RenderEngine renderEngine;
	private float fogNear = 20.0f;
	private float fogFar = 40.0f;
	private Color fogColor = new Color(0.7f, 0.7f, 0.8f, 1f);
	
	private ArrayList<IRenderable> renderers = new ArrayList<IRenderable>();
	
	public GlobalRenderer(RenderEngine engine){
		this.renderEngine = engine;
	}
	
	public void prepareGlobalRendering(){
		GL11.glEnable(GL11.GL_FOG);		
		FloatBuffer fogColors = BufferUtils.createFloatBuffer(4);
		fogColors.put(new float[] {fogColor.r, fogColor.g, fogColor.b, fogColor.a});
		GL11.glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
		fogColors.flip();
		GL11.glFog(GL11.GL_FOG_COLOR, fogColors);
		GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_NICEST);
		GL11.glFogf(GL11.GL_FOG_END, fogFar);
		GL11.glFogf(GL11.GL_FOG_DENSITY, 0.005f);
	}
	
	public void prepareForRender(Camera camera){
		renderEngine.setRenderMode(EnumRenderMode.PERSPECTIVE);
		GL11.glRotatef(camera.getPitchRotation(), 1, 0, 0);
		GL11.glRotatef(camera.getYawRotation(), 0, 1, 0);
		GL11.glRotatef(camera.getRollRotation(), 0, 0, 1);
		GL11.glTranslatef(-camera.getX(), -camera.getY(), camera.getZ());
		GL11.glScalef(camera.getZoom(), camera.getZoom(), camera.getZoom());
	}
	
	public void registerRenderer(IRenderable renderer){
		if (!renderers.contains(renderer)){
			renderers.add(renderer);
		}
	}
}
