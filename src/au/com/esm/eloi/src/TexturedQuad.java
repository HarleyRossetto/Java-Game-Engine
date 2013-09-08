package au.com.esm.eloi.src;

public class TexturedQuad implements IRenderable{
	private TexturedTriangle[] triangles = new TexturedTriangle[2];
	
	public TexturedQuad(){
		
	}
	
	public TexturedQuad(TexturedTriangle t1, TexturedTriangle t2){
		triangles[0] = t1;
		triangles[1] = t2;
	}
	
	public TexturedQuad setTriangle(int triangleNumber, TexturedTriangle triangle){
		triangles[triangleNumber] = triangle;
		return this;
	}
	
	public TexturedVertex getVertex(int vertexNumber){
		if (vertexNumber >= 0 && vertexNumber < 4){
			if (vertexNumber >= 4) {
				return triangles[1].getVertex(1);
			} else {
				return triangles[0].getVertex(vertexNumber);
			}
		}
		return null;
	}

	@Override
	public void render(GlobalRenderer renderer) {
		Constructor var1 = Constructor.instance;
		TexturedVertex vertex;
		for (int i = 0; i < 1; i++){
			for (int j = 0; j < 2; j++){
				vertex = triangles[i].getVertex(j);
				var1.addVertexAndUV(vertex.getVertexData().getX(), vertex.getVertexData().getY(), vertex.getVertexData().getZ(), vertex.getU(), vertex.getV());
			}			
		}		
	}
}
