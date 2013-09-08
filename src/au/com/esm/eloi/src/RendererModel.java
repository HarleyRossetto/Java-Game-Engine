package au.com.esm.eloi.src;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class RendererModel implements IRenderable {
	protected boolean compiled = false;
	protected boolean requiresRenderTimeRecompilation = false;
	protected boolean compiling = false;
	private Model model;
	private int[] rawOglCallIds = new int[16];
	private int rawOglCallIndex = 0;
	
	@Override
	public void render(GlobalRenderer renderer) {
		this.renderModel(renderer);		
	}	

	protected void compileModel(GlobalRenderer renderer){
		if (!isModelCompiling()){
			if (rawOglCallIndex < rawOglCallIds.length){
				compiling = true;
				GL11.glNewList(registerNewOglListId(), GL11.GL_COMPILE);
				
				TexturedQuad quad;
				for (int i = 0; i < model.modelComponents.size(); i++){
					if (model.modelComponents.get(i) != null){
						quad = model.modelComponents.get(i);
						for (int j = 0; j < 4; j++){
							quad.render(renderer);
						}
					}
				}				
				for (int i = 0; i < model.children.size(); i++){
					if (model.children.get(i) != null){
						model.children.get(i);
					}
				}
				
				
				compiled = true;
			}
		}
	}
	
	private void renderModel(GlobalRenderer renderer){
		if (!compiled | requiresRenderTimeRecompilation){
			this.compileModel(renderer);
		}
		GL11.glCallLists((IntBuffer)BufferUtils.createIntBuffer(rawOglCallIndex).put(rawOglCallIds).flip());
		for (int i = 0; i < model.children.size(); i++){
			if (model.children.get(i) != null){
				//models.get(i).render(renderer);
			}
		}
	}
	protected final void beginCompiling(){
		if (!isModelCompiling()){
			if (rawOglCallIndex < rawOglCallIds.length){
				compiling = true;
				GL11.glNewList(registerNewOglListId(), GL11.GL_COMPILE);
			}
		}
	}
	
	protected final void endCompiling(){
		if (isModelCompiling()){
			GL11.glEndList();
			compiling = false;
		}
	}
	
	protected final int registerNewOglListId(){
		rawOglCallIds[rawOglCallIndex] = OpenGLHelper.generateDisplayLists(1);
		rawOglCallIndex++;
		return rawOglCallIds[rawOglCallIndex = 1];
	}
	
	public final void cleanupModelResources(){
		GL11.glDeleteLists(rawOglCallIds[0], rawOglCallIndex);
		compiled = false;
	}
	
	public boolean isModelCompiling(){
		return compiling;
	}
}
