package au.com.esm.eloi.src;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GUI implements IRenderable {
	public static final float genericGuiControlMapPixelSize = 0.00390625F;
	protected List<GUIControl> controlList = new ArrayList<GUIControl>();
	protected int clickHeldTime = 0;
		
	public GUI(){
		
	}
	
	public void update() {
		int mouseX = Mouse.getX();
		int mouseY = Eloi.screenHeight - Mouse.getY() - 1;
		
		for (GUIControl control : controlList){
			if (control.isMouseOver(mouseX, mouseY)){
				control.state = 1;	
				if (Mouse.isButtonDown(0)){
					control.setState(2);
					if (clickHeldTime == 0){
						this.fireEvents(control);
						clickHeldTime += Eloi.delta;
					}
				}
				Mouse.next();
				if (!Mouse.getEventButtonState()){
					clickHeldTime = 0;
				}
			} else {
				control.setState(0);
			}
		}
	}
	
	protected void fireEvents(GUIControl guiControl){
		guiControl.onClick();
	}
	
	protected void build(){}
	
	@Override
	public void render(GlobalRenderer renderer) {	
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		renderer.renderEngine.setRenderMode(EnumRenderMode.ORTHOGRAPHICAL);
		renderer.renderEngine.bindTexture("GenericGUIControls");
		for (GUIControl control : controlList){
			control.render(renderer);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}	
}
