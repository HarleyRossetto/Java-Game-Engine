package au.com.esm.eloi.src;

public class Entity {
	private int entityId;
	private float xPosition;
	private float yPosition;
	private float zPosition;
	private float previousXposition;
	private float previousYposition;
	private float previousZposition;
	private float yawRotation = 0;
	private float pitchRotation = 0;
	private float previousYawRotation;
	private float previousPitchRotation;
	private float xVelocity;
	private float yVelocity;
	private float zVelocity;
	private float speed;
	private float weight;
	private int age;
	
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
