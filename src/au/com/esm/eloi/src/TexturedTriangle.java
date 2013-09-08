package au.com.esm.eloi.src;

public class TexturedTriangle {
	private TexturedVertex[] verticies = new TexturedVertex[3];
	private int index = 0;
	
	public void addVertex(TexturedVertex vertex){
		if (index < 3){
			verticies[index] = vertex;
			index++;
		}
	}
	
	public TexturedVertex getVertex(int vertexIndex){
		if (index >= 0 && index <3){
			return verticies[vertexIndex];
		}
		return null;
	}
}
