package au.com.esm.eloi.src;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

public abstract class ModelOld {
	private String modelInternalName;
	protected IntBuffer callIds;
	
	public ModelOld(String internalName){
		modelInternalName = internalName;
	}
	
	protected abstract void compileModel();
	
	public void renderModel(){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glCallLists(callIds);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public String getModelInternalName(){
		return modelInternalName;
	}
	
	public void deleteModelOGLCallsLists(){
		GL11.glDeleteLists(callIds.get(0), callIds.limit() - 1);
	}
}
