package au.com.esm.eloi.src;

public class Camera {
	private float xPosition;
	private float yPosition;
	private float zPosition;
	private float prevXPosition;
	private float prevYPosition;
	private float prevZPosition;
	private float yawRotation;
	private float pitchRotation;
	private float rollRotation;
	private float prevYawRotation;
	private float prevPitchRotation;
	private float prevRollRotation;
	private float speed = 0.005F;
	private float slowSpeed = 0.0002F;
	private float speedMultiplier = 2F;
	private float zoom = 1f;
	private float zoomSpeed = 0.005F;
	private float rotationSpeed = 0.05F;
	private int facing = 0;
	private float yawRotationLimit = 90F;
	private float pitchRotationLimit = 85F;
	public MovementBasicControl mbc;
	
	public Camera(){
		mbc = new MovementBasicControl();
		xPosition = 0;
		yPosition = 0;
		zPosition = 0;
		yawRotation = 0;
		pitchRotation = 0;
		rollRotation = 0;
	}
	
	public Camera(float xPos, float yPos, float zPos){
		mbc = new MovementBasicControl();
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.zPosition = zPos;
		yawRotation = 0;
		pitchRotation = 0;
		rollRotation = 0;
	}
	
	public Camera(float xPos, float yPos, float zPos, float yaw, float pitch, float roll){
		mbc = new MovementBasicControl();
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.zPosition = zPos;
		yawRotation = yaw;
		pitchRotation = pitch;
		rollRotation = roll;
	}
	
	public void update(){
		prevXPosition = xPosition;
		prevYPosition = yPosition;
		prevZPosition = zPosition;
		prevYawRotation = yawRotation;
		prevPitchRotation = pitchRotation;
		prevRollRotation = rollRotation;		
		
		float movementMultiplier = false ? speedMultiplier : 1;
		if (mbc.moveFoward != 0){
			//zPosition += ((speed * Eloi.delta) * movementMultiplier) * mbc.moveFoward;
			xPosition += (((speed * Eloi.delta) * movementMultiplier) * mbc.moveFoward) * (float)Math.sin(Math.toRadians(yawRotation));
            zPosition += (((speed * Eloi.delta) * movementMultiplier) * mbc.moveFoward) * (float)Math.cos(Math.toRadians(yawRotation));			
		} 
		if (mbc.moveStrafe != 0){
			//xPosition += ((speed * Eloi.delta) * movementMultiplier) * mbc.moveStrafe;
			xPosition += ((((speed * Eloi.delta) * movementMultiplier) * mbc.moveStrafe) * (float)Math.sin(Math.toRadians(yawRotation + (90 * mbc.moveStrafe)))) * mbc.moveStrafe;
			zPosition += ((((speed * Eloi.delta) * movementMultiplier) * mbc.moveStrafe) * (float)Math.cos(Math.toRadians(yawRotation + (90 * mbc.moveStrafe)))) * mbc.moveStrafe;			
		}
		if (mbc.moveUp != 0){
			yPosition += (((Eloi.getEloi().inputController.moveVertSlow.isPressed ? slowSpeed : speed) * Eloi.delta) * movementMultiplier) * mbc.moveUp;
		}
		if (mbc.roll != 0){
			rollRotation += (rotationSpeed * Eloi.delta) * mbc.roll;
		}
		if (mbc.yaw != 0){
			yawRotation += (rotationSpeed * Eloi.delta) * mbc.yaw;
			//yawRotation += (rotationSpeed * Eloi.delta) * (mbc.yaw * facing); 
			facing = (int)(360 / yawRotation);
		}
		if (mbc.pitch != 0){
			//pitchRotation += (rotationSpeed * Eloi.delta) * mbc.pitch;
			
			//pitchRotation -= ((rotationSpeed * Eloi.delta) * (float)Math.sin(Math.toRadians(prevPitchRotation))) * mbc.pitch;
			//yawRotation += ((rotationSpeed * Eloi.delta) * (float)Math.cos(Math.toRadians(prevYawRotation))) * mbc.pitch;
			//rollRotation -= (rotationSpeed * Eloi.delta) * mbc.pitch;
		}	
		//this.limitRotation();
	}
	
	public void updateCameraPosition(float xAddative, float yAddative, float zAddative){
		xPosition += xAddative;
		yPosition += yAddative;
		zPosition += zAddative;
	}
	
	public void updateX(float addative){
		xPosition += addative;
	}
	
	public void updateY(float addative){
		yPosition += addative;
	}
	
	public void updateZ(float addative){
		zPosition += addative;
	}
	
	public void setCameraRotation(float yaw, float pitch, float roll){
		this.yawRotation = yaw;
		this.pitchRotation = pitch;
		this.rollRotation = roll;		
		//this.limitRotation();
	}
	
	private void limitRotation(){
		if (yawRotation < -yawRotationLimit){
			yawRotation = -yawRotationLimit;
		} else if (yawRotation > yawRotationLimit){
			yawRotation = yawRotationLimit;
		}
		if (pitchRotation < -pitchRotationLimit){
			pitchRotation = -pitchRotationLimit;
		} else if (pitchRotation > pitchRotationLimit){
			pitchRotation = pitchRotationLimit;
		}
	}
	
	public void updateYaw(float addative){
		yawRotation += addative;
	}
	
	public void updatePitch(float addative){
		pitchRotation += addative;
	}
	
	public void updateRoll(float addative){
		rollRotation += addative;
	}
	
	public void setX(float value){
		xPosition = value;
	}
	
	public void setY(float value){
		yPosition = value;
	}
	
	public void setZ(float value){
		zPosition = value;
	}
	
	public void setPosition(float x, float y, float z){
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
	}
	
	public void setZoom(float value){
		zoom = value;
	}
	
	public float getX(){
		return xPosition;
	}
	
	public float getY(){
		return yPosition;
	}
	
	public float getZ(){
		return zPosition;
	}
	
	public float getYawRotation(){
		return yawRotation;
	}
	
	public float getPitchRotation(){
		return pitchRotation;
	}
	
	public float getRollRotation(){
		return rollRotation;
	}
	
	public float getZoom(){
		return zoom;
	}
	
	public float getDistanceSquaredTo(float otherXpos, float otherYpos, float otherZpos){
		float x = xPosition - otherXpos;
		float y = yPosition - otherYpos;
		float z = zPosition - otherZpos;
		return x * x + y * y + z * z;
	}
	
	public int getFacing(){
		return facing;
	}
}
