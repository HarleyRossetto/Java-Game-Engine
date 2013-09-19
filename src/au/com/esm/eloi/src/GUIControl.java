package au.com.esm.eloi.src;

public abstract class GUIControl implements IRenderable{
	protected int controlId;
	protected float xPosition;
	protected float yPosition;
	protected float width;
	protected float height;
	protected int state = 0;
	
	public GUIControl(int controlIdentifier){
		this.controlId = controlIdentifier;
	}
	
	public boolean isMouseOver(int mX, int mY){
		if (mX >= xPosition && mX <= xPosition + width){
			if (mY >= yPosition && mY <= yPosition + height){
				return true;
			}
		}
		return false;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public void onClick(){	}
	
	@Override
	public void render(GlobalRenderer renderer) {	}
}
