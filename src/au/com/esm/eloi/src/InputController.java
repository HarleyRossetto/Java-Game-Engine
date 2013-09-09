package au.com.esm.eloi.src;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class InputController {
	private Map<Integer, Keybinding> keys = new HashMap<Integer, Keybinding>();
	public Keybinding moveFoward;
	public Keybinding moveBack;
	public Keybinding moveLeft;
	public Keybinding moveRight;
	public Keybinding moveUp;
	public Keybinding moveDown;
	public Keybinding moveVertSlow;
	public Keybinding speedMultiplier;
	public Keybinding yawMinus;
	public Keybinding yawPlus;
	public Keybinding rollMinus;
	public Keybinding rollPlus;
	public Keybinding pitchMinus;
	public Keybinding pitchPlus;
	public Keybinding resetOrientation;	
	
	public InputController(){
		this.registerBinding(moveFoward = new Keybinding("NavigateFoward", Keyboard.KEY_W));
		this.registerBinding(moveBack = new Keybinding("NavigateBack", Keyboard.KEY_S));
		this.registerBinding(moveLeft = new Keybinding("NavigateLeft", Keyboard.KEY_A));
		this.registerBinding(moveRight = new Keybinding("NavigateRight", Keyboard.KEY_D));
		this.registerBinding(moveUp = new Keybinding("NavigateUp", Keyboard.KEY_SPACE));
		this.registerBinding(moveDown = new Keybinding("NavigateDown", Keyboard.KEY_LSHIFT));
		this.registerBinding(speedMultiplier = new Keybinding("SpeedMultiplier", Keyboard.KEY_LCONTROL));
		this.registerBinding(yawMinus = new Keybinding("OrientateLeft", Keyboard.KEY_NUMPAD4));
		this.registerBinding(yawPlus = new Keybinding("OrientateRight", Keyboard.KEY_NUMPAD6));
		this.registerBinding(rollMinus = new Keybinding("RotateLeft", Keyboard.KEY_NUMPAD7));
		this.registerBinding(rollPlus = new Keybinding("RotateRight", Keyboard.KEY_NUMPAD9));
		this.registerBinding(pitchMinus = new Keybinding("OrientateDown", Keyboard.KEY_NUMPAD8));
		this.registerBinding(pitchPlus = new Keybinding("OrientateUp", Keyboard.KEY_NUMPAD2));
		this.registerBinding(resetOrientation = new Keybinding("OrientateReset", Keyboard.KEY_NUMPAD5));
		this.registerBinding(moveVertSlow = new Keybinding("MoveVerticallySlow", Keyboard.KEY_E));
	}
	
	public void updateController(int key, boolean state){
		Keybinding binding = keys.get(key);
		if (binding != null){
			binding.isPressed = state;
			if (binding.isPressed){
				binding.heldTime += Eloi.delta;
			} else {
				binding.heldTime = 0;
			}
		}			
		if (Keyboard.getEventKey() == Keyboard.KEY_R){
			Eloi.getEloi().renderView.setPosition(0, 0, 0);
		}
		if (resetOrientation.isPressed){
				Eloi.getEloi().renderView.setCameraRotation(0f, 0f, 0f);
		}
	}
	
	public void registerBinding(Keybinding binding){
		if (!testForBindingAvailability(binding.getKeyConstant())){
			keys.put(binding.getKeyConstant(), binding);
		}
	}
	
	public boolean testForBindingAvailability(int constant){
		return keys.containsKey(constant);
	}	
}
