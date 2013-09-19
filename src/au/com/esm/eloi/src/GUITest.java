package au.com.esm.eloi.src;

public class GUITest extends GUI {
	public GUITest(){
		super();
		build();
	}
	
	public void update(){
		super.update();
	}
	
	protected void build(){
		super.build();
		controlList.add(new GUIControlButton(0, "TOGGLE DEBUG LAYER", Eloi.screenWidth / 2, Eloi.screenHeight - 100, 300, 40));
	}
	
	protected void fireEvents(GUIControl guiControl){
		super.fireEvents(guiControl);
		if (guiControl.controlId == 0){
			Eloi.getEloi().showDebugLayer = !Eloi.getEloi().showDebugLayer;
		}
	}
}
