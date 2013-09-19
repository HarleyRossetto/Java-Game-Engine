package au.com.esm.eloi.src;

import org.lwjgl.opengl.GL11;

public class GUIControlButton extends GUIControl {
	private String displayText;
	private float buttonStateOffset = 17;
	
	public GUIControlButton(int controlId, String displayTxt, float x, float y, float width, float height){
		super(controlId);
		this.displayText = displayTxt;
		this.xPosition = x;
		this.yPosition = y;
		this.width = width;
		this.height = height;
	}
	
	public void onClick(){
		super.onClick();
	}
	
	@Override
	public void render(GlobalRenderer globalRenderer){
		float yOffset = buttonStateOffset * state;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glBegin(GL11.GL_TRIANGLES);			
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 2, GUI.genericGuiControlMapPixelSize * (2 + yOffset));
		GL11.glVertex2f(xPosition, yPosition);			
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 2, (float) (GUI.genericGuiControlMapPixelSize * (17 + yOffset)));
		GL11.glVertex2f(xPosition, yPosition + height);				
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 102, (float) (GUI.genericGuiControlMapPixelSize * (17 + yOffset)));
		GL11.glVertex2f(xPosition + width, yPosition + height);			
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 102, (float) (GUI.genericGuiControlMapPixelSize * (17 + yOffset)));
		GL11.glVertex2f(xPosition + width, yPosition + height);		
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 102, GUI.genericGuiControlMapPixelSize * (2 + yOffset));
		GL11.glVertex2f(xPosition + width, yPosition);				
		GL11.glTexCoord2f(GUI.genericGuiControlMapPixelSize * 2, GUI.genericGuiControlMapPixelSize * (2 + yOffset));
		GL11.glVertex2f(xPosition, yPosition);					
		GL11.glEnd();
		RendererText textRenderer = Eloi.getEloi().textRenderer;
		int stringLength = textRenderer.getStringLength(displayText);
		float textXposition = xPosition + ((width / 2) - (stringLength / 2));
		float textYposition = (height / 2) + yPosition;
		Eloi.getEloi().textRenderer.drawString(displayText, textXposition, textYposition);
	}
}
