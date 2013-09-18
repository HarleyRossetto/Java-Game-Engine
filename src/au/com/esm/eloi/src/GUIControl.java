package au.com.esm.eloi.src;

public abstract class GUIControl implements IRenderable{
	protected float xPosition;
	protected float yPosition;
	
	public void onClick(){	}
	
	@Override
	public void render(GlobalRenderer renderer) {	}
}
