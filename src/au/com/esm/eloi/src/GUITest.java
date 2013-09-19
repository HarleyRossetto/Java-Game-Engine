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
		controlList.add(new GUIControlButton("BUTTON", Eloi.screenWidth / 2, Eloi.screenHeight - 100, 300, 40));
	}
}
