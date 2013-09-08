package au.com.esm.eloi.src;

import java.util.ArrayList;

public abstract class Model{
	protected ArrayList<Model> children = new ArrayList<Model>();
	protected ArrayList<TexturedQuad> modelComponents = new ArrayList<TexturedQuad>();
	
	public Model(){
		
	}
	
	public void addChildModel(Model child){
		children.add(child);
	}		
}
