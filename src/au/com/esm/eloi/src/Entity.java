package au.com.esm.eloi.src;

public class Entity {
	private int entityId;
	protected float xPosition;
	protected float yPosition;
	protected float zPosition;
	protected float previousXposition;
	protected float previousYposition;
	protected float previousZposition;
	protected float yawRotation = 0;
	protected float pitchRotation = 0;
	protected float rollRotation = 0;
	protected float previousYawRotation;
	protected float previousPitchRotation;
	protected float xVelocity;
	protected float yVelocity;
	protected float zVelocity;
	protected float speed;
	protected float weight;
	protected int age;
	
	public Entity(int id){
		entityId = id;
	}
	
	public void update(){
		onUpdate();
	}
	
	private void onUpdate(){
		age++;
		previousXposition = xPosition;
		previousYposition = yPosition;
		previousZposition = zPosition;
		previousYawRotation = yawRotation;
		previousPitchRotation = pitchRotation;
		
		xPosition += xVelocity * speed;
		yPosition += yVelocity * speed;
		zPosition += zVelocity * speed;
	}
}
