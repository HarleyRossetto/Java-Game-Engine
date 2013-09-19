package au.com.esm.eloi.src;

import org.lwjgl.opengl.Display;

public abstract class GUIControl implements IRenderable{
	protected float xPosition;
	protected float yPosition;
	protected float width;
	protected float height;
	protected int state = 0;
	
	public boolean isMouseOver(int mX, int mY){
		if (mX >= xPosition && mX <= xPosition + width){
			if (mY >= yPosition && mY <= yPosition + height){
				return true;
			}
		}
		return false;
	}
	
	public void onClick(){	
		state = 2;
	}
	
	@Override
	public void render(GlobalRenderer renderer) {	}
}
