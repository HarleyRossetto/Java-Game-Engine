package au.com.esm.eloi.src;

public class Keybinding {
	private String bindingName;
	private int keyboardConstant;
	protected int heldTime;
	protected boolean isPressed = false;
	
	public Keybinding(String name, int key){
		bindingName = name;
		keyboardConstant = key;
	}
	
	public int getKeyConstant(){
		return keyboardConstant;
	}
	
	public int getBindingHeldTime(){
		return heldTime;
	}
	
	public boolean active(){
		return isPressed;
	}
}
