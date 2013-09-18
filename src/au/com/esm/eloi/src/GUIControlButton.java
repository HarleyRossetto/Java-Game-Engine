package au.com.esm.eloi.src;

public class GUIControlButton extends GUIControl {
	private String displayText;
	private int state = 0;
	
	public GUIControlButton(String displayTxt){
		this.displayText = displayTxt;
	}
	
	public GUIControlButton setPosition(float x, float y){
		this.xPosition = x;
		this.yPosition = y;
		return this;
	}
	
	@Override
	public void render(GlobalRenderer globalRenderer){
		
	}
}
