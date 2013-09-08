package au.com.esm.eloi.src;

public class TexturedVertex {
	private Vertex vertexData;
	private float u;
	private float v;
	
	public TexturedVertex(float x, float y, float z, float u, float v){
		vertexData = new Vertex(x, y, z);
		this.u = u;
		this.v = v;
	}
	
	public Vertex getVertexData(){
		return vertexData;
	}
	
	public float getU(){
		return u;
	}
	
	public float getV(){
		return v;
	}
}
