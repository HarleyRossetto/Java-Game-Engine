package au.com.esm.eloi.src;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ModelDebug extends ModelOld{
	public ModelDebug(){
		super("House");
		compileModel();
	}

	@Override
	public void compileModel() {
		TextureUV v = RenderEngine.getTextureMap(0).getUVCoordinates(0);		
		int glCallId = OpenGLHelper.generateDisplayLists(1);
		GL11.glNewList(glCallId, GL11.GL_COMPILE);		
		Constructor var1 = Constructor.instance;
		var1.addVertexAndUV(0, 0, 0, v.uMin, v.vMin);
		var1.addVertexAndUV(1, 0, 0, v.uMax, v.vMin);
		var1.addVertexAndUV(1, 1, 0, v.uMax, v.vMax);
		var1.addVertexAndUV(0, 1, 0, v.uMin, v.vMax);
		GL11.glEndList();
		this.callIds = (IntBuffer) BufferUtils.createIntBuffer(1).put(glCallId).flip();
	}
}
