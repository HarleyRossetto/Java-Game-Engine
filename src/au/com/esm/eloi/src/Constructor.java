package au.com.esm.eloi.src;

import java.lang.Float;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class Constructor {
	public static final Constructor instance = new Constructor(2097152);
	
	private ByteBuffer byteBuffer;	
	private IntBuffer vertexBuffers;
	private IntBuffer intBuffer;
	
	private int[] rawDataBuffer;
	
	private int vertexBufferObjectCount = 5;
	private int vertexBufferObjectIndex = 0;
	
	private boolean isTextured = false;
	private boolean isColored = false;
	private float u;
	private float v;
	private int red;
	private int green;
	private int blue;
	private int alpha;
	
	private float xOffset = 0;
	private float yOffset = 0;
	private float zOffset = 0;
	
	private int rawDataBufferIndex = 0;
	private int addedVertices = 0;
	private int vertexCount = 0;
	
	private boolean isDrawing = false;
	
	public Constructor(int bufferSize){		
		byteBuffer = ByteBuffer.allocateDirect(bufferSize * 4).order(ByteOrder.nativeOrder());
		intBuffer = byteBuffer.asIntBuffer();
		rawDataBuffer = new int[bufferSize];
		this.vertexBuffers = ByteBuffer.allocateDirect(vertexBufferObjectCount << 2).asIntBuffer();
		ARBVertexBufferObject.glGenBuffersARB(this.vertexBuffers);
	}
	
	public void draw(){
		
		if (!isDrawing) {
			System.out.println("Already drawing!");
		} else {			
			if (addedVertices > 0) {
				intBuffer.clear();
				intBuffer.put(rawDataBuffer, 0, this.rawDataBufferIndex);
				byteBuffer.position(0);
				byteBuffer.limit(this.rawDataBufferIndex * 4);
				
				this.vertexBufferObjectIndex = (this.vertexBufferObjectIndex + 1) % vertexBufferObjectCount;
				ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexBuffers.get(this.vertexBufferObjectIndex));
				ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.byteBuffer, ARBVertexBufferObject.GL_STREAM_DRAW_ARB);
				
				if (isTextured) {
					GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, 12L);
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				
				if (isColored) {
					GL11.glColorPointer(4, GL11.GL_FLOAT, 32, 20L);
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}
								
				GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, 0L);				
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				
				
				GL11.glDrawArrays(GL11.GL_QUADS, 0, vertexCount);	
				
				
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				
				if (isColored) {
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
					isColored = false;
				}
				if (isTextured) {
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					isTextured = false;
				}
				
				isDrawing = false;				
				this.reset();
				}
			}
		}
	}
	
	public void startDrawing(){
		this.isDrawing = true;
		this.draw();
	}
	
	public void setColor(float red, float green, float blue, float alpha){
		this.red = Float.floatToRawIntBits(red);
		this.green = Float.floatToRawIntBits(green);
		this.blue = Float.floatToRawIntBits(blue);
		this.alpha = Float.floatToRawIntBits(alpha);
		this.isColored = true;
	}
	
	public void setUV(float u, float v){
		this.u = u;
		this.v = v;
		this.isTextured = true;
	}
	
	public void addVertexTranslation(float xOffset, float yOffset, float zOffset){
		this.xOffset += xOffset;
		this.yOffset += yOffset;
		this.zOffset += zOffset;
	}
	
	public void setVertexTranslation(float xOffset, float yOffset, float zOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	public void resetTranslation(){
		this.xOffset = 0f;
		this.yOffset = 0f;
		this.zOffset = 0f;
	}
	
	public void addVertexAndUV(float x, float y, float z, float u, float v){
		this.setUV(u, v);
		this.addVertex(x, y, z);
	}
	
	public void addVertexWithColor(float x, float y, float z, float red, float green, float blue, float alpha){
		this.setColor(red, green, blue, alpha);
		this.addVertex(x, y, z);
	}
	
	public void addVertex(float x, float y, float z){
		addedVertices++;
		
		rawDataBuffer[rawDataBufferIndex + 0] = Float.floatToRawIntBits((float)x + xOffset);
		rawDataBuffer[rawDataBufferIndex + 1] = Float.floatToRawIntBits((float)y + yOffset);
		rawDataBuffer[rawDataBufferIndex + 2] = Float.floatToRawIntBits((float)z + zOffset);
		
		if (isTextured){
			rawDataBuffer[rawDataBufferIndex + 3] = Float.floatToRawIntBits((float)u);
			rawDataBuffer[rawDataBufferIndex + 4] = Float.floatToRawIntBits((float)v);
		}
		
		if (isColored){
			rawDataBuffer[rawDataBufferIndex + 5] = this.red;
			rawDataBuffer[rawDataBufferIndex + 6] = this.green;
			rawDataBuffer[rawDataBufferIndex + 7] = this.blue;
			rawDataBuffer[rawDataBufferIndex + 8] = this.alpha;
		}
		this.vertexCount++;
		this.rawDataBufferIndex += 8;	
		
		if (addedVertices % 4 == 0){
			this.isDrawing = true;
			this.draw();
		}
	}
	
	private void reset(){
		this.byteBuffer.clear();
		this.rawDataBufferIndex = 0;
		this.addedVertices = 0;
		this.vertexCount = 0;
	}
}